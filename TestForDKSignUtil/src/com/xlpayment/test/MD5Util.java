package com.xlpayment.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Util {
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes("GBK");
			// ���MD5ժҪ�㷨�MessageDigest ����
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// ʹ��ָ�����ֽڸ���ժ�G
			mdInst.update(btInput);
			// �������
			byte[] md = mdInst.digest();
			// ������ת����ʮ�����Ƶ��ַ�����ʽ
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

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(MD5Util.MD5("800010000050009MD5EBPageResult1.0000000SUCCESS2016101100303053922018031913392167977317621770100220180319134014eGAHNOW2WnAeaY24l16lHWe4BvnxHtiTgeWeJtXkPOlRSzWc10q0oflhMSaX6hVj"));
		MD5Util2 md5Util = new MD5Util2();
		
	}
	
}