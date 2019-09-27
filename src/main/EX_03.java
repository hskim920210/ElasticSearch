package com.practice.elasticsearch.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EX_03 {
	public static void main(String[] args) throws IOException {
		// 인덱스의 OPEN/CLOSE
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto";
		
		// 인덱스 오픈
		OpenIndexRequest reqOpen = new OpenIndexRequest(INDEX_NAME);
		OpenIndexResponse oir = client.indices().open(reqOpen, RequestOptions.DEFAULT);
		boolean ackOpen = oir.isAcknowledged();
		
		// 인덱스 클로즈
		CloseIndexRequest reqClose = new CloseIndexRequest(INDEX_NAME);
		CloseIndexResponse cir = client.indices().close(reqClose, RequestOptions.DEFAULT);
		boolean ackClose = cir.isAcknowledged();
		
		client.close();
	}

}
