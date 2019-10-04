package com.movielate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lowagie.text.pdf.PdfPublicKeyRecipient;

import ch.qos.logback.core.joran.conditional.ThenAction;

public class DatabaseConnection {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/fiszki?useUnicode=yes&characterEncoding=UTF-8";
	String username = "root";
	String password = "";
	
	public DatabaseConnection () {
	
	}
	public Connection getConnection() throws Exception {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {System.out.println(e);
		}
		return null;
		
	}
	

	public void getData(String query) {
		Statement statement;
		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery(query);
			
			while (result.next()) {
				if (result.isLast()) {
				String id = result.getString("id");
				String eng = result.getString("eng");
				String pl = result.getString("pl");
				
				System.out.println("id: "+ id + ", eng: " + eng + ", pl: " + pl + "\n");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public void addData(String eng, String pl) throws SQLException, Exception {
		Statement statement;
			statement = getConnection().createStatement();
			statement.executeUpdate("INSERT INTO fiszki.fiszki_tab (eng, pl) VALUES "
					+ "('"+eng+"','"+pl+"')");
			
	
}


}

