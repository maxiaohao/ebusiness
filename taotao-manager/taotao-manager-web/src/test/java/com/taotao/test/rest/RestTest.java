package com.taotao.test.rest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestTest {
	static Logger log = LoggerFactory.getLogger(RestTest.class.getName());
	
	
	@Test
	public void testSolrJ() throws Exception {
/*		  HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
		CloseableHttpClient httpclient =httpClientBuilder.build();  		
		HttpGet httpGet = new HttpGet("http://localhost:8081/rest/item/updatesolr/101434521126763");
		CloseableHttpResponse response  = httpclient.execute(httpGet);
		System.out.println("测试结果utf8:" + EntityUtils.toString(response.getEntity()));
		log.debug("测试结果utf8:" + EntityUtils.toString(response.getEntity()));*/
	}
	
	
}

