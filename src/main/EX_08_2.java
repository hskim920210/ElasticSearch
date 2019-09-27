package com.practice.elasticsearch.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

public class EX_08_2 {
	public static void main(String[] args) throws IOException {
		// 업데이트 요청 2
		// 문서의 일부분을 업데이트
		// 기존 문서를 검색해 새로 생성한 문서로 덮어쓴다.
		// 업데이트 대상이 되는 칼럼의 값은 변경되고 나머지 칼럼은 그대로 유지
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String _id = "1";
		
		XContentBuilder builder = jsonBuilder();
		
		builder.startObject();
		builder.field("createdAt", new Date());
		builder.field("prdtYear", "2019");
		builder.field("typeNm", "장편");
		builder.endObject();
		
		UpdateRequest req = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(builder);
		
		try {
			UpdateResponse res = client.update(req, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("업데이트 대상이 존재하지 않습니다.");
			}
		}
		
	}
}
