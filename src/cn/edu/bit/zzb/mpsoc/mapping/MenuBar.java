package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -7319328629845083310L;

	private JMenu menu = null;

	public MenuBar(final MainFrame mf) {
		menu = add(new JMenu("File"));
		menu.add(mf.bind("Exit", new AbstractAction() {

			private static final long serialVersionUID = -4205166249991052977L;

			public void actionPerformed(ActionEvent arg0) {
				mf.exit();
			}
		}));
		
		menu = add(new JMenu("Help"));
		menu.add(mf.bind("About", new AbstractAction() {
			private static final long serialVersionUID = 9064785841573864944L;

			public void actionPerformed(ActionEvent arg0) {
				mf.about();
			}
		}));

	}
	
};