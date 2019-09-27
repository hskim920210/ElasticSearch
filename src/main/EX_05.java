package com.practice.elasticsearch.rest;


import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EX_05 {
	public static void main(String[] args) throws IOException {
		// 문서 API - 문서 조회
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String _id = "1";
		
		// 요청
		GetRequest req = new GetRequest(INDEX_NAME, TYPE_NAME, _id);
		
		// 응답
		GetResponse res = client.get(req, RequestOptions.DEFAULT);
		
		// 응답의 결과를 Map 형태로 받는다.
		if (res.isExists()) {
			long version = res.getVersion();
			Map<String, Object> sourceAsMap = res.getSourceAsMap();
		} else {
			System.out.println("결과가 존재하지 않음.");
		}
		
	}
}
