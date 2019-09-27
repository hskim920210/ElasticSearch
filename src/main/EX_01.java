package com.practice.elasticsearch.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class EX_01 {
	public static void main(String[] args) throws IOException {
		// 인덱스의 생성
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

		String INDEX_NAME = "movie_rest";
		String TYPE_NAME = "_doc";

		// 맵핑 정보
		XContentBuilder indexBuilder = jsonBuilder()
				.startObject()
					.startObject(TYPE_NAME)
						.startObject("properties")
							.startObject("movieCd")
								.field("type", "keyword")
								.field("store", "true")
								.field("index_options", "docs")
							.endObject()
						.startObject("movieNm")
							.field("type", "text")
							.field("store", "true")
							.field("index_options", "docs")
						.endObject()
						.startObject("movieNmEn")
							.field("type", "text")
							.field("store", "true")
							.field("index_options", "docs")
						.endObject()
					.endObject()
				.endObject()
			.endObject();
		
		// 맵핑 설정
		CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
		request.mapping(TYPE_NAME, indexBuilder);
		
		// 별칭 설정
		String ALIAS_NAME = "movie_auto_alias";
		request.alias(new Alias(ALIAS_NAME));
		
		// 인덱스 생성
		// 1. Sync 방식
		CreateIndexResponse createIndexResponse = 
				client.indices().create(request, RequestOptions.DEFAULT);
		boolean acknowledged = createIndexResponse.isAcknowledged();
		client.close();
		// 2. Async 방식
		/*
		ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
			@Override
			public void onFailure(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onResponse(CreateIndexResponse arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		client.indices().createAsync(request, listener);
		client.close();
		*/
		
	}

}
