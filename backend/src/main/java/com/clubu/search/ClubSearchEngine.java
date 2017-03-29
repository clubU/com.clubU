package com.clubu.server.search;

import com.clubu.server.ClubUConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ClubSearchEngine {

	private static ClubSearchEngine instance = null;
	public static ClubSearchEngine getInstance() {
		if (instance == null) {
			instance = new ClubSearchEngine();
		}
		return instance;
	}

	private SolrClient client = null;
	private ClubSearchEngine() {
		String solrUrl = ClubUConfiguration.getInstance().getSolrUrl();
		client = new HttpSolrClient(solrUrl);
	}

	public List<Long> executeQuery(String keyword) {
		SolrQuery query = new SolrQuery();
		QueryResponse response = null;
		SolrDocument document = null;
		SolrDocumentList documents = null;
		Iterator<SolrDocument> iter = null;
		List<Long> ret = null;
		String value = null;

		query.set("q", "name:\"" + keyword + "\"");
		try {
			response = client.query(query);
		} catch (Exception e) {
			return null;
		}


		if (response != null) {
			documents = response.getResults();
			iter = documents.iterator();
			ret = new ArrayList<Long>();
			while (iter.hasNext()) {
				document = iter.next();
				ret.add((Long)(document.getFieldValue("id")));
			}
			return ret;
		} else {
			return null;
		}
	}
}
