package com.movielate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MerriamWebbsterDictionary {
	public MerriamWebbsterDictionary() {};
	String Definicja(String word) {	
	String definicjaString = null;	
	final String USER_AGENT = "Mozilla/5.0";
	String url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"+word+"?key=cf4af251-7b1a-446a-8699-fbdbd4bfc28d";
	try {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//print result
		String endDictionary = response.toString();
		System.out.println(response.toString());
		JSONParser parser = new JSONParser();
		Object object = parser.parse(endDictionary);
		JSONArray jsonArray = (JSONArray) object;
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		System.out.println(jsonObject.get("shortdef"));
		String yourString =  jsonObject.get("shortdef").toString();
		String[] array = yourString.split("\"");
		definicjaString = array[1];
		for(int i = 1;i<array.length;i=i+2) {
		    System.out.println(array[i]);
		   ;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return definicjaString;
	}

}
