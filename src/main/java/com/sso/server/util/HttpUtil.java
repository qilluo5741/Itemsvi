package com.sso.server.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class HttpUtil {
	public static String request_get(String httpUrl) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(20000);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	       // e.printStackTrace();
	    	return null;
	    }
	    return result;
	}
	public static String request_post(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(20000);
	        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
