package cn.edu.bit.zzb.mpsoc.mapping;

import java.util.ArrayList;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class NAG extends ArrayList<NAGNode>  implements Runnable{

	private static final long serialVersionUID = 8785659070118589304L;
	
	private int numColumns = 8;
	private int numRows = 8;
	
	mxGraph graph = null;

	public NAG(mxGraph graph) {
		super();
		this.graph = graph;
	}
	
	public void setRC(int numColumns, int numRows) {
		this.numColumns = numColumns;
		this.numRows = numRows;
	}

	public void creatICG() {
		MainFrame.statusBar.setStatue("Clearing the old ICG");
		MainFrame.nAG.clear();
		MainFrame.statusBar.setStatue("Creat a new ICG");
		for (int i = 0; i < numColumns; i++) {
			for (int j = 0; j < numRows; j++)
				this.add(new NAGNode(i, j, graph));
		}
		bandWithGraph();
		MainFrame.statusBar.setStatue("Ready");
	}

	public void bandWithGraph() {
		graph.selectCells(true, false);
		graph.selectVertices();
		Object[] vertices = graph.getSelectionCells();

		for (int i = 0; i < this.size(); i++) {
			mxCell v = ((mxCell) (vertices[i]));
			v.setStyle("strokeColor=black;fillColor=orange");
			v.setValue(this.get(i));
		}
		graph.clearSelection();
		graph.refresh();
	}

	public void clearACG() {
		for (int i = 0; i < this.size(); i++)
			this.get(i).setACGNode(null);
	}

	public void refresh() {
		graph.refresh();
	}

	public static int distance(NAGNode a, NAGNode b) {
		int distance = 0;
		distance += ((a.x > b.x) ? a.x - b.x : b.x - a.x);
		distance += ((a.y > b.y) ? a.y - b.y : b.y - a.y);
		return distance;
	}

	public void run() {
		creatICG();		
	}

}
