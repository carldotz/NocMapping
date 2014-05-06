package cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;






import cn.edu.bit.zzb.mpsoc.mapping.ConfigDialog;
import cn.edu.bit.zzb.mpsoc.mapping.MainFrame;

public class NAGAConfigDialog extends ConfigDialog {
	
	private static final long serialVersionUID = -7414648667840261560L;
	
	int individualNumber = 200; 
	int gNNMin = 1000;
	float gNIMin = 1E-8f;
	
	private JTextField individualNumberJ = new JTextField();
	private JTextField gNNMinJ = new JTextField();
	
	public NAGAConfigDialog() {
		super("NAGA Mapping");
				
		JPanel panel = new JPanel(new GridLayout(4, 2, 2, 2));
		JLabel jlabel = null;
		jlabel =  new JLabel("Individual Num");
		jlabel.setToolTipText("The number of Individual");
		panel.add(jlabel);
		panel.add(individualNumberJ);
		jlabel =  new JLabel("Min GenRec");
		jlabel.setToolTipText("The min number of Genetic record");
		panel.add(jlabel);
		panel.add(gNNMinJ);
		
		updateFrameValue();

		JPanel panelBorder = new JPanel();
		panelBorder.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelBorder.add(panel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(16, 8, 8, 8));

		JButton applyButton = new JButton("Map");
		JButton closeButton = new JButton("Cancel");
		buttonPanel.add(closeButton);
		buttonPanel.add(applyButton);
		getRootPane().setDefaultButton(applyButton);
		
		
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(MainFrame.aCG == null | MainFrame.nAG == null) {
					JOptionPane.showMessageDialog(null, "Please Create a ACG and a ICG first!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(MainFrame.aCG.size() > MainFrame.nAG.size()) {
					JOptionPane.showMessageDialog(null, "The size of ACG must smaller than the size of ICG", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(NAGA.Running) {
					JOptionPane.showMessageDialog(null, "Please Teminate the old GA!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					updateProperties();
					Thread t = new Thread(new NAGA(individualNumber, gNNMin, gNIMin));
					t.start();
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
		individualNumberJ.setText(String.valueOf(this.individualNumber));
		gNNMinJ.setText(String.valueOf(this.gNNMin));
	}
	
	protected void updateProperties() {	
		individualNumber = Integer.parseInt(individualNumberJ.getText());
		gNNMin = Integer.parseInt(gNNMinJ.getText());
	}
	
}
