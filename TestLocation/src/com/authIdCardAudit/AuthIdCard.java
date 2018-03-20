
package com.authIdCardAudit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;



public class AuthIdCard {
	
	//private String url ="http://open.tf56.com/openGateway/oauthentry/certificateAudit/1/authVehicleCardAudit";

	private static Logger logger = HiLog.getLogger("AuthIdCardAudit.trc");
	public int execute(HiATLParam args, final HiMessageContext ctx)
			throws HiException {
		logger.info("--------------------------身份证查询--------------------------");     
		
		HiMessage msg = ctx.getCurrentMsg();
		HiETF etf = msg.getETFBody();

		String realname = HiArgUtils.getStringNotNull(args, "realname");// 姓名
		logger.info("realname", realname);
	
		String certificatenumber = HiArgUtils.getStringNotNull(args, "certificatenumber");//身份证号
		logger.info("certificatenumber", certificatenumber);
		
		String ordernumber = HiArgUtils.getStringNotNull(args, "ordernumber");//	订单号
		logger.info("ordernumber", ordernumber);
		
		String sourcecode = HiArgUtils.getStringNotNull(args, "sourcecode");//调用方订单号
		logger.info("sourcecode", sourcecode);
		
		
		//Token token_id = new Token();
		String tokenURL="https://open.tf56.com/openGateway/oauth/token?grant_type=client_credentials&client_id=7&client_secret=7fad9d6b20e849219d6888b25e3f40db";
		String apiURL = "https://open.tf56.com/openGateway/oauthentry/certificateAudit/1/authIdCardAudit";
		
	    PostMethod post = new PostMethod(apiURL);
	    
 org.apache.http.client.methods.HttpPost post2 = new HttpPost(tokenURL);
		
		org.apache.http.client.HttpClient client2 = new DefaultHttpClient();
        
        HttpResponse response2=null;
		InputStream is=null;
		String access_token=null;
		try {
			response2 = client2.execute(post2);
			/*String response22 = HiArgUtils.getStringNotNull(args, "response22");*/
			logger.info("response2",response2);
			is = response2.getEntity().getContent();
			BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(is));
			String line;
			
			while ((line = rd.readLine()) != null) {
				
				JsonObject jsonObj = new JsonParser().parse(line).getAsJsonObject();
				
				access_token=jsonObj.get("access_token").toString().replace("\"","");
				logger.info("line",line);
        		
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); //;charset=GBK
        NameValuePair[] param = { 
        		
                new NameValuePair("realname", realname),
                new NameValuePair("certificatenumber", certificatenumber),
                new NameValuePair("ordernumber", ordernumber),
                new NameValuePair("sourcecode", sourcecode),
                new NameValuePair("access_token", access_token)} ;
        
        post.setRequestBody(param);
        
        HttpClient client = new HttpClient();//创建httpClient对象 
        String response =null;
        int Retcod = 0;
        try {  
            int code=client.executeMethod(post);
            
            if (code==200) {  
		         //System.out.println("请求成功");  
            	logger.info("code==>",code);
		         InputStream inputStream = post.getResponseBodyAsStream();
		         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		         StringBuffer stringBuffer = new StringBuffer();
		         String str = "";
		         while ((str = br.readLine()) != null) {
		             stringBuffer.append(str);
		         }
		         response = stringBuffer.toString();
		         logger.info("response--->",response);
		          JSONObject obj = JSONObject.fromObject(response);
		         if(obj.get("result").toString().equals("success")){
		        	 Retcod = 0;
		        	 etf.setChildValue("RESULT", obj.get("result").toString());
			         logger.info("RESULT",etf.getChildValue("RESULT"));
			         etf.setChildValue("CODE", obj.get("code").toString());
			         logger.info("CODE",etf.getChildValue("CODE"));
			         etf.setChildValue("REAL_NM", JSONObject.fromObject(obj.get("data")).get("realname").toString());
			         logger.info("REAL_NM",etf.getChildValue("REAL_NM"));
			         etf.setChildValue("CER_NUM", JSONObject.fromObject(obj.get("data")).get("certificatenumber").toString());
			         logger.info("CER_NUM",etf.getChildValue("CER_NUM"));
			         etf.setChildValue("MSG", obj.get("msg").toString());
			         logger.info("msg",etf.getChildValue("MSG"));
			         
		         }
		         else{
		        	 Retcod = 1;
		        	 etf.setChildValue("RESULT", obj.get("result").toString());
			         logger.info("RESULT",etf.getChildValue("RESULT"));
			         etf.setChildValue("CODE", obj.get("code").toString());
			         logger.info("CODE",etf.getChildValue("CODE"));
			         etf.setChildValue("MSG", obj.get("msg").toString());
			         logger.info("msg",etf.getChildValue("MSG"));
		         }
		         
		         /*JSONArray obj = JSONArray.fromObject(response);
		         etf.setChildValue("REAL_NM", JSONArray.fromObject(JSONArray.fromObject(obj.get(4)).get(2)).toString());
		         logger.info("REAL_NM",etf.getChildValue("REAL_NM"));
		         etf.setChildValue("CER_NUM", JSONArray.fromObject(JSONArray.fromObject(obj.get(4)).get(1)).toString());
		         logger.info("CER_NUM",etf.getChildValue("CER_NUM"));
		         etf.setChildValue("RESULT", obj.get(0).toString());
		         logger.info("RESULT",etf.getChildValue("RESULT"));
		         
		         logger.info("msg",JSONArray.fromObject(obj.get(2)).toString());*/
		         
		         
		     }else {  
		         System.out.println("请求失败");
		         Retcod = -1;
		         
		     }
		  } catch (HttpException e) {  
		         e.printStackTrace();  
		     } catch (IOException e) {  
		         e.printStackTrace();  
		     }finally {  
		         post.releaseConnection();//关闭连接    
		  }
        return Retcod;
	   /*--------------------------------------------------------------- */
	}
		 
	
		
	}
	
	

