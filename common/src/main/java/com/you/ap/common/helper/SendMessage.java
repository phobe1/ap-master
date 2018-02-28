package com.you.ap.common.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMessage {
	/** 用户名常量 */
	public static final String UID = "dugu61888";
	/** 用户密码常量 */
	public static final String PWD = "wp19870618";

	public static final String URL = "http://api.sms.cn/sms/";

	private static final String ZOOMURL="https://api.zoom.us/v1/";

	private static final String API_KEY="W8wgIcP2TxmAicNGTfasrQ";

	private static final String API_Secret="HSeSb59wmM2nfWx76DYSQ7quMyToINAIWTlL";

	public static HttpClient client = new HttpClient();
	

	public static String sentMessage(String phone,String code,String templateId) throws HttpException, IOException {
		PostMethod post = new PostMethod(URL);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("code", code);
		NameValuePair[] data = { new NameValuePair("ac", "send"), new NameValuePair("uid", UID),
				new NameValuePair("pwd", MD5Util.getStringMD5String(PWD + UID)),
				new NameValuePair("mobile", phone),
				new NameValuePair("encode","utf8"),
				new NameValuePair("content", json.toString()), new NameValuePair("template", templateId) };
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
		post.releaseConnection();
		System.out.println(code);
		return result;		
	}
	
	public static String sentMessage(String phone,String code) {
		try{
			return sentMessage(phone,code,"411481");
		}catch (Exception e){
			e.printStackTrace();
			return "服务器异常";
		}
	}

	public static  String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx1cc921938ad2e075"
				+ "&secret=6940113a75b6b15bd596960e0c6a5dd9&code="+code+"&grant_type=authorization_code";
		URI uri = URI.create(url);
		org.apache.http.client.HttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);
				}

				JSONObject object=JSONObject.parseObject(sb.toString().trim());
				return object==null?null:object.getString("openid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String zoomPost(Map<String,String> params,String url) throws IOException {
        if(params==null){
            params=new HashMap<>();

        }
        NameValuePair[] datas=new NameValuePair[params.size()+2];
        params.put("api_key",API_KEY);
        params.put("api_secret",API_Secret);
        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){
            NameValuePair nameValuePair=new NameValuePair(key,params.get(key));
            list.add(nameValuePair);
        }
        list.toArray(datas);
        PostMethod post = new PostMethod(ZOOMURL+url);
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        post.setRequestBody(datas);
        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
        post.releaseConnection();

        return result;

    }


}
