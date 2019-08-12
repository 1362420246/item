package com.telecomyt.item.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPost {
	
	public static String addJson(String uri, String obj, String charset) {
		StringBuffer sb = new StringBuffer("");
		try {
			// 创建连接
			URL url = new URL(uri);
			System.out.println("uri="+uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			System.out.println("请求参数："+obj.toString());
			out.write(obj.toString().getBytes(charset));
			out.flush();
			out.close();
			// 读取响应
			int status = connection.getResponseCode();
			BufferedInputStream in;
			if (status >= 400 ) {
			    in = new BufferedInputStream( connection.getErrorStream() );
			} else {
			    in = new BufferedInputStream( connection.getInputStream() );
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}


}
