package com.movielate;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class CaptureDisplay {
	
	BufferedImage screenshot_BI;
	String ocrText;
	Mat screenshot_Mat;
	
	public CaptureDisplay(){}
	
	BufferedImage doScreenshot() throws IOException {

		try {
			Robot robot = new Robot();

//			screenShot_BI = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			
			screenshot_BI = robot.createScreenCapture(new Rectangle(0, 700, 1920, 680));

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshot_BI;
		
		
	}
	
	String doOCR (Mat screenshot_Mat) {

		Imgproc.cvtColor(screenshot_Mat, screenshot_Mat, Imgproc.COLOR_RGB2GRAY);
		
		Imgproc.threshold(screenshot_Mat, screenshot_Mat, 200, 255, Imgproc.THRESH_BINARY_INV);

		screenshot_BI = ImageToolBox.ConvertMat2BufferedImage(screenshot_Mat);
		//showBufferedImage(screenshot_BI, "elko");
		
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
