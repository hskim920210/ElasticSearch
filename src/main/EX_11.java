package com.practice.elasticsearch.rest;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class EX_11 {
	public static void main(String[] args) throws IOException {
		// Scroll API
		// 한 번에 많은 양의 검색 결과를 요청할 때 페이지 단위로 나눠 순차적으로 제공.
		// scroll_id를 전달받아 초기에 설정한 사이즈로 데이터를 전송한다.
		// SearchRequest를 이용하여 Query Dsl 클래스를 사용
		// 전체 문서를 검색하여 5건을 출력
		
		RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "캡틴 아메리카";
		
		// 검색 쿼리 설정
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(matchQuery(FIELD_NAME, QUERY_TEXT));
		
		// 스크롤 생성
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		
		// 최초 요청
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		
		// 최초 응답
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while ( searchHits != null && searchHits.length > 0 ) {
			// 다음 요청
			SearchScrollRequest scrollReq = new SearchScrollRequest(scrollId);
			scrollReq.scroll(scroll);
			
			// 다음 응답
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			searchHits = searchResponse.getHits().getHits();
		}
	}
}
