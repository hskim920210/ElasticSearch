package com.practice.elasticsearch.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EX_07 {
	public static void main(String[] args) throws IOException {
		// 문서 삭제
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String _id = "1";
		
		DeleteRequest req = new DeleteRequest(INDEX_NAME, TYPE_NAME, _id);
		DeleteResponse res = client.delete(req, RequestOptions.DEFAULT);
	}
}
