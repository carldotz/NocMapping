package cn.edu.bit.zzb.mpsoc.mapping;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.mxgraph.view.mxGraph;

public class AboutFrame extends JDialog {

	private static final long serialVersionUID = 8479297199516825314L;

	public AboutFrame(Frame owner) {
		super(owner);
		setTitle("About");
		setLayout(new BorderLayout());

		JPanel text = new JPanel();
		text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
		text.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 6));

		text.add(new JLabel("System Message"));
		text.add(new JLabel("  System Name:" + System.getProperty("os.name")));
		text.add(new JLabel("  System Version:"
				+ System.getProperty("os.version")));
		text.add(new JLabel("  System Architecture:"
				+ System.getProperty("os.arch")));
		text.add(new JLabel(" "));
		text.add(new JLabel("Java VM Message"));
		text.add(new JLabel("  JRE Name:" + System.getProperty("java.vm.name")));
		text.add(new JLabel("  JRE Version:"
				+ System.getProperty("java.version")));
		text.add(new JLabel(" "));
		text.add(new JLabel("Plug in"));
		text.add(new JLabel("  mxGraph:" + mxGraph.VERSION));
		text.add(new JLabel("  TGFF:3.5"));
		text.add(new JLabel(" "));
		text.add(new JLabel("Software Message"));
		text.add(new JLabel("  Soft Name:Graducation Project"));
		text.add(new JLabel("  Soft Version:1.0"));
		text.add(new JLabel(" "));
		text.add(new JLabel("Software Developer Message"));
		text.add(new JLabel("  QQ:549032320"));
		text.add(new JLabel("  E-mail:zhang91114@163.com"));
		text.add(new JLabel("  Company:Qinghai University"));
		text.add(new JLabel("          Beijing Institute Of Technology"));

		getContentPane().add(text, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 16));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);
		setResizable(false);
		setSize(340, 420);
	}

	protected JRootPane createRootPane() {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
			}
		}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

}
