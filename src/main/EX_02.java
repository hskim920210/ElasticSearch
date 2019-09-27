package com.practice.elasticsearch.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EX_02 {
	public static void main(String[] args) throws IOException {
		// 인덱스의 삭제
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_rest";
		
		// 인덱스 삭제
		DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
		
		DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		
		client.close();
	}
}
