package com.practice.elasticsearch.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EX_04 {
	public static void main(String[] args) throws IOException {
		// 문서 API 사용하기 - 문서추가
		
		RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		// 인덱스명
		String INDEX_NAME = "movie_auto_java";
		// 타입명
		String TYPE_NAME = "_doc";
		// 문서 키 값
		String _id = "1";
		
		// 데이터 추가
		IndexRequest req = new IndexRequest(INDEX_NAME, TYPE_NAME, _id);
		req.source(jsonBuilder()
				.startObject()
					.field("movieCd", "20173732")
					.field("movieNm", "살아남은 아이")
					.field("movieNmEn", "Last Child")
				.endObject());
		
		// 결과 조회
		try {
			IndexResponse res = client.index(req, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			System.out.println("문서 생성 실패");
		}
	}
}
