package cn.edu.bit.zzb.mpsoc.mapping;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class StatusBar extends JLabel {

	private static final long serialVersionUID = -4947337355670940704L;
	
	public StatusBar() {
		setStatue("ready");
		setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
	}

	public void setStatue(String statue) {
		this.setText(statue);
	}
		
}
