package com.yikangyiliao.fileManage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.yikangyiliao.base.encryption.AES;

/**
 * @author liushuaic
 * @date 2015/07/27 18:22
 * @发送请求工具类
 **/

public class SendRequest {

	private static String REQUEST_URL = "http://192.168.1.7:8081/yikangservice/service/";

	private static ObjectMapper objectMapper = new ObjectMapper();
	@SuppressWarnings("unchecked")
	public static void sendGet(String serviceCode, Map<String, Object> paramData) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {

			String paramDataJsonString = objectMapper.writeValueAsString(paramData);

			try {
				paramDataJsonString = AES.Encrypt(paramDataJsonString, "1234567890abcDEF");
			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpGet httpget = new HttpGet(REQUEST_URL + serviceCode + "&paramData=" + paramDataJsonString);

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						System.out.println(entity.toString());
						return entity != null ? EntityUtils.toString(entity) : null;

					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
			Map<String, Object> data = new HashMap<String, Object>();
			System.out.println("接收到请求时间" + sdf.format(new Date()));
			data = objectMapper.readValue(responseBody, Map.class);
			if (data.containsKey("data")) {
				String dataStr = data.get("data").toString();
				System.out.println("解析出请求时间" + sdf.format(new Date()));
				System.out.println(AES.Decrypt(dataStr, "1234567890abcDEF"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
	}

	public static void sendPost(String serviceCode, Map<String, Object> paramData) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost("http://192.168.1.7:8081/yikangFileManage/fileUpload/imageFileUpload");

		File file = new File("E:/123.jpg");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("files", file, ContentType.DEFAULT_BINARY, file.getName());
		builder.addTextBody("fileGroup", "headImage");
		Charset charset=Charset.forName("utf-8");
		builder.setCharset(charset);
		
		HttpEntity entity = builder.build();
		httpPost.setEntity(entity);
		System.out.println("Executing request " + httpPost.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					System.out.println(entity.toString());
					return entity != null ? EntityUtils.toString(entity) : null;

				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		String responseBody = httpclient.execute(httpPost, responseHandler);
		System.out.println("----------------------------------------");
		Map<String, Object> data = new HashMap<String, Object>();
		System.out.println("接收到请求时间" + sdf.format(new Date()));

	}

	public static void main(String[] args) {
		try {
			Map<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("name", "hello");

			SendRequest.sendPost("00-01-01", paramData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestRequest() {
		try {
			Map<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("name", "hello");

			SendRequest.sendPost("00-01-01", paramData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestAesEncipty() throws Exception {
		String str = AES.Encrypt("{name:hello}", "1234567890abcDEF");
		System.out.println(str);
	}

}
