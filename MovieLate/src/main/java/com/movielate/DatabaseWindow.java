package com.movielate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class DatabaseWindow {

	private static final int NUMER_OF_COLUMNS = 2;
	private static final String TABLE_COLUMN_1 = "Polish";
	private static final String TABLE_COLUMN_0 = "English";
	private static final int TABLE_HEIGHT = 240;
	private static final int TABLE_WIDTH = 610;
	private static final int WINDOW_HEIGHT = 300;
	private static final int WINDOW_WIDTH = 650;
	private JFrame frame;
	private JTable table;
	DefaultTableModel model;
	
	FireStore fireStore;
	
	ArrayList<String> flashcard;


	/**
	 * Create the application.
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public DatabaseWindow() throws InterruptedException, ExecutionException {
		
		fireStore = new FireStore();
		fireStore.connect();
		flashcard = fireStore.readData();
	
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		
		windowProperties();
		setTable();
		fillTable();

	}

	private void fillTable() {
		Object object [] = new Object [NUMER_OF_COLUMNS];
		for (int i=0; i<flashcard.size();i=i+NUMER_OF_COLUMNS) {
			System.out.println("pozycja = "+i+"\n");
			object[0] = flashcard.get(i);
			object[1] = flashcard.get(i+1);
			model.addRow(object);
		}
		table.setModel(model);
	}

	private void setTable() {
		JScrollPane scrollPanel = new JScrollPane();
		scrollPanel.setBounds(10, 10, TABLE_WIDTH, TABLE_HEIGHT);
		frame.getContentPane().add(scrollPanel);

		
		table = new JTable();

		scrollPanel.setViewportView(table);
		
		model = new DefaultTableModel();
		model.addColumn(TABLE_COLUMN_0);
		model.addColumn(TABLE_COLUMN_1);
	}

	private void windowProperties() {
		frame = new JFrame();
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}
}
