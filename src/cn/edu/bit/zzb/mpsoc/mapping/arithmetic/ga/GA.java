package cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import cn.edu.bit.zzb.mpsoc.mapping.MainFrame;
import cn.edu.bit.zzb.mpsoc.mapping.MappingArithmetic;

@SuppressWarnings("serial")
public class GA extends ArrayList<Individual> implements MappingArithmetic {

	public static boolean Running = false;
	public static int sleepTime = 1000;
	public int gNMax = 0;
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

	public GA(int indiviadualNumber, int gNMax, int gNNMin, float gNIMin) {
		this.gNMax = gNMax;
		this.gNNMin = gNNMin;
		this.gNIMin = gNIMin;

		int i;
		for (i = 0; i < indiviadualNumber; i++) {
			this.add(new Individual(this));
		}
	}

	protected Individual rouletteWheelSelection(List<Individual> c) {
		double sum = 0;
		for (Individual id : c) {
			sum += id.getFitness();
		}
		double randomNumber = Math.random() * sum;
		sum = 0;
		for (Individual id : c) {
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
		MainFrame.iCG.clearACG();// 初始化
		geneticNumber = 0;
		bestFitness = Collections.max(this).getFitness();
		while (!shouldBeEnd()) {
			// 选择
			List<Individual> hasSelected = new LinkedList<Individual>();
			while (hasSelected.size() < this.size()) {
				hasSelected.add(rouletteWheelSelection(this));
			}

			// 交叉
			Individual[][] hasMated = new Individual[this.size() / 2][2];
			List<Individual> newGenetic = new ArrayList<Individual>();
			for (int i = 0; i < hasMated.length; i++) {
				for (int j = 0; j < 2; j++) {
					hasMated[i][j] = hasSelected
							.get((int) (Math.random() * hasSelected.size()));
				}
				newGenetic.addAll(Individual.crossover(hasMated[i]));
			}
			this.clear();
			// 变异
			for (int i = 0; i < newGenetic.size(); i++) {
				this.add(Individual.mutation(newGenetic.get(i)));
			}

			double sumFitness = 0;
			for (Individual id : this) {
				sumFitness += id.getFitness();
			}
			averageFitness = (float) (sumFitness / this.size());

			geneticNumber++;
			// 最优值变化则刷新
			if (bestFitness < Collections.max(this).getFitness()) {
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
		if (geneticNumber > gNMax)
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
		sleepTime = sleepTime < 100 ? 0 : sleepTime - 100;
	}

	public static void slower() {
		sleepTime += 100;
	}

}
