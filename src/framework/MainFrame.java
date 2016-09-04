package framework;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	public MainFrame(){
		setTitle("Aplikacija iz MBRS-a");
		Container content = getContentPane();
		content.setBackground(Color.WHITE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		menu  = new GenericMenu();
		
		ImageIcon image = new ImageIcon("images/java.jpeg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add( label, BorderLayout.CENTER );
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);

		//setJMenuBar(new MyMenuBar());
		add(panel, BorderLayout.CENTER);
		
		
		
	}
}
