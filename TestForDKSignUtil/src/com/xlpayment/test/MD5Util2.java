package com.xlpayment.test;

import java.security.MessageDigest;

public class MD5Util2 {
	    public final  String MD5(String s,String charset) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
	        try {
	            byte[] btInput = s.getBytes(charset);
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            mdInst.update(btInput);
	            byte[] md = mdInst.digest();
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	
	public static void main(String[] args) {
		MD5Util2 md5Util = new MD5Util2();
		String signdata = "00800010000050009ZXL601021803130812221MD5CheckOrder1.0000000SUCCESShttp://10.180.29.60:8680/hipos/OMCGPUB5/4420781.dow?MERC=800010000050009800010000050009_20161011_01.txt6459af1c0e1ae97feac384b5ec44ebca6459af1c0e1ae97feac384b5ec44ebcaeGAHNOW2WnAeaY24l16lHWe4BvnxHtiTgeWeJtXkPOlRSzWc10q0oflhMSaX6hVj";
		String mac = md5Util.MD5(signdata, "gbk");
		System.out.println(mac);

	}

}
