package cn.edu.bit.zzb.mpsoc.mapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class ACG extends ArrayList<ACGNode> implements Runnable {

	private static final long serialVersionUID = 5531367216863080553L;
	mxGraph graph = null;
	Map<String, String> properties = null;

	public ACG(mxGraph graph, Map<String, String> properties) {
		super();
		this.graph = graph;
		this.properties = properties;
	}

	public void creatACG() {

		MainFrame.statusBar.setStatue("Clearing the old ACG");
		this.clear();
		MainFrame.statusBar.setStatue("Run the TGFF");
		runTGFF(properties);
//		MainFrame.statusBar.setStatue("Creating a new ACG");
		readTGFF(graph);
		MainFrame.statusBar.setStatue("Layout the ACG");
		View.layoutACG();
		MainFrame.statusBar.setStatue("Clearing the trush");
		clearTrash();
		MainFrame.statusBar.setStatue("Ready");

	}

	public void readTGFF(mxGraph graph) {
		BufferedReader br = null;

		List<Float> edgeType = new ArrayList<Float>();

		String str;
		boolean fileFormatErrorOfCom = true;
		boolean fileFormatErrorOfGraph = true;

		try {
			br = new BufferedReader(new FileReader(
					System.getProperty("user.dir") + File.separator
							+ "NocMappingExtension" + File.separator
							+ "Task.tgff"));
			while ((str = br.readLine()) != null) {
				if (str.startsWith("@COMMUNICATION_TYPE")) {
					// read communication type first
					br.readLine();
					br.readLine();
					br.readLine();
					br.readLine();
					br.readLine();

					while (!((str = br.readLine()).startsWith("}"))) {
						edgeType.add(Float.valueOf(str.substring(7).trim()));
					}
					fileFormatErrorOfCom = false;
				} else if (str.startsWith("@TASK_GRAPH")) {
					// read task Graph
					br.readLine();
					br.readLine();

					int i = 0;
					List<mxCell> v = new ArrayList<mxCell>();
					while (!((str = br.readLine()).startsWith("}"))) {
						if (str.startsWith("	TASK")) {
							String[] str1 = str.split("M");
							String[] str2 = str1[0].split("E");
							ACGNode task = new ACGNode(i++,
									Float.parseFloat(str2[1].trim()),
									Float.parseFloat(str1[1].trim()));
							this.add(task);

							v.add((mxCell) graph.insertVertex(
									graph.getDefaultParent(),
									String.valueOf(task.getId()), task.getId(),
									0, 0, 30, 30, "shape=ellipse"));
						} else if (str.startsWith("	ARC")) {
							str = str.split("FROM")[1];
							String str1[] = str.split("TO");
							String str2[] = str1[1].split("TYPE");
							int sourceTaskIndex = Integer.valueOf(str1[0]
									.trim().substring(3).trim());
							int targetTaskIndex = Integer.valueOf(str2[0]
									.trim().substring(3).trim());
							int typeIndex = Integer.valueOf(str2[1].trim());

							this.get(sourceTaskIndex).addEdge(
									this.get(targetTaskIndex),
									edgeType.get(typeIndex));
							graph.insertEdge(graph.getDefaultParent(),
									String.valueOf(typeIndex),
									edgeType.get(typeIndex),
									v.get(sourceTaskIndex),
									v.get(targetTaskIndex));

						}
					}

					fileFormatErrorOfGraph = false;

				}

			}

			if (fileFormatErrorOfCom || fileFormatErrorOfGraph) {
				System.out.println("Format of file is Error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void clearTrash() {
		Process p = null;
		String parent = System.getProperty("user.dir") + File.separator
				+ "NocMappingExtension" + File.separator;
		try {
			if (System.getProperties().getProperty("os.name")
					.contains("Window")) {
				p = Runtime.getRuntime().exec(
						"cmd /c del " + parent + "Task.vcg");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec(
						"cmd /c del " + parent + "Task.tgffopt");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec(
						"cmd /c del " + parent + "Task.eps");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec(
						"cmd /c del " + parent + "Task.tgff");
				p.waitFor();
				p.destroy();
			} else {
				p = Runtime.getRuntime().exec("rm -f " + parent + "Task.vcg");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec(
						"rm -f " + parent + "Task.tgffopt");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec("rm -f " + parent + "Task.eps");
				p.waitFor();
				p.destroy();
				p = Runtime.getRuntime().exec("rm -f " + parent + "Task.tgff");
				p.waitFor();
				p.destroy();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void runTGFF(Map<String, String> properties) {
		Process p = null;
		BufferedWriter bw = null;
		try {
			String parent = System.getProperty("user.dir") + File.separator
					+ "NocMappingExtension" + File.separator;
			
			MainFrame.statusBar.setStatue("tgff1");
			
			bw = new BufferedWriter(new FileWriter(parent + "Task.tgffopt"));
			bw.write("#This is only for TaskGraph");
			bw.newLine();
			bw.write("seed " + properties.get("seed") + "\n");
			bw.write("period_mul " + properties.get("period_mul") + "\n");
			bw.write("prob_multi_start_nodes "
					+ properties.get("prob_multi_start_nodes") + "\n");
			bw.write("start_node " + properties.get("start_node") + "\n");
			bw.write("task_type_cnt " + properties.get("task_type_cnt") + "\n");
			bw.write("task_cnt " + properties.get("task_cnt") + "\n");
			bw.write("trans_type_cnt " + properties.get("trans_type_cnt")
					+ "\n");
			bw.write("gen_series_parallel\n");
			bw.write("series_wid " + properties.get("series_wid") + "\n");
			bw.write("series_len " + properties.get("series_len") + "\n");
			bw.write("series_must_rejoin "
					+ properties.get("series_must_rejoin") + "\n");
			bw.write("series_subgraph_fork_out "
					+ properties.get("series_subgraph_fork_out") + "\n");
			bw.write("series_local_xover "
					+ properties.get("series_local_xover") + "\n");
			bw.write("series_global_xover "
					+ properties.get("series_global_xover") + "\n");
			bw.write("table_label COMMUNICATION_TYPE\n");
			bw.write("table_cnt 1\n");
			bw.write("type_attrib C " + properties.get("type_attrib") + "\n");
			bw.write("trans_write\n");
			bw.write("task_attrib E " + properties.get("task_attrib_e") + ",M "
					+ properties.get("task_attrib_m") + "\n");
			bw.write("tg_cnt 1\n");
			bw.write("tg_write");

			bw.flush();
			
			MainFrame.statusBar.setStatue("tgff2");
			if (System.getProperties().getProperty("os.name")
					.contains("Window")) {
				MainFrame.statusBar.setStatue(parent + "tgff_windows.exe " + parent + "Task");
				p = Runtime.getRuntime().exec(
						parent + "tgff_windows.exe " + parent + "Task");
			} else {
				if (System.getProperties().getProperty("os.arch")
						.contains("x64")) {
					p = Runtime.getRuntime().exec(
							parent + "tgff_linux_x64 " + parent + "Task");
				} else {
					p = Runtime.getRuntime().exec(
							parent + "tgff_linux_x86 " + parent + "Task");
				}
			}
			p.waitFor();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		this.creatACG();
	}
}