package com.practice.elasticsearch.rest;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class EX_10 {
	public static void main(String[] args) throws IOException {
		// 검색 API
		// SearchRequest를 이용하여 Query Dsl 클래스를 사용
		// 전체 문서를 검색하여 5건을 출력
		
		RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String FIELD_NAME = "movieNm";
		
		// 검색 쿼리 설정
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(5);
		searchSourceBuilder.sort(new FieldSortBuilder(FIELD_NAME).order(SortOrder.DESC));
		
		// 요청
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.types(TYPE_NAME);
		searchRequest.source(searchSourceBuilder);
		
		// 응답
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = searchResponse.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
		}
	}
}
