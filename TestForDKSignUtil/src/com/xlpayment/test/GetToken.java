package com.sdgs.main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;




public class GetToken {
	public static void main(String[] args) throws ClientProtocolException, IOException{
		String phoneNum = "18766111862";
		String IP="";
		 InetAddress inetAddress = InetAddress.getLocalHost();  
	     System.out.println("����IP:"  + inetAddress.getHostAddress().toString());  
	    IP=inetAddress.getHostAddress().toString();
		String[] verifyString = new String[] { "0", "1", "2", "3", "4", "5",  
                "6", "7", "8", "9" };  
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyBuilder = new StringBuilder();  
        for (int i = 0; i < 6; i++) {  
            int rd = random.nextInt(10);  //������� һ��0��10֮�������
            verifyBuilder.append(verifyString[rd]);  //ƴ��
        }  
        String verifyCode = verifyBuilder.toString(); 
        System.out.println("��λ�����֤��------------>"+verifyCode);
		String signFieldValue = "juhepay|7651d1810e2e77360e2a0744579d4186|"+phoneNum+"|����,���˴ε���֤���ǣ�"+verifyCode+",��Чʱ��60�롣|refundsubmit|"+IP;
		String[] signFieldKey = {"sysName", "sysPass","telNum","message","busiType","customerIp"};
		
		String notifyUrl = "http://10.180.228.160:8080/muip/SendMsgServlet?";
		String[] signFieldArray = signFieldValue.split("\\|");
		for (int index = 0; index < signFieldArray.length; index++) {
			System.out.println(signFieldKey[index] + "="
					+ signFieldArray[index]);
			if (index < signFieldArray.length - 1) {
				notifyUrl += signFieldKey[index] + "=" + signFieldArray[index] + "&";
			} else {
				notifyUrl += signFieldKey[index] + "=" + signFieldArray[index];
			}
		}
		System.out.println("��ַ------------------> " + notifyUrl);
		org.apache.http.client.methods.HttpGet get = new HttpGet(notifyUrl);
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		org.apache.http.HttpResponse response = client.execute(get);
		
		java.io.InputStream is = response.getEntity().getContent();

		java.io.BufferedReader rd = new java.io.BufferedReader(
				new java.io.InputStreamReader(is, "UTF-8"));
		String line;
		try{
			while ((line = rd.readLine()) != null) {
				System.out.println("���============��" + line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
