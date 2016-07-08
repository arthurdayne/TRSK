package com.sh_trisk.fxkz;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	private final static String NET_ERROR= "AÕ¯¬ÁÕ®–≈“Ï≥£";
	
	public static String getJson(String s){
		String IP = "http://139.196.177.141:80/VMonitor" + s;
		System.out.println("3"+IP);
		InputStream is = null;
		HttpURLConnection huc = null;
		String result ="";
		int responseCode = 0;
		try{
			URL url = new URL(IP);
			huc = (HttpURLConnection) url.openConnection();
			huc.setConnectTimeout(3000);
			huc.setDoInput(true);
			huc.setRequestMethod("GET");
			System.out.println("4");
			responseCode = huc.getResponseCode();
			System.out.println("5ResponseCode:" + responseCode);
			if(responseCode == 200){
				is = huc.getInputStream();
			}
			byte[] data = new byte[1024];
			int len = 0;
			while((len = is.read(data))!=-1){
				result=result+new String(data,0,len);
			}
//			System.out.println("result:"+result);
			
		} catch(IOException e){
			e.printStackTrace();
			System.out.println("responseCode:"+responseCode);
			return NET_ERROR;
		}
		
		return result;
	}

}
