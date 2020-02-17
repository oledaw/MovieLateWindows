package com.movielate;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Snapshot {
	BufferedImage screenshot_BI;
	String ocrText;
	Mat screenshot_Mat;
	int selectedIDDisplay;
	public Snapshot(){}
	
	
	void inicializeConfigFile() {
    	try {
    	    FileInputStream fstream = new FileInputStream("config.txt");
    	    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	    String strLine;         
    	    int lineNumber = 0;
    	    while ((strLine = br.readLine()) != null) {
    	        lineNumber++;
    	        strLine = strLine.replaceAll("[^0-9]+", " ").trim();
    	        selectedIDDisplay = Integer.parseInt(strLine);	        
    	    }       
    	} catch (Exception e) {// Catch exception if any
    	    System.err.println("Error: " + e.getMessage());
    	}
	}
	
	
	BufferedImage multiDisplayMode() throws IOException {
		
		inicializeConfigFile();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gDevs = ge.getScreenDevices();
            DisplayMode mode = gDevs[selectedIDDisplay].getDisplayMode();
            Rectangle bounds = gDevs[selectedIDDisplay].getDefaultConfiguration().getBounds();
            System.out.println(gDevs[selectedIDDisplay].getIDstring());
            System.out.println("Min : (" + bounds.getMinX() + "," + bounds.getMinY() + ") ;Max : (" + bounds.getMaxX()
                    + "," + bounds.getMaxY() + ")");
            System.out.println("Width : " + mode.getWidth() + " ; Height :" + mode.getHeight());
            try {
                Robot robot = new Robot();

               screenshot_BI = robot.createScreenCapture(new Rectangle((int) bounds.getMinX(),
                        (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight()));
                
//                ImageIO.write(screenshot_BI, "png",
//                        new File("src/screen_" + gDevs[selectedIDDisplay].getIDstring().replace("\\", "") + ".png"));

            } catch (AWTException e) {
                e.printStackTrace();
            }
            return screenshot_BI;
	}
	
	
	
	
	BufferedImage doOne() throws IOException {
		try {
			Robot robot = new Robot();
			screenshot_BI = robot.createScreenCapture(new Rectangle(0, 700, 1920, 680));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshot_BI;	
	}
	String doOCR (Mat screenshot_Mat) {
		Imgproc.cvtColor(screenshot_Mat, screenshot_Mat, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(screenshot_Mat, screenshot_Mat, 190, 255, Imgproc.THRESH_BINARY_INV);
		screenshot_BI = ImageToolBox.ConvertMat2BufferedImage(screenshot_Mat);
		//showBufferedImage("elko");
		Tesseract tesseract = new Tesseract();
		try {
			tesseract.setDatapath("C:/Users/dawid/Desktop/tessdata-master");		
			ocrText = tesseract.doOCR(screenshot_BI);
			System.out.print(ocrText);
		 } catch (TesseractException e) {		
			e.printStackTrace();
		};
		return ocrText;  
	}
	Mat readFile(String file) {
		//String file ="C:/Users/dawid/eclipse-workspace/Opencv+/myScreenShot.png"; 
		screenshot_Mat = Imgcodecs.imread(file);
		return screenshot_Mat;
	}
	void showBufferedImage(String title) {
		 JFrame frame = new JFrame(title);
		 ImageIcon icon = new ImageIcon(screenshot_BI);
		 Image image = icon.getImage();
		 Image newimage = image.getScaledInstance(720, 480, Image.SCALE_SMOOTH);
		 icon = new ImageIcon(newimage);
		 JLabel label = new JLabel(icon);
		 frame.add(label);
		 frame.setSize(720, 480);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.pack();
		 frame.setVisible(true);
	}

}
