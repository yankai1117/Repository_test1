package com.test1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test2 {
	public static void main(String[] args){
		String testString = "中文";
	    try
	    {
	        String encoderString = URLEncoder.encode(testString, "utf-8");
	        System.out.println(encoderString);
	        String decodedString = URLDecoder.decode(encoderString, "utf-8");
	        System.out.println(decodedString);
	    } catch (UnsupportedEncodingException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	
}
