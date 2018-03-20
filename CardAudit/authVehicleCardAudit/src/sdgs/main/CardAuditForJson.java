package sdgs.main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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



public class CardAuditForJson {
	
	//private String url ="http://open.tf56.com/openGateway/oauthentry/certificateAudit/1/authVehicleCardAudit";

	private static Logger logger = HiLog.getLogger("authVehicleCardAudit.trc");
	public String execute(HiATLParam args, final HiMessageContext ctx)
			throws HiException {
		logger.info("--------------------------行驶证查询--------------------------");     
		
		HiMessage msg = ctx.getCurrentMsg();
		HiETF etf = msg.getETFBody();

		String carplatenumber = HiArgUtils.getStringNotNull(args, "carplatenumber");// 车牌号
		logger.info("carplatenumber", carplatenumber);
	
		String caridentcode = HiArgUtils.getStringNotNull(args, "caridentcode");//车辆识别号
		logger.info("caridentcode", caridentcode);
		
		String carenginenumber = HiArgUtils.getStringNotNull(args, "carenginenumber");//	发动机号
		logger.info("carenginenumber", carenginenumber);
		
		String ordernumber = HiArgUtils.getStringNotNull(args, "ordernumber");//调用方订单号
		logger.info("ordernumber", ordernumber);
		
		String sourcecode = HiArgUtils.getStringNotNull(args, "sourcecode");//数据来源
		logger.info("sourcecode", sourcecode);
		//Token token_id = new Token();
		String tokenURL="https://open.tf56.com/openGateway/oauth/token?grant_type=client_credentials&client_id=7&client_secret=7fad9d6b20e849219d6888b25e3f40db";
		String apiURL = "https://open.tf56.com/openGateway/oauthentry/certificateAudit/1/authVehicleCardAudit";
		
	    PostMethod post = new PostMethod(apiURL);
	    
 org.apache.http.client.methods.HttpPost post2 = new HttpPost(tokenURL);
		
		org.apache.http.client.HttpClient client2 = new DefaultHttpClient();
        
        HttpResponse response2=null;
		InputStream is=null;
		String access_token=null;
		try {
			response2 = client2.execute(post2);
			is = response2.getEntity().getContent();
			BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(is));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println("输出==========》" + line);
				JsonObject jsonObj = new JsonParser().parse(line).getAsJsonObject();
				
				access_token=jsonObj.get("access_token").toString().replace("\"","");
        		System.out.println("access_token==》" + access_token);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
        NameValuePair[] param = { new NameValuePair("carplatenumber", carplatenumber),
                new NameValuePair("caridentcode", caridentcode),
                new NameValuePair("carenginenumber", carenginenumber),
                new NameValuePair("ordernumber", ordernumber),
                new NameValuePair("sourcecode", sourcecode),
                new NameValuePair("access_token", access_token)} ;
        
        post.setRequestBody(param);
        
        HttpClient client = new HttpClient();//创建httpClient对象 
        String response =null;
        try {  
            int code=client.executeMethod(post);//发送数据 
            
            if (code==200) {  
		         System.out.println("请求成功！");  
		         InputStream inputStream = post.getResponseBodyAsStream();
		         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		         StringBuffer stringBuffer = new StringBuffer();
		         String str = "";
		         while ((str = br.readLine()) != null) {
		             stringBuffer.append(str);
		         }
		         response = stringBuffer.toString();
		         System.out.println(response); 
		         return response;
		     }else {  
		         System.out.println("请求失败！");  
		     }
		  } catch (HttpException e) {  
		         e.printStackTrace();  
		     } catch (IOException e) {  
		             e.printStackTrace();  
		     }finally {  
		    	 
		         post.releaseConnection();//关闭连接    
		  }
	   /*--------------------------------------------------------------- */
        return response;
	}
		 
	
		
	}
	
	

