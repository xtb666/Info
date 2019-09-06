package com.togest.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpURLUtil {

	public static HttpURLConnection getURLConnection(String urlStr) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(HttpURLConnection conn) {
		if (conn != null) {
			conn.disconnect();
			conn = null;
		}
	}

	public static String getContentType(File file) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String str = fileNameMap.getContentTypeFor(file.getAbsolutePath());
		return str;

	}
	//新增的方法
	 public static File downloadFile(String urlPath, String downloadDir,String fileName) {
         File file = null;
       try {
             // 统一资源
             URL url = new URL(urlPath);
             // 连接类的父类，抽象类
             URLConnection urlConnection = url.openConnection();
             // http的连接类
             HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
             // 设定请求的方法，默认是GET
             httpURLConnection.setRequestMethod("GET");
             
             httpURLConnection.setDoOutput(true);
             httpURLConnection.setDoInput(true);
             // 设置字符编码
             httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
             BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
             String path = downloadDir + File.separatorChar + fileName;
             file = new File(path);
             if (!file.getParentFile().exists()) {
                 file.getParentFile().mkdirs();
             }
            OutputStream out = new FileOutputStream(file);
             int size = 0;
             int len = 0;
             byte[] buf = new byte[1024];
             while ((size = bin.read(buf)) != -1) {
                len += size;
                 out.write(buf, 0, size);
                 // 打印下载百分比
                 // System.out.println("下载了-------> " + len * 100 / fileLength +
                 // "%\n");
            }
            bin.close();
            out.close();
         } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } finally {
            
         }
       return file;
     }
	public static String formUpload(String urlStr, Map<String, String> fileMap) {
		String res = "";
		String BOUNDARY = "---------------------------123821742118716";
		HttpURLConnection conn = null;
		try {
			conn = getURLConnection(urlStr);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			conn.connect();
			conn.setConnectTimeout(10000);
			OutputStream out = conn.getOutputStream();
			if (fileMap != null) {
				Iterator<Map.Entry<String, String>> iter = fileMap.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					// System.out.println(filename);

					String contentType = getContentType(file);
					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"; filename=\"" + filename
							+ "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();

			res = getDataByUrl(conn);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(conn);
		}

		return res;
	}
	
	public static String getDataByUrl(String urlStr,String value){
		HttpURLConnection conn=null;
		String res="";
		try {
			conn=HttpURLUtil.getURLConnection(urlStr);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content_Type","application/x-www-form-urlencoded");
			conn.connect();
			conn.setConnectTimeout(2000);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(value);
			out.flush();
			out.close();
			
			res=getDataByUrl(conn);
			
		} catch (Exception e) {
			return "false";
			//e.printStackTrace();
		}finally{
			close(conn);
		}
		return res;
		
	}

	public static String getDataByUrl(HttpURLConnection conn) {
		BufferedReader reader = null;
		String res = "";
		try {
			StringBuffer strBuf = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return res;
	}

	public static String getDataByUrl(String urlStr) {
		HttpURLConnection conn = getURLConnection(urlStr);
		try {
			conn.setRequestMethod("GET");
			conn.connect();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res = getDataByUrl(conn);
		return res;
	}

	public static JSONObject getJsonObjectByUrl(String urlStr) {
		JSONObject json = null;
		json = JSONObject.fromObject(getDataByUrl(urlStr));
		return json;
	}

	public static JSONArray getJsonArrayByUrl(String urlStr) {
		JSONArray json = null;
		json = JSONArray.fromObject(getDataByUrl(urlStr));
		return json;
	}

}
