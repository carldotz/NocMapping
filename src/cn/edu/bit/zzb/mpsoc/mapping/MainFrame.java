package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1939029107269904891L;

	public View view = null;
	public static StatusBar statusBar = null; 
	public static ACG aCG = null;
	public static NAG nAG = null;
	public static ToolBar toolBar = null;
	public static MenuBar menuBar = null;

	public View getView() {
		return view;
	}

	public static void main(String[] args) {
		StartWindow sf = new StartWindow();
		MainFrame mf = new MainFrame();
		sf.dispose();
		mf.launchFrame();
	}

	private void launchFrame() {
		setVisible(true);
	}

	public Action bind(String name, final Action action) {
		AbstractAction newAction = new AbstractAction(name) {

			private static final long serialVersionUID = 5219830575290751133L;

			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(new ActionEvent(this, e.getID(), e
						.getActionCommand()));
			}
		};

		newAction.putValue(Action.SHORT_DESCRIPTION,
				action.getValue(Action.SHORT_DESCRIPTION));

		return newAction;
	}

	public MainFrame() {
		super("MPSoC Mapping");
		ImageIcon icon=new ImageIcon(StartWindow.class.getResource("images/ico.gif" ));
		this.setIconImage(icon.getImage());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.setLayout(new BorderLayout());
		view = new View(this);
		getContentPane().add(view,BorderLayout.CENTER);
		statusBar = new StatusBar();
		getContentPane().add(statusBar,BorderLayout.SOUTH);
		toolBar = new ToolBar(this);
		getContentPane().add(toolBar,BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new MenuBar(this);
		setJMenuBar(new MenuBar(this));
		setSize(800, 600);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation(screenSize.width / 2 - (frameSize.width / 2),
				screenSize.height / 2 - (frameSize.height / 2));

	}

	public void about() {

		AboutFrame about = new AboutFrame(this);
		about.setModal(true);

		int x = this.getX() + (this.getWidth() - about.getWidth()) / 2;
		int y = this.getY() + (this.getHeight() - about.getHeight()) / 2;
		about.setLocation(x, y);

		about.setVisible(true);
	}

	public void exit() {
		this.dispose();
	}
	

}
