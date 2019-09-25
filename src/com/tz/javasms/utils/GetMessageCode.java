package com.tz.javasms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;






/**
 *@Classname GetMessageCode.java
 *@Description ������֤����
 *@Author ���־���
 *@Date 2017��12��9��
 */
public class GetMessageCode {
	private static final String QUERY_PATH = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	private static final String ACCOUNT_SID = "c4d1fc3fb61c444880383858b6c719ae";
	private static final String AUTH_TOKEN = "8dd7c07eaa154e498fc842d0dec4a507";
	
	
	/**
	 * TODO(�����ֻ���)
	 * @throws IOException 
	 */
	public static String getCode(String phone) {
		String  random = smsCode();
		String timestamp = getTimestamp();
		String sig = getMD5(ACCOUNT_SID, AUTH_TOKEN, timestamp);
		String tamp = "�����־��ܡ�"+"��֤��Ϊ��{"+random+"}������2��������ȷ���룬��Ǳ��˲���������Դ˶��š�";
		StringBuilder result = new StringBuilder();
		OutputStreamWriter out = null;
		BufferedReader  br = null;
		try {
			URL url = new URL(QUERY_PATH);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);//���ò���
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");//:
			out = new OutputStreamWriter(connection.getOutputStream(), "utf-8") ;
			String args = getQueryArgs(ACCOUNT_SID, tamp, phone, timestamp, sig, "JSON");
			out.write(args);
			out.flush();
			
			//��ȡ���ؽ�� 
			
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String temp = "";
			while((temp = br.readLine())!=null){
				result.append(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		org.json.JSONObject json = new org.json.JSONObject(result.toString());
		  String respCode = json.getString("respCode");
		  
		  String defaultRespCode = "00000";
		  if(defaultRespCode.equals(respCode)){
			  return random;
		  }else{
			  return defaultRespCode;
		  }
		
	}
	/**
	 * ����ƴ��
	 * @param accountSid
	 * @param smsContent
	 * @param to
	 * @param timestamp
	 * @param sig
	 * @param respDataType
	 * @return
	 */
	public static String getQueryArgs(String accountSid, String smsContent, String to, String timestamp, String  sig, String respDataType){
		return  "accountSid="+accountSid+"&smsContent="+smsContent
				+"&to="+to+"&timestamp="+timestamp+"&sig="+sig
				+"&respDataType="+respDataType;
		
	}
	
	
	public static String getTimestamp(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	/**
	 * sigǩ��
	 * @param sid
	 * @param token
	 * @param timestamp
	 * @return
	 * @throws 
	 */
	public static String getMD5(String sid, String token, String timestamp) {
		StringBuilder result = new StringBuilder();
		String source = sid+token+timestamp;
		try{
			
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bytes = digest.digest(source.getBytes());
		for(byte b : bytes){
			String hex = Integer.toHexString(b&0xff);
			if(hex.length()==1){
				result.append("0"+hex);
				
			}else{
				result.append(hex);
			}
		}
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
			
		}
		  
		  
				return result.toString();
	}
	/**
	 * (������֤��)
	 * @return
	 */
	public static String smsCode(){
		String ran = new Random().nextInt(1000000)+"";
		if(ran.length()!=6){
			return smsCode();
		}else{
			
			return ran;
			
		}
		
	}
	public static void main(String[] args){
		String code = getCode("15228318140");
		System.out.println(code);
		
	}

}
