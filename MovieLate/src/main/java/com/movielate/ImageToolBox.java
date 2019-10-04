package com.movielate;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageToolBox {

	
	static BufferedImage ConvertMat2BufferedImage (Mat screenshot_Mat ) {
		
		BufferedImage screenshot_BI;
		
		int columns = screenshot_Mat.cols();
		int rows = screenshot_Mat.rows();
		int elementSize = (int)screenshot_Mat.elemSize();
		byte [] data = new byte[columns*rows*elementSize];
		int type = 0;
		
		screenshot_Mat.get(0, 0, data);
		
		switch (screenshot_Mat.channels()) {  
        case 1:  
          type = BufferedImage.TYPE_BYTE_GRAY;  
          break;  
        case 3:   
          type = BufferedImage.TYPE_3BYTE_BGR;  
          // bgr to rgb  
          byte b;  
          for(int i=0; i<data.length; i=i+3) {  
            b = data[i];  
            data[i] = data[i+2];  
            data[i+2] = b;  
          }
          
          break;
		}
		
		screenshot_BI = new BufferedImage(columns, rows, type);
		screenshot_BI.getRaster().setDataElements(0, 0, columns, rows, data);
		return screenshot_BI;
	}
	
	static Mat convertBufferedImageToMat (BufferedImage screenshot_BI){
		
		Mat screenshot_Mat;
		
		int rows = screenshot_BI.getHeight();
		int columns = screenshot_BI.getWidth();
		int [] data = screenshot_BI.getRGB(0, 0, columns, rows, null, 0, columns);
		
		byte[] bytesArray = new byte[columns*rows*3];
		
		int j=0;
		
		for (int i=0; i< bytesArray.length; i=i+3) {
			bytesArray[i]= (byte) (data[j]& 0xFF);
			bytesArray[i+1]= (byte) ((data[j]& 0xFF00)>>8);
			bytesArray[i+2]= (byte) ((data[j]& 0xFF0000)>>16);
			j++;
		}
	    		
		screenshot_Mat = new Mat(rows, columns, CvType.CV_8UC3);
		screenshot_Mat.put(0, 0, bytesArray);
		return screenshot_Mat;
		
	}

	static void RGB(BufferedImage screenshot_BI) {
		
		Mat screenshot_Mat;
		
		int rows = screenshot_BI.getHeight();
		int columns = screenshot_BI.getWidth();
		int [] data = screenshot_BI.getRGB(0, 0, columns, rows, null, 0, columns);
		System.out.println("Surowy pixel 0 z tablicy []data " + data[0]);
		
		int color = screenshot_BI.getRGB(0, 0);
		System.out.println("Surowy pixel 0 z uzyciem metody getRGB: " + color);
	
		int red = (data[0] & 0xFF0000) >> 16;
        int redBeforeBitwiseOperation = (data[0] & 0xFF0000);
        int green = (data[0] & 0xFF00) >> 8;
        int blue = (data[0] & 0xFF);
        
        System.out.println("Red z tablicy []data: " + red);
		System.out.println("Green z tablicy []data: "+ green);
		System.out.println("Blue z tablicy []data: "+ blue);
		System.out.println("BITOWA wartość pixela 0,0 z użyciem metody getRGB: "+Integer.toBinaryString(color));
		System.out.println("BITOWA wartość Red przed operacją bitową: "+Integer.toBinaryString(redBeforeBitwiseOperation));
		System.out.println("BITOWA wartość Red: "+Integer.toBinaryString(red));
		
		
		Color color1 = new Color(screenshot_BI.getRGB(0, 0));
		System.out.println("Red z użyciem metody getRED: "+color1.getRed());
		System.out.println("Green z użyciem metody getGreen: "+color1.getGreen());
		System.out.println("Blue z użyciem metody getBlue: "+color1.getBlue());
		
		
		System.out.println("Rozmiar obrazu Width x Height = " + columns+ " x " + rows);
		

		byte[] bytesArray = new byte[columns*rows*3];
		
		int j=0;
		
		for (int i=0; i< bytesArray.length; i=i+3) {
			bytesArray[i]= (byte) (data[j]& 0xFF);
			bytesArray[i+1]= (byte) ((data[j]& 0xFF00)>>8);
			bytesArray[i+2]= (byte) ((data[j]& 0xFF0000)>>16);
			j++;
		}
	    
			
		System.out.println("bytearray elements= "+ data.length );
	    
		System.out.println("Blue: "+ bytesArray[0]);
		System.out.println("Green: "+ bytesArray[1]);
		System.out.println("Red: "+ bytesArray[2]);
		
		
		screenshot_Mat = new Mat(rows, columns, CvType.CV_8UC3);
		screenshot_Mat.put(0, 0, bytesArray);
		
	}
	
	static void showBufferedImage(BufferedImage bufferedImage, String title) {
		 JFrame frame = new JFrame(title);
		 ImageIcon icon = new ImageIcon(bufferedImage);
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
