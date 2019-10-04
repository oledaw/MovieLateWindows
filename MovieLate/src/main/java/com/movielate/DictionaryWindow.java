package com.movielate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class DictionaryWindow extends JFrame {
	
	JFrame jFrame = new JFrame();
	JPanel jPanel = new JPanel();
	JLabel jLabel1 = new JLabel("null", SwingConstants.CENTER);
	JLabel jLabel2 = new JLabel("null", SwingConstants.CENTER);
	JLabel jLabel3 = new JLabel();
	JTextField jTextField = new JTextField(10);
	
	public DictionaryWindow(String text1, String text2) {
	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		

		Font ff1 = new Font("TimesRoman",Font.BOLD,25);
		Font ff2 = new Font("TimesRoman",Font.ITALIC,25);
		Font ff3 = new Font("TimesRoman",Font.PLAIN,15);
		
		jLabel1.setText("ENG: "+ text1);
		jLabel1.setFont(ff1);
		jLabel1.setBounds(0, 10, text1.length()*15, 30);

		jLabel2.setText("PL: " +  text2);
		jLabel2.setFont(ff2);
		jLabel2.setBounds(0, 60, text1.length()*15, 30);

		
		jLabel3.setText("EN Dictionary");
		jLabel3.setFont(ff3);
		jLabel3.setBounds(10, 150, 300, 30);

		
		JFrame f = new JFrame();
		f.setSize(text1.length()*15, 300);
		f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
		
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jTextField.setBounds(10, 115, 200, 30);
		f.add(jTextField);
		f.add(jLabel1);
		f.add(jLabel2);
		f.add(jLabel3);
		f.setLayout(null);
		f.setVisible(true);
		
		jTextField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String inputString = jTextField.getText();
				MerriamWebbsterDictionary httpRequest = new MerriamWebbsterDictionary();
				
				jLabel3.setText(httpRequest.Definicja(inputString));
				
				System.out.println("DEFNINICJA: " + httpRequest.Definicja(inputString));
			}
		});
		
		
		
		
	

		
		
		


		

	}

}
