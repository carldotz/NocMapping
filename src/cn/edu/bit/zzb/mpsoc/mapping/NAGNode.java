package cn.edu.bit.zzb.mpsoc.mapping;

import com.mxgraph.view.mxGraph;

public class NAGNode {
	int x;
	int y;

	ACGNode acgNode = null;
	mxGraph graph = null;

	public NAGNode(int i, int j, mxGraph graph) {
		this.x = i;
		this.y = j;

		this.graph = graph;
	}

	public synchronized void setACGNode(ACGNode acgNode) {
		this.acgNode = acgNode;
	}

	public synchronized String toString() {
		if (acgNode == null) {
			return "";
		} else
			return "" + acgNode;
	}

}
