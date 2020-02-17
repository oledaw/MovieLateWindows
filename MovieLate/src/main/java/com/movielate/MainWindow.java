package com.movielate;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.opencv.core.Mat;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;
import com.movielate.DictionaryWindow;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class MainWindow extends javax.swing.JFrame {
	JPanel contentPanel;
	JTextField txtEnglishText, txtPolishText;
	JLabel englishIcon, polishIcon;
	JButton btnDatabase, btnSearch, btnAdd, btnDictionary;	
	DatabaseWindow databaseWindow;
	FireStore fireStore;
	static String eng = null;
    static String pl = null;
    static BufferedImage screenBufferedImage = null;
   
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		
		if (netIsAvailable()==false) {
			//custom title, error icon
		JOptionPane.showMessageDialog(new JFrame(),
		    "Sprawdz polaczenie z internetem.",
		    "Inane error",
		    JOptionPane.ERROR_MESSAGE);
		System.exit(1);
		}
		
		
		nu.pattern.OpenCV.loadLocally();
	    Snapshot snapshot = new Snapshot();
		//screenBufferedImage = snapshot.doOne();
	    
		screenBufferedImage = snapshot.multiDisplayMode();
	    Mat screenMat = ImageToolBox.convertBufferedImageToMat(screenBufferedImage);
	    eng = snapshot.doOCR(screenMat);
	   	pl = TranslationText.onTranslate(eng); 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(eng, pl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MainWindow(String englishText, String polishText) {
		windowProperties(englishText);
		contentPanel();
		englishTextView(englishText);
		polishTextView(polishText);
		englishIcon();
		polishIcon();		
		buttonDatabase();
		buttonSearch();
		buttonAdd();
		buttonMerriamWebsterDictionary();
	}
	private void windowProperties(String englishText) {
		setMinimumSize(new Dimension(540, 180));
		setTitle("MoviesLate");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Program-icon.png"));
		setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
		setBounds(100, 100, englishText.length()*8 , 180);
	}
	private void contentPanel() {
		contentPanel = new JPanel();
		contentPanel.setMinimumSize(new Dimension(540, 180));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
	}
	private void buttonMerriamWebsterDictionary() {
		btnDictionary = new JButton("Słownik");
		btnDictionary.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Merriam-Webster-icon.png"));
		btnDictionary.setBackground(new Color(255, 99, 71));
		btnDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DictionaryWindow dictionaryWindow = new DictionaryWindow();
				dictionaryWindow.initialize();
			}
		});
		btnDictionary.setBounds(398, 113, 122, 23);
		contentPanel.add(btnDictionary);
	}
	private void buttonAdd() {
		
		btnAdd = new JButton("Dodaj");
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Add-icon.png"));
		btnAdd.setBounds(282, 113, 106, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eng = txtEnglishText.getText();
				pl = txtPolishText.getText();
				System.out.println(pl);
				fireStore = new FireStore();
				fireStore.connect();
				try {
					fireStore.add(eng, pl);
				} catch (InterruptedException | ExecutionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}	
			}
		});
		contentPanel.add(btnAdd);
	}
	private void buttonSearch() {
		btnSearch = new JButton("Szukaj");
		btnSearch.setBackground(new Color(176, 224, 230));
		btnSearch.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Search-icon.png"));
		btnSearch.setBounds(166, 113, 106, 23);
		contentPanel.add(btnSearch);
	}
	private void buttonDatabase() {
		btnDatabase = new JButton("Baza Danych");
		btnDatabase.setBackground(new Color(176, 196, 222));
		btnDatabase.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Database-icon.png"));
		btnDatabase.setBounds(10, 113, 146, 23);
		btnDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
					databaseWindow = new DatabaseWindow();
					databaseWindow.initialize();
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPanel.add(btnDatabase);
	}
	private void polishIcon() {
		polishIcon = new JLabel();
		polishIcon.setBounds(10, 67, 35, 35);
		polishIcon.setVisible(true);
		polishIcon.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\Polish-icon.png"));
		contentPanel.add(polishIcon);
	}
	private void englishIcon() {
		englishIcon = new JLabel();
		englishIcon.setBounds(10, 11, 35, 35);
		englishIcon.setVisible(true);
		englishIcon.setIcon(new ImageIcon("C:\\Users\\dawid\\eclipse-workspace\\maveen\\img\\English-icon.png"));
		contentPanel.add(englishIcon);
	}
	private void polishTextView(String polishText) {
		txtPolishText = new JTextField();
		txtPolishText.setText(polishText);
		txtPolishText.setBounds(55, 67, polishText.length()*5+100, 35);
		contentPanel.add(txtPolishText);
	}
	private void englishTextView(String englishText) {
		txtEnglishText = new JTextField();
		txtEnglishText.setText(englishText);
		txtEnglishText.setBounds(55, 10, englishText.length()*5+100, 35);
		contentPanel.add(txtEnglishText);
		eng = txtEnglishText.getText();
		txtEnglishText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Translate translate = TranslateOptions.getDefaultInstance().getService();				
			    Translation translation =
				        translate.translate(
				            txtEnglishText.getText(),
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("pl"));	
				txtPolishText.setText(translation.getTranslatedText());
				eng = txtEnglishText.getText();
				pl = txtPolishText.getText();
			}
		});
	}
	
	private static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}
}
