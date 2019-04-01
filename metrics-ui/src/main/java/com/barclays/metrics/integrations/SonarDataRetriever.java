package com.barclays.metrics.integrations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class SonarDataRetriever {
	
	private String projectName = "basic-maths";
	
	 public void pullSonarMetrics() throws ClientProtocolException, IOException {
		  HttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet("http://localhost:9000/api/measures/component?metricKeys=ncloc,complexity,violations,coverage&component=" + projectName);
		  HttpResponse response = client.execute(request);
		  BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		  String line;
		  String allData = "";
		  while ((line = rd.readLine()) != null) {
		    allData = allData + line;
		  }
		  System.out.println(allData);
		 }
}
