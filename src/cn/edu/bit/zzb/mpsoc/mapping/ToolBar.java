package cn.edu.bit.zzb.mpsoc.mapping;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cn.edu.bit.zzb.mpsoc.mapping.arithmetic.ga.GA;

import java.net.URL;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {

	private static final long serialVersionUID = 9160267769831416350L;

	protected MainFrame mf;
	
	public JButton buttonACG = null;
	public JButton buttonICG = null;
	public JButton buttonMap = null;
	public JButton buttonFaster = null;
	public JButton buttonSlower = null;
	public JButton buttonTerminate = null;
	public JButton buttonSuspend = null;
	public JButton buttonPlay = null;
	
	public ToolBar(MainFrame mf) {
		super(new BorderLayout());
		
		this.mf = mf;
		JToolBar toolBar = new JToolBar("Tool Bar");
		addButtons(toolBar);
		add(toolBar, BorderLayout.PAGE_START);
	}

	protected void addButtons(JToolBar toolBar) {

		buttonACG = makeButton("New ACG", "New ACG", "Creat a New ACG", "New ACG");
		toolBar.add(buttonACG);

		buttonICG = makeButton("New ICG", "New ICG", "Creat a New ICG", "New ICG");
		toolBar.add(buttonICG);

		buttonMap = makeButton("GA Mapping", "GA Mapping", "Mapp using GA",
				"GA Mapping");
		toolBar.add(buttonMap);
		
		buttonFaster = makeButton("Faster", "Faster", "Faster animal","Faster");
		toolBar.add(buttonFaster);
		
		buttonSlower = makeButton("Slower", "Slower", "Slower animal",
				"Slower");
		toolBar.add(buttonSlower);
		
		buttonTerminate = makeButton("Terminate", "Terminate", "Terminate GA mapping",
				"Terminate");
		toolBar.add(buttonTerminate);
		
		buttonPlay = makeButton("Play", "Play", "Continue GA mapping",
				"Play");
		toolBar.add(buttonPlay);
		
		buttonSuspend = makeButton("Suspend", "Suspend", "Suspend GA mapping",
				"Suspend");
		toolBar.add(buttonSuspend);
		
		this.buttonMap.setEnabled(false);
		this.buttonFaster.setEnabled(false);
		this.buttonSlower.setEnabled(false);
		this.buttonTerminate.setEnabled(false);
		this.buttonSuspend.setEnabled(false);
		this.buttonPlay.setEnabled(false);
	}

	protected JButton makeButton(String imageName, String actionCommand,
			String toolTipText, String altText) {
		String imgLocation = "images/" + imageName + ".gif";
		URL imageURL = ToolBar.class.getResource(imgLocation);

		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this);

		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {
			button.setText(altText);
		}
		return button;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if ("New ACG".equals(cmd)) {
			mf.getView().creatACG();
			if(MainFrame.iCG != null) {
				this.buttonMap.setEnabled(true);
			}
		} else if ("New ICG".equals(cmd)) {
			mf.getView().creatICG();
			if(MainFrame.aCG != null) {
				this.buttonMap.setEnabled(true);
			}
		} else if ("GA Mapping".equals(cmd)) {
			mf.getView().creatGA();
			if(GA.Running) {
				this.buttonACG.setEnabled(false);
				this.buttonICG.setEnabled(false);
				this.buttonMap.setEnabled(false);
				this.buttonFaster.setEnabled(true);
				this.buttonSlower.setEnabled(true);
				this.buttonTerminate.setEnabled(true);
				this.buttonSuspend.setEnabled(true);
				this.buttonPlay.setEnabled(false);
			}
		} else if ("Faster".equals(cmd)) {
			GA.faster();
		} else if ("Slower".equals(cmd)) {
			GA.slower();
		}  else if ("Terminate".equals(cmd)) {
			GA.end();
			this.buttonACG.setEnabled(true);
			this.buttonICG.setEnabled(true);
			this.buttonFaster.setEnabled(false);
			this.buttonSlower.setEnabled(false);
			this.buttonTerminate.setEnabled(false);
			this.buttonSuspend.setEnabled(false);
			this.buttonPlay.setEnabled(false);
			this.buttonMap.setEnabled(true);
		} else if("Suspend".equals(cmd)) {
			GA.suspend();
			this.buttonFaster.setEnabled(false);
			this.buttonSlower.setEnabled(false);
			this.buttonPlay.setEnabled(true);
			this.buttonSuspend.setEnabled(false);
		} else if("Play".equals(cmd)) {
			GA.play();
			this.buttonFaster.setEnabled(true);
			this.buttonSlower.setEnabled(true);
			this.buttonSuspend.setEnabled(true);
			this.buttonPlay.setEnabled(false);
		}

	}
}
