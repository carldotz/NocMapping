package cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.bit.zzb.mpsoc.mapping.ACGNode;
import cn.edu.bit.zzb.mpsoc.mapping.ICG;
import cn.edu.bit.zzb.mpsoc.mapping.ICGNode;
import cn.edu.bit.zzb.mpsoc.mapping.MainFrame;

public class NAGAIndividual implements Comparable<NAGAIndividual> {
	public static final double PCROSSOVERMAX = 1;
	public static final double PCROSSOVERMIN = 0.1;
	public static final double PMUTATIONMAX = 0.2;
	public static final double PMUTATIONMIN = 0.01;
	
	public  double pCrossover = 0.6;
	public  double pMutation = 0.05;
	
	protected NAGA ga = null;
		
	private float fitness =0;
	protected ArrayList<ICGNode> genetic = null ;
	
	public ArrayList<ICGNode> getGenetic() {
		return genetic;
	}

	public NAGAIndividual(NAGA ga) {
		genetic = new ArrayList<ICGNode>();
		this.ga = ga;
		initGenetic();
		caculateFitness();
	}
	
	public NAGAIndividual(NAGAIndividual id) {
		this.ga = id.ga;
		genetic = new ArrayList<ICGNode>();
		genetic.addAll(id.genetic);
		this.fitness = id.getFitness();
	}
	
	protected void initGenetic() {
		ArrayList<ICGNode> iCGRange = new ArrayList<ICGNode>();
		iCGRange.addAll(MainFrame.iCG);
		for(int i=0;i<MainFrame.aCG.size();i++) {
			ICGNode iCGNode = null;
			int index = (int)(Math.random()*iCGRange.size());
			iCGNode = iCGRange.get(index);
			genetic.add(iCGNode);
			iCGRange.remove(iCGNode);
		}
	}
	
	public static NAGAIndividual mutation(NAGAIndividual source) {
		NAGAIndividual newIndividual = new NAGAIndividual(source);
		newIndividual.caculateMutationP();
	
		for(int i=0;i<newIndividual.genetic.size();i++) {
			if(Math.random() < newIndividual.pMutation) {
				int index = (int)(Math.random()*MainFrame.iCG.size());
				ICGNode iCGNode = MainFrame.iCG.get(index);
				if(newIndividual.genetic.contains(iCGNode)) {
					int swapIndex = newIndividual.genetic.indexOf(iCGNode);
					ICGNode tempNode = newIndividual.genetic.get(i);
					newIndividual.genetic.remove(i);
					newIndividual.genetic.add(i, iCGNode);
					newIndividual.genetic.remove(swapIndex);
					newIndividual.genetic.add(swapIndex, tempNode);
				} else {
					newIndividual.genetic.remove(i);
					newIndividual.genetic.add(i, iCGNode);
				}
			}
		}
		newIndividual.caculateFitness();
		if(source.getFitness() < newIndividual.getFitness()) return newIndividual;
		else return new NAGAIndividual(source);	
	}
	
	
	public static List<NAGAIndividual> crossover(NAGAIndividual[] hasMated) {
		double pCrossoverT = 0;
		
		if(hasMated[0].fitness > hasMated[1].fitness) {
			hasMated[0].caculateCrossoverP();
			pCrossoverT = hasMated[0].pCrossover;
		} else {
			hasMated[1].caculateCrossoverP();
			pCrossoverT = hasMated[1].pCrossover;
		}
		
		List<NAGAIndividual> result = new ArrayList<NAGAIndividual>(2);
		if(Math.random() < pCrossoverT) {
			NAGAIndividual[] newIndividual = new NAGAIndividual[2];
			newIndividual[0] = new NAGAIndividual(hasMated[0]);
			newIndividual[1] = new NAGAIndividual(hasMated[1]);
			
			int position = (int)(Math.random() * hasMated[0].genetic.size());
			
			for(int i=position;i<hasMated[0].genetic.size();i++) {
				ICGNode tempNodeA = newIndividual[0].genetic.get(i);
				ICGNode tempNodeB = newIndividual[1].genetic.get(i);
				
				if(!newIndividual[0].genetic.contains(tempNodeB)) {
					if(!newIndividual[1].genetic.contains(tempNodeA)) {
						newIndividual[0].genetic.remove(i);
						newIndividual[0].genetic.add(i, tempNodeB);
						newIndividual[1].genetic.remove(i);
						newIndividual[1].genetic.add(i, tempNodeA);
					}
				}
			}
			newIndividual[0].caculateFitness();
			newIndividual[1].caculateFitness();
			//¸¸×Ó´ú¾ºÕù
			NAGAIndividual[] newRange = new NAGAIndividual[4];
			newRange[0] = hasMated[0];
			newRange[1] = hasMated[1];
			newRange[2] = newIndividual[0];
			newRange[3] = newIndividual[1];
			Arrays.sort(newRange);
			result.add(newRange[2]);
			result.add(newRange[3]);
		} else {
			result.add(hasMated[0]);
			result.add(hasMated[1]);
		}
		return result;
	}
	
	public void caculateCrossoverP() {
		if(this.fitness > ga.averageFitness) {
			pCrossover = PCROSSOVERMAX;
			pCrossover -= (PCROSSOVERMAX - PCROSSOVERMIN) * ga.sameFitnessGN / ga.gNNMin;
		} else {
			pCrossover = PCROSSOVERMAX;
		}
	}
	
	public void caculateMutationP() {
		if(this.fitness > ga.averageFitness) {
			pMutation = PMUTATIONMIN;
			pMutation += (PMUTATIONMAX - PMUTATIONMIN) * ga.sameFitnessGN / ga.gNNMin;
		} else {
			pMutation = PMUTATIONMIN;
		}
	}
	
	public void caculateFitness() {
		double comcost = 0;
		List<ACGNode> nextTask = null;
		List<Float> edge = null;
		for(int i=0;i<MainFrame.aCG.size();i++) {
			nextTask = MainFrame.aCG.get(i).getNextTask();
			edge = MainFrame.aCG.get(i).getEdge();
			ICGNode icgNodeA = genetic.get(i);
			for(int j=0;j<nextTask.size();j++) {
				int index = nextTask.get(j).id;
				ICGNode icgNodeB = genetic.get(index);
				float communication = edge.get(j);
				int distance = ICG.distance(icgNodeA,icgNodeB);
				comcost += (distance*communication);
			}
		}
		fitness = (float)(1 / comcost);
	}
	
	public float getFitness() {
		return fitness;
	}
	
	public int compareTo(NAGAIndividual id) {
		if(this.fitness > id.fitness) {
			return 1;
		} else if(this.fitness < id.fitness) {
			return -1;
		} else return 0;
	}

	public void updateShow() {
		MainFrame.iCG.clearACG();
		for(int i=0;i<genetic.size();i++) {
			this.genetic.get(i).setACGNode(MainFrame.aCG.get(i));
		}
		MainFrame.iCG.refresh();
	}
}
