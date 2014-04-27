package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class StartWindow extends JWindow {

	private static final long serialVersionUID = -3296287625042743424L;

	public StartWindow() {
		add(new CInstead());
		setSize(480,320);
		setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation(screenSize.width / 2 - (frameSize.width / 2),
				screenSize.height / 2 - (frameSize.height / 2));
	}
	 
	class CInstead extends JPanel
	{ 
		private static final long serialVersionUID = -4874787335066454946L;
		
		ImageIcon icon;
		Image img;
		
		public CInstead()
		{ 
			icon=new ImageIcon(StartWindow.class.getResource("images/start.gif" ));
			img=icon.getImage();
		} 
		
		public void paintComponent(Graphics g)
		{ 
			super.paintComponent(g);
			g.drawImage(img,0,0,null );
		} 
	} 
	
	
}


