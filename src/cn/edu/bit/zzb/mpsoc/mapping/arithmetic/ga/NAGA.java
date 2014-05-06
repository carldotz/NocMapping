package cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import cn.edu.bit.zzb.mpsoc.mapping.MainFrame;
import cn.edu.bit.zzb.mpsoc.mapping.MappingArithmetic;

public class NAGA extends ArrayList<NAGAIndividual> implements MappingArithmetic {

	private static final long serialVersionUID = -5818364744633644873L;
	public static boolean Running = false;
	public static int sleepTime = 1000;
	public int gNNMin = 0;
	public float gNIMin = 0;

	public float bestFitness = 0;
	public float averageFitness = 0;
	public int sameFitnessGN = 0;
	public int geneticNumber = 0;
	private static boolean suspend = false;
	private static boolean gAEnd = false;

	public static void end() {
		gAEnd = true;
	}

	public static void suspend() {
		suspend = true;
	}

	public static void play() {
		suspend = false;
	}

	public NAGA(int indiviadualNumber, int gNNMin, float gNIMin) {
		this.gNNMin = gNNMin;
		this.gNIMin = gNIMin;

		int i;
		for (i = 0; i < indiviadualNumber; i++) {
			this.add(new NAGAIndividual(this));
		}
	}

	protected NAGAIndividual rouletteWheelSelection(List<NAGAIndividual> c) {
		double sum = 0;
		for (NAGAIndividual id : c) {
			sum += id.getFitness();
		}
		double randomNumber = Math.random() * sum;
		sum = 0;
		for (NAGAIndividual id : c) {
			sum += id.getFitness();
			if (sum > randomNumber) {
				return id;
			}
		}
		return c.get(0);
	}

	public void run() {
		Running = true;
		sleepTime = 1000;
		gAEnd = false;
		suspend = false;
		MainFrame.nAG.clearACG();// 初始化
		geneticNumber = 0;
		bestFitness = Collections.max(this).getFitness();
		while (!shouldBeEnd()) {
			
			NAGAIndividual bestIndividual = Collections.max(this);
			
			double sumFitness = 0;
			for (NAGAIndividual id : this) {
				sumFitness += id.getFitness();
			}
			averageFitness = (float) (sumFitness / this.size());
			
			// 选择
			List<NAGAIndividual> hasSelected = new LinkedList<NAGAIndividual>();
			while (hasSelected.size() < this.size()) {
				hasSelected.add(rouletteWheelSelection(this));
			}

			// 交叉
			NAGAIndividual[][] hasMated = new NAGAIndividual[this.size() / 2][2];
			List<NAGAIndividual> newGenetic = new ArrayList<NAGAIndividual>();
			for (int i = 0; i < hasMated.length; i++) {
				for (int j = 0; j < 2; j++) {
					hasMated[i][j] = hasSelected
							.get((int) (Math.random() * hasSelected.size()));
				}
				newGenetic.addAll(NAGAIndividual.crossover(hasMated[i]));
			}
			this.clear();
			// 变异
			for (int i = 0; i < newGenetic.size(); i++) {
				this.add(NAGAIndividual.mutation(newGenetic.get(i)));
			}

			geneticNumber++;
			// 最优值变化则刷新
			
			if(Collections.max(this).getFitness() < bestIndividual.getFitness()) {
				this.remove(Collections.min(this));
				this.add(bestIndividual);
			}

			if (Collections.max(this).getFitness() - bestFitness > gNIMin) {
				bestFitness = Collections.max(this).getFitness();
				Collections.max(this).updateShow();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sameFitnessGN = 0;
			} else
				sameFitnessGN++;
		}

		Running = false;
		if (!gAEnd) {
			MainFrame.toolBar.buttonTerminate.doClick();
			JOptionPane.showMessageDialog(null,
					"GA Mapping finished Best is G " + (geneticNumber - gNNMin)
							+ "   C " + 1 / bestFitness, "GA finished",
					JOptionPane.INFORMATION_MESSAGE);
			MainFrame.statusBar.setStatue("Ready");
		}
	}

	protected boolean shouldBeEnd() {
		MainFrame.statusBar.setStatue("GA Mapping: G " + geneticNumber
				+ "   C " + 1 / bestFitness);
		if (sameFitnessGN > gNNMin)
			return true;
		if (gAEnd)
			return true;

		while (suspend) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static void faster() {
		sleepTime = sleepTime < 100 ? 10 : sleepTime - 100;
	}

	public static void slower() {
		sleepTime += 100;
	}

}
