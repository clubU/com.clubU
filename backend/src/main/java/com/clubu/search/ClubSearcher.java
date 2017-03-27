package com.clubu.server.search;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocumentList;

public class ClubSearcher {

	private static final String SOLR_URL = "http://localhost:8983/solr";
	private static SolrClient client = null;
	static {
		client = new HttpSolrClient(SOLR_URL);
	}

	private static SolrDocumentList executeQuery(String keyword) {
		return null;
	}

	private static List<Long> extractId(SolrDocumentList documentList) {
		return null;
	}
}
