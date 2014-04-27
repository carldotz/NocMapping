package cn.edu.bit.zzb.mpsoc.mapping;

import java.util.ArrayList;
import java.util.List;

public class ACGNode {

	public int id;
	private float execTime;
	private float memory;
	private int initInput;
	private int initOutput;
	private int input;

	private List<ACGNode> nextTask;
	private List<Float> edge;

	public ACGNode(int _id, float _execTime, float _memory) {
		this.id = _id;
		this.execTime = _execTime;
		this.memory = _memory;
		this.initInput = 0;
		this.initOutput = 0;
		this.nextTask = new ArrayList<ACGNode>();
		this.edge = new ArrayList<Float>();
	}

	public void addEdge(ACGNode next, float communication) {
		this.nextTask.add(next);
		this.edge.add(communication);
		this.initOutput += 1;
		next.initInput += 1;
		next.input += 1;
	}

	public float getExecTime() {
		return execTime;
	}

	public float getMemory() {
		return memory;
	}

	public int getInitInput() {
		return initInput;
	}

	public int getInitOutput() {
		return initOutput;
	}

	public int getInput() {
		return input;
	}

	public List<ACGNode> getNextTask() {
		return nextTask;
	}

	public List<Float> getEdge() {
		return edge;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "" + id;
	}

}
