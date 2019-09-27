package com.practice.elasticsearch.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class EX_09 {
	public static void main(String[] args) throws IOException {
		// BULK API - Create, Update, Delete 동시처리 가능
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String FIELD_NAME = "movieNm";
		
		// 데이터 생성
		BulkRequest req = new BulkRequest();
		req.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "4")
				.source(XContentType.JSON, FIELD_NAME, "살아남은 아이"));
		req.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "5")
				.source(XContentType.JSON, FIELD_NAME, "프렌즈: 몬스터섬의 비밀"));
		req.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "6")
				.source(XContentType.JSON, FIELD_NAME, "캡틴 아메리카 시빌 워"));
		
		// 결과 조회
		BulkResponse res = client.bulk(req, RequestOptions.DEFAULT);
		for (BulkItemResponse bulkItemRes : res) {
			DocWriteResponse itemRes = bulkItemRes.getResponse();
			
			if (bulkItemRes.getOpType() == DocWriteRequest.OpType.INDEX
					|| bulkItemRes.getOpType() == DocWriteRequest.OpType.CREATE) {
				IndexResponse iRes = (IndexResponse) itemRes;
			} else if (bulkItemRes.getOpType() == DocWriteRequest.OpType.UPDATE) {
				UpdateResponse uRes = (UpdateResponse) itemRes;
			} else if (bulkItemRes.getOpType() == DocWriteRequest.OpType.DELETE) {
				DeleteResponse dRes = (DeleteResponse) itemRes;
			}
		}
	}
}
