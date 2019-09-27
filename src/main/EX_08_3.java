package com.practice.elasticsearch.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

public class EX_08_3 {
	public static void main(String[] args) throws IOException {
		// 업데이트 요청 3
		// UPSERT를 활용하는 방법
		// 문서를 검색하여 있으면 업데이트, 없으면 새로 생성
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String _id = "2";
		
		IndexRequest indexReq = new IndexRequest(INDEX_NAME, TYPE_NAME, _id)
				.source(jsonBuilder()
						.startObject()
							.field("movieCd", "20173732")
							.field("movieNm", "살아남은 아이")
							.field("movieNmEn", "Last Child")
							.field("openDt", "")
							.field("typeNm", "장편")
						.endObject());
		
		XContentBuilder upsertBuilder = jsonBuilder();
		
		
		upsertBuilder.startObject();
		upsertBuilder.field("createdAt", new Date());
		upsertBuilder.endObject();
		
		UpdateRequest req = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(upsertBuilder).upsert(indexReq);
		
		try {
			UpdateResponse res = client.update(req, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("업데이트 대상이 존재하지 않습니다.");
			}
		}
		
	}
}
