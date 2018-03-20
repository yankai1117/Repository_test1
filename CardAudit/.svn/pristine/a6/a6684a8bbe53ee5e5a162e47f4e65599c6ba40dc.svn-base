package sdgs.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
  
public class Test {  
  
    public static void main(String[] args){
String apiURL = "https://open.tf56.com/openGateway/oauthentry/certificateAudit/1/authVehicleCardAudit";
//?access_token=8c6b90b8103c3455a588970c186654fd
String tokenURL="https://open.tf56.com/openGateway/oauth/token?grant_type=client_credentials&client_id=7&client_secret=7fad9d6b20e849219d6888b25e3f40db";
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

PostMethod post = new PostMethod(apiURL);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
        NameValuePair[] param = { 
        		new NameValuePair("carplatenumber", "鲁Q7376A"),//鲁A38J29
                new NameValuePair("caridentcode", "D09118"),//LGBG12E246Y027559
                new NameValuePair("carenginenumber", "381307"),
                new NameValuePair("ordernumber", "i5364"),
                new NameValuePair("sourcecode", "027559"),
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
        System.out.println(response);
    }
}  