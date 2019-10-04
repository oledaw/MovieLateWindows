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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;



public class DictionaryWindow extends JFrame {
	
	JFrame frame;
		
	private static final int WINDOW_HEIGHT = 300;
	private static final int WINDOW_WIDTH = 650;
	
	private JTextField searchingWord;
	JLabel dictionarydefiniton;
	
	public DictionaryWindow() {

	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		windowProperties();
		
		setDefinitionField();
		
		setSearchingWordField();


	}

	private void setSearchingWordField() {
		searchingWord = new JTextField();
		searchingWord.setHorizontalAlignment(SwingConstants.CENTER);
		searchingWord.setText("searching word...");
		frame.getContentPane().add(searchingWord, BorderLayout.NORTH);
		searchingWord.setColumns(10);
		searchingWord.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String inputString = searchingWord.getText();
				MerriamWebbsterDictionary merriamWebbsterDictionary = new MerriamWebbsterDictionary();
				
				dictionarydefiniton.setText("<html>"+merriamWebbsterDictionary.Definicja(inputString)+"</html>");
				
				System.out.println("DEFNINICJA: " + merriamWebbsterDictionary.Definicja(inputString));
			}
		});
	}

	private void setDefinitionField() {
		dictionarydefiniton = new JLabel("Definiton");
		dictionarydefiniton.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(dictionarydefiniton);
	}
	
	
	private void windowProperties() {
		frame = new JFrame();
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
}
