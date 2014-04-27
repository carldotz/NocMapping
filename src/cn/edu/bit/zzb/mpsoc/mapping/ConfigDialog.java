package cn.edu.bit.zzb.mpsoc.mapping;

import javax.swing.JDialog;

import com.mxgraph.view.mxGraph;

public class ConfigDialog extends JDialog {

	private static final long serialVersionUID = 2157374317113408012L;

	protected mxGraph graph;

	public ConfigDialog(mxGraph _graph, String title) {
		this.graph = _graph;
		this.setTitle(title);
		setVisible(false);
	}

	public ConfigDialog(String title) {
		this.setTitle(title);
		setVisible(false);
	}

	public void launch() {
		setVisible(true);
	}

}
