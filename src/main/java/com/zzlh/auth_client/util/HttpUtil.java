package com.zzlh.auth_client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzlh.auth_client.domain.ResponseEntity;

/**
 * @Description http工具类
 * @author liulei
 * @date 2018年10月22日 下午5:38:53
 */
public class HttpUtil {

	/**
	 * @Description 发送http get请求
	 * @param path 请求路径
	 * @return
	 */
	public static ResponseEntity<Object> doGet(String path) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setConnectionRequestTimeout(60000)
				.setSocketTimeout(60000).build();

		HttpGet httpGet = new HttpGet(path);
		httpGet.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			// 获取响应实体
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.readValue(EntityUtils.toString(httpEntity, "UTF-8"), ResponseEntity.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void httpPost(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/Servlet/do_login.do");
        //设置post请求传递参数
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username","test") );
        list.add(new BasicNameValuePair("password","admin"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            post.setEntity(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        //执行请求并处理响应
        try{
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity!=null){
                System.out.println(EntityUtils.toString(httpEntity,"UTF-8"));
            }
            response.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}