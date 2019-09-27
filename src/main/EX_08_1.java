package com.practice.elasticsearch.rest;

import static java.util.Collections.singletonMap;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

public class EX_08_1 {
	public static void main(String[] args) throws IOException {
		// 업데이트 요청 1
		// 스크립트를 이용한 업데이트
		// 특정 값에 추가적으로 값을 더하거나 빼는 스트립트를 작성
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		
		String INDEX_NAME = "movie_auto_java";
		String TYPE_NAME = "_doc";
		String _id = "1";
		
		UpdateRequest req = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id);
		
		Object objCount = 10;
		Map<String, Object> params = singletonMap("count", objCount);
		
		Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.prdtYear += params.count", params);
		
		req.script(inline);
		
		try {
			UpdateResponse res = client.update(req, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("업데이트 대상이 존재하지 않습니다.");
			}
		}
		
	}
}
