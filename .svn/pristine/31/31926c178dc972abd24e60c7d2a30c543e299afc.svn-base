package com.icbc.rel.hefei.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能描述：http请求发送类
 * 
 * @author
 * @date 2017-06-15
 * 
 */
public class HttpUtil {

	private static int CONNECTION_TIMEOUT = 6000;

	/**
	 * 发送get请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doHttpGet(String url) throws ClientProtocolException, IOException {
		JSONObject returnJSON = new JSONObject();
		StringBuilder result = new StringBuilder("");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		HttpGet httpGet = new HttpGet(url.trim());
		HttpResponse response = httpClient.execute(httpGet);
		try {
			returnJSON.put("statusCode", response.getStatusLine().getStatusCode());
			returnJSON.put("response", "");
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), HTTP.UTF_8));
				String line = null;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				try {
					try {
						JSONObject tmp = new JSONObject(result.toString());
						returnJSON.put("response", tmp);
					} catch (JSONException e) {
						JSONArray tmp = new JSONArray(result.toString());
						returnJSON.put("response", tmp);
					}
				} catch (Exception e) {
					returnJSON.put("response", result.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnJSON;
	}
	
	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static JSONObject doHttpPost(String url, Map<String, String> param) throws Exception {
		JSONObject returnJSON = new JSONObject();
		StringBuilder result = new StringBuilder("");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		HttpPost httpPost = new HttpPost(url.trim());
		List<BasicNameValuePair> nvpList = new ArrayList<BasicNameValuePair>();
		if (param != null && !param.isEmpty()) {
			Iterator<String> keyIterator = param.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				nvpList.add(new BasicNameValuePair(key, param.get(key)));
			}
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nvpList, HTTP.UTF_8));
		HttpResponse response = httpClient.execute(httpPost);
		returnJSON.put("statusCode", response.getStatusLine().getStatusCode());
		returnJSON.put("response", "");
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), HTTP.UTF_8));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			try {
				try {
					JSONObject tmp = new JSONObject(result.toString());
					returnJSON.put("response", tmp);
				} catch (JSONException e) {
					JSONArray tmp = new JSONArray(result.toString());
					returnJSON.put("response", tmp);
				}
			} catch (JSONException e) {
				returnJSON.put("response", result.toString());
			}
		}
		return returnJSON;
	}

	/**
	 * 发送post请求
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String doHttpPost(String url, String data) throws IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		HttpPost httpPost = new HttpPost(url.trim());
		httpPost.setEntity(new StringEntity(data, "utf-8"));
		HttpResponse response = httpClient.execute(httpPost);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			StringBuilder result = new StringBuilder("");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), HTTP.UTF_8));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}
		return null;
	}
}
