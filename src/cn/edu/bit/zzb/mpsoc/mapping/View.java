package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga.NAGAConfigDialog;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class View extends JPanel {

	private static final long serialVersionUID = 8491424754659119260L;

	protected static mxGraphComponent graphComponentACG = new mxGraphComponent(
			new mxGraph());
	protected static mxGraphComponent graphComponentICG = new mxGraphComponent(
			new mxGraph());

	ICGConfigDialog iCGConfigDialog = null;
	ACGConfigDialog aCGConfigDialog = null;
	NAGAConfigDialog gAConfigDialog = null;

	public View(MainFrame mf) {

		final mxGraph graphACG = graphComponentACG.getGraph();
		final mxGraph graphICG = graphComponentICG.getGraph();

		graphICG.setAllowDanglingEdges(false);
		graphICG.setCellsEditable(false);
		graphICG.setCellsLocked(true);
		graphComponentICG.setConnectable(false);
		graphComponentACG.setConnectable(false);
		graphACG.setAllowDanglingEdges(false);
		graphACG.setCloneInvalidEdges(false);
		graphACG.setCellsEditable(false);
		graphACG.setCellsResizable(false);
		
		graphComponentACG.setToolTips(true);
		graphComponentICG.setToolTips(true);
		
		JSplitPane graphView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				graphComponentACG, graphComponentICG);
		graphView.setOneTouchExpandable(true);
		graphView.setDividerLocation(515);
		graphView.setDividerSize(12);
		graphView.setBorder(null);

		setLayout(new BorderLayout());
		add(graphView, BorderLayout.CENTER);

		installListeners();
	}

	protected void installListeners() {
		MouseWheelListener wheelTracker = new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isControlDown()) {
					if (e.getWheelRotation() < 0) {
						if (e.getComponent() == graphComponentACG) {
							graphComponentACG.zoomIn();
						} else if (e.getComponent() == graphComponentICG) {
							graphComponentICG.zoomIn();
						}
					} else {
						if (e.getComponent() == graphComponentACG) {
							graphComponentACG.zoomOut();
						} else if (e.getComponent() == graphComponentICG) {
							graphComponentICG.zoomOut();
						}
					}
				}
			}

		};

		graphComponentACG.addMouseWheelListener(wheelTracker);
		graphComponentICG.addMouseWheelListener(wheelTracker);
	}

	public Action bindWithACG(String name, final Action action) {
		AbstractAction newAction = new AbstractAction(name) {
			private static final long serialVersionUID = -3507218652228794208L;

			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(new ActionEvent(graphComponentACG, e
						.getID(), e.getActionCommand()));
			}
		};

		newAction.putValue(Action.SHORT_DESCRIPTION,
				action.getValue(Action.SHORT_DESCRIPTION));

		return newAction;
	}

	public Action bindWithICG(String name, final Action action) {
		AbstractAction newAction = new AbstractAction(name) {
			private static final long serialVersionUID = -808468492499267798L;

			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(new ActionEvent(graphComponentICG, e
						.getID(), e.getActionCommand()));
			}
		};

		newAction.putValue(Action.SHORT_DESCRIPTION,
				action.getValue(Action.SHORT_DESCRIPTION));

		return newAction;
	}

	public void creatICG() {
		if (iCGConfigDialog == null)
			iCGConfigDialog = new ICGConfigDialog(graphComponentICG.getGraph());

		this.iCGConfigDialog.launch();
	}

	public void creatACG() {
		if (aCGConfigDialog == null)
			aCGConfigDialog = new ACGConfigDialog(graphComponentACG.getGraph());
		this.aCGConfigDialog.launch();
	}

	public void creatGA() {
		if (gAConfigDialog == null)
			gAConfigDialog = new NAGAConfigDialog();
		this.gAConfigDialog.launch();
	}

	public static void layoutACG() {
		final mxGraph graph = graphComponentACG.getGraph();
		mxIGraphLayout layout = new mxHierarchicalLayout(graph);

		graph.getModel().beginUpdate();
		try {
			layout.execute(graph.getDefaultParent());
		} finally {
			graph.getModel().endUpdate();
		}
	}

}
