package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga.NAGA;

import com.mxgraph.view.mxGraph;

public class ACGConfigDialog extends ConfigDialog {

	private static final long serialVersionUID = 5531899798866668338L;

	Map<String, String> properties = null;

	private JTextField seed = new JTextField();
	private JTextField task_cnt = new JTextField();
	private JTextField task_type_cnt = new JTextField();
	private JTextField trans_type_cnt = new JTextField();
	private JTextField period_mul = new JTextField();
	private JTextField prob_periodic = new JTextField();
	private JTextField start_node = new JTextField();
	private JTextField prob_multi_start_nodes = new JTextField();
	private JTextField series_must_rejoin = new JTextField();
	private JTextField series_subgraph_fork_out = new JTextField();
	private JTextField series_len = new JTextField();
	private JTextField series_wid = new JTextField();
	private JTextField series_local_xover = new JTextField();
	private JTextField series_global_xover = new JTextField();
	private JTextField type_attrib = new JTextField();
	private JTextField task_attrib_e = new JTextField();
	private JTextField task_attrib_m = new JTextField();

	public ACGConfigDialog(final mxGraph _graph) {
		super(_graph, "ACG Generate");

		properties = new HashMap<String, String>();
		
		MainFrame.aCG = new ACG(_graph, properties);
		
		properties.put("seed", "1");
		properties.put("task_cnt", "5 2");
		properties.put("task_type_cnt", "30");
		properties.put("trans_type_cnt", "50");
		properties.put("period_mul", "1,1.5");
		properties.put("prob_periodic", "1");
		properties.put("start_node", "5 4");
		properties.put("prob_multi_start_nodes", "0.8");
		properties.put("series_must_rejoin", "0");
		properties.put("series_subgraph_fork_out", "0.8");
		properties.put("series_len", "5 4");
		properties.put("series_wid", "5 4");
		properties.put("series_local_xover", "4");
		properties.put("series_global_xover", "8");
		properties.put("type_attrib", "30 20");
		properties.put("task_attrib_e", "70 20");
		properties.put("task_attrib_m", "20 20");

		JPanel panel = new JPanel(new GridLayout(9, 4, 10, 2));
		JLabel jlabel = null;
		jlabel =  new JLabel("Random Seed");
		jlabel.setToolTipText("The seed for the pseudo-random number generator");
		panel.add(jlabel);
		panel.add(seed);
		jlabel =  new JLabel("Task Num");
		jlabel.setToolTipText("The minimum number of tasks per task graph (average, multiplier)");
		panel.add(jlabel);
		panel.add(task_cnt);
		jlabel =  new JLabel("Task Type");
		jlabel.setToolTipText("The number of possible task types");
		panel.add(jlabel);
		panel.add(task_type_cnt);
		jlabel =  new JLabel("Trans Type");
		jlabel.setToolTipText("The number of possible transmit types");
		panel.add(jlabel);
		panel.add(trans_type_cnt);
		jlabel =  new JLabel("Period Num");
		jlabel.setToolTipText("The multipliers for periods in multirate systems");
		panel.add(jlabel);
		panel.add(period_mul);
		jlabel =  new JLabel("Period Pro");
		jlabel.setToolTipText("The probability that a graph is periodic");
		panel.add(jlabel);
		panel.add(prob_periodic);
		jlabel =  new JLabel("Start Num");
		jlabel.setToolTipText("The number of start nodes for graphs which have multiple start nodes (average,multiplier)");
		panel.add(jlabel);
		panel.add(start_node);
		jlabel =  new JLabel("Start Pro");
		jlabel.setToolTipText("The probability that a graph has more than one start node");
		panel.add(jlabel);
		panel.add(prob_multi_start_nodes);
		jlabel =  new JLabel("Must Rejoin");
		jlabel.setToolTipText("If it is 1, force subgraphs formed in series chains to rejoin into the main graph");
		panel.add(jlabel);
		panel.add(series_must_rejoin);
		jlabel =  new JLabel("Fork Out Pro");
		jlabel.setToolTipText("The probability subgraphs will not rejoin");
		panel.add(jlabel);
		panel.add(series_subgraph_fork_out);
		jlabel =  new JLabel("Chain Length");
		jlabel.setToolTipText("The length of series chains (average, multiplier)");
		panel.add(jlabel);
		panel.add(series_len);
		jlabel =  new JLabel("Chain Width");
		jlabel.setToolTipText("The width of series chains (average, multiplier)");
		panel.add(jlabel);
		panel.add(series_wid);
		jlabel =  new JLabel("Local Xover");
		jlabel.setToolTipText("The number of extra local arcs added");
		panel.add(jlabel);
		panel.add(series_local_xover);
		jlabel =  new JLabel("Global Xover");
		jlabel.setToolTipText("The number of extra global arcs added");
		panel.add(jlabel);
		panel.add(series_global_xover);
		jlabel =  new JLabel("Trans Num");
		jlabel.setToolTipText("The number of transmit (average, multiplier)");
		panel.add(jlabel);
		panel.add(type_attrib);
		jlabel =  new JLabel("Exec Time");
		jlabel.setToolTipText("The size of execution time for task(average, multiplier)");
		panel.add(jlabel);
		panel.add(task_attrib_e);
		jlabel =  new JLabel("Mem Cos");
		jlabel.setToolTipText("The size of required memorry for task(average, multiplier)");
		panel.add(jlabel);
		panel.add(task_attrib_m);

		updateFrameValue();

		JPanel panelBorder = new JPanel();
		panelBorder.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBorder.add(panel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		JButton applyButton = new JButton("Generate");
		JButton closeButton = new JButton("Cancel");
		buttonPanel.add(closeButton);
		buttonPanel.add(applyButton);
		getRootPane().setDefaultButton(applyButton);

		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NAGA.Running) {
					JOptionPane.showMessageDialog(null, "You should terminate GA first!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					updateProperties();
					updateFrameValue();
	
					graph.getModel().beginUpdate();
					graph.selectAll();
					graph.removeCells();
	
					Thread t = new Thread(MainFrame.aCG);
					t.start();
					
					graph.getModel().endUpdate();
				}
				setVisible(false);
			}
		});

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		getContentPane().add(panelBorder, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();

		setModal(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation(screenSize.width / 2 - (frameSize.width / 2),
				screenSize.height / 2 - (frameSize.height / 2));
		setResizable(false);
	}

	protected void updateFrameValue() {

		seed.setText(String.valueOf((int) (Math.random() * 62535)));
		task_cnt.setText(properties.get("task_cnt"));
		task_type_cnt.setText(properties.get("task_type_cnt"));
		trans_type_cnt.setText(properties.get("trans_type_cnt"));
		period_mul.setText(properties.get("period_mul"));
		prob_periodic.setText(properties.get("prob_periodic"));
		start_node.setText(properties.get("start_node"));
		prob_multi_start_nodes
				.setText(properties.get("prob_multi_start_nodes"));
		series_must_rejoin.setText(properties.get("series_must_rejoin"));
		series_subgraph_fork_out.setText(properties
				.get("series_subgraph_fork_out"));
		series_len.setText(properties.get("series_len"));
		series_wid.setText(properties.get("series_wid"));
		series_local_xover.setText(properties.get("series_local_xover"));
		series_global_xover.setText(properties.get("series_global_xover"));
		type_attrib.setText(properties.get("type_attrib"));
		task_attrib_e.setText(properties.get("task_attrib_e"));
		task_attrib_m.setText(properties.get("task_attrib_m"));
	}

	protected void updateProperties() {
		properties.clear();

		properties.put("seed", seed.getText());
		properties.put("task_cnt", task_cnt.getText());
		properties.put("task_type_cnt", task_type_cnt.getText());
		properties.put("trans_type_cnt", trans_type_cnt.getText());
		properties.put("period_mul", period_mul.getText());
		properties.put("prob_periodic", prob_periodic.getText());
		properties.put("start_node", start_node.getText());
		properties.put("prob_multi_start_nodes",
				prob_multi_start_nodes.getText());
		properties.put("series_must_rejoin", series_must_rejoin.getText());
		properties.put("series_subgraph_fork_out",
				series_subgraph_fork_out.getText());
		properties.put("series_len", series_len.getText());
		properties.put("series_wid", series_wid.getText());
		properties.put("series_local_xover", series_local_xover.getText());
		properties.put("series_global_xover", series_global_xover.getText());
		properties.put("type_attrib", type_attrib.getText());
		properties.put("task_attrib_e", task_attrib_e.getText());
		properties.put("task_attrib_m", task_attrib_m.getText());

	}

}
