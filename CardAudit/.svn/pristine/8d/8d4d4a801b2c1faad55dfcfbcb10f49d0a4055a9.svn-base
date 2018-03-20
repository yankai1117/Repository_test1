package sdgs.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



public class GetToken {
	public static void main(String[] args) throws ClientProtocolException, IOException{
		String signFieldValue = "client_credentials|7|7fad9d6b20e849219d6888b25e3f40db";
		String[] signFieldKey = {"grant_type", "client_id","client_secret"};
		
		String notifyUrl = "https://open.tf56.com/openGateway/oauth/token?";
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
		
		System.out.println("地址------------------> " + notifyUrl);
		// notifyUrl = "http://www.xlpayment.com/bkg/OBKGPUB1/4420025.dow?ACCESSTYPE=1&ACQINSCODE=48914500&BIZTYPE=000201&CERTID=69597475696&CURRENCYCODE=156&ENCODING=UTF-8&MERID=891451047840008&ORDERID=20160808400080170262&QUERYID=201608081445281103878&RESPCODE=00&RESPMSG=Success!&SETTLEAMT=10000&SETTLECURRENCYCODE=156&SETTLEDATE=0808&SIGNMETHOD=01&TRACENO=110387&TRACETIME=0808144528&TXNAMT=10000&TXNSUBTYPE=01&TXNTIME=20160808144528&TXNTYPE=01&VERSION=5.0.0&SIGNATURE=nh4V6k6napIPcz/yJi7LFxcCXlnpC6xciQP2Mwf9IuuTG2XUK2H9oWW/R7RTs9Srw/BUeIsICISp71KN8WH3BD+PD69HYtnJG8j5X44fS8hluDR0EkVj87+tpKnYWjMX5lr1CXHolWaqViJZFia0XcTjzmzGE61PIBFXsoAxUt/oECxs7CekdIi+RseEi55DRLePgFFAaK82IKc4tlVUhXWGNDdW9cKLCkAOeKlysK+qljO41Of8SeNn1BroSDsaqTvfmlYl+XurSYRWpU9CNG2UUCllSCZn2O0mZxe+owBQWgDNa2U/PwJ4Ji5DrBfvAmAGbyTkSiLg1Q+JF09FHw==";
		
		org.apache.http.client.methods.HttpPost post = new HttpPost(notifyUrl);
		//org.apache.http.client.methods.HttpGet get = new HttpGet(notifyUrl);
		
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		
		org.apache.http.HttpResponse response = client.execute(post);
		//org.apache.http.HttpResponse response = client.execute(get);

		java.io.InputStream is = response.getEntity().getContent();

		java.io.BufferedReader rd = new java.io.BufferedReader(
				new java.io.InputStreamReader(is));
		String line;
		String token_id;
		try{
			while ((line = rd.readLine()) != null) {
				System.out.println("输出============》" + line);
				System.out.println(line.substring(line.lastIndexOf(":")+2,line.lastIndexOf("}")-1));
				token_id=line.substring(line.lastIndexOf(":")+2,line.lastIndexOf("}")-1);
				System.out.println("token_id--------------->"+token_id);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
