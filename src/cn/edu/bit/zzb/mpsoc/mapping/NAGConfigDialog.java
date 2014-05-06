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

import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphGenerator;
import com.mxgraph.analysis.mxGraphProperties;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.costfunction.mxDoubleValCostFunction;
import com.mxgraph.view.mxGraph;

public class NAGConfigDialog extends ConfigDialog {

	private static final long serialVersionUID = 314415782483658529L;

	private int numRows = 8;
	private int numColumns = 8;
	private float gridSpacing = 40;

	private mxAnalysisGraph aGraph;

	private JTextField numRowsField = new JTextField();
	private JTextField numColumnsField = new JTextField();

	public NAGConfigDialog(final mxGraph _graph) {

		super(_graph, "ICG Generate");
		MainFrame.nAG = new NAG(_graph);

		this.aGraph = new mxAnalysisGraph();
		this.numRowsField.setText(String.valueOf(numRows));
		this.numColumnsField.setText(String.valueOf(numColumns));

		JPanel panel = new JPanel(new GridLayout(2, 2, 20, 10));
		JLabel jlabel = null;
		jlabel =  new JLabel("Rows");
		jlabel.setToolTipText("Number of rows");
		panel.add(jlabel);
		panel.add(numRowsField);
		jlabel =  new JLabel("Columns");
		jlabel.setToolTipText("Number of columns");
		panel.add(jlabel);
		panel.add(numColumnsField);

		JPanel panelBorder = new JPanel();
		panelBorder.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBorder.add(panel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(16, 8, 8, 8));

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
					int yDim = Integer.parseInt(numRowsField.getText());
					int xDim = Integer.parseInt(numColumnsField.getText());
	
					numRows = (yDim < 1) ? 1 : yDim;
					numColumns = (xDim < 1) ? 1 : xDim;
	
					graph.getModel().beginUpdate();
					graph.selectAll();
					graph.removeCells();
	
					MainFrame.nAG.clear();
	
					mxGraphGenerator generator = new mxGraphGenerator(
							mxGraphGenerator.getGeneratorFunction(graph, false, 0,
									0), new mxDoubleValCostFunction());
					Map<String, Object> props = new HashMap<String, Object>();
					mxGraphProperties.setDirected(props, false);
					aGraph.setGraph(graph);
					aGraph.setGenerator(generator);
					aGraph.setProperties(props);
	
					generator.getGridGraph(aGraph, numColumns, numRows);
					generator.setGridGraphSpacing(aGraph, gridSpacing, gridSpacing,
							numColumns, numRows);
					mxGraphStructure.setDefaultGraphStyle(aGraph, false);
	
					
					graph.getModel().endUpdate();
					
					MainFrame.nAG.setRC(numColumns, numRows);
					Thread t = new Thread(MainFrame.nAG);
					t.run();
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

}
