package com.barclays.metrics.charts;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PieChartCreator {

	private String chart = "{\"chart\" : {\"type\" : \"pie\",\"margin\" : 40,\"options3d\" : {\"enabled\" : true,\"alpha\" : 45,\"beta\" : 0}},\"title\" : {\"text\" : \"Lines of code\"},\"tooltip\" : {\"pointFormat\" : \"{point.y:,.0f}\"},\"plotOptions\" : {\"pie\" : {\"allowPointSelect\" : true,\"depth\" : 35}},\"series\": [{\"name\": \"Regions\",\"colorByPoint\":true,\"data\": [{\"name\": \"CardNG1\",\"y\": 17089 },{\"name\": \"BAPI\",\"y\": 10603},{\"name\": \"CL\",\"y\": 5223},{\"name\": \"BNP\",\"y\": 10111}]}]}";

	private String vanillaPieChartString = "{\"chart\" : {\"type\" : \"pie\",\"margin\" : 40,\"options3d\" : {\"enabled\" : true,\"alpha\" : 45,\"beta\" : 0}},\"tooltip\" : {\"pointFormat\" : \"{point.y:,.0f}\"},\"plotOptions\" : {\"pie\" : {\"allowPointSelect\" : true,\"depth\" : 35}}}";

	public JSONObject getPieData() throws JSONException {
		List<PieceOfPie> data = new ArrayList<>();
		data.add(new PieceOfPie("CardNG", 17089));
		data.add(new PieceOfPie("BAPI", 10603));
		data.add(new PieceOfPie("CL", 5223));
		data.add(new PieceOfPie("BNP", 10111));
		JSONObject seriesJson = new JSONObject();
		JSONArray dataArray = new JSONArray();
		for (PieceOfPie pieceOfPie : data) {
			JSONObject dataJson = new JSONObject();
			dataJson.put("name", pieceOfPie.getName());
			dataJson.put("y", pieceOfPie.getValue());
			dataArray.put(dataJson);
		}

		seriesJson.put("data", dataArray);
		return seriesJson;
	}

	public String getChart() throws JSONException {
		JSONObject chartJson = new JSONObject(vanillaPieChartString);
		String titleString = "Lines of code";
		String seriesName = "Regions";
		List<PieceOfPie> data = new ArrayList<>();
		data.add(new PieceOfPie("CardNG", 17089));
		data.add(new PieceOfPie("BAPI", 10603));
		data.add(new PieceOfPie("CL", 5223));
		data.add(new PieceOfPie("BNP", 10111));
		chartJson.put("title", getTitle(titleString));
		chartJson.put("series", getSeriesJson(seriesName, data));
		chartJson.put("plotOptions", getPlotOptions());
		return chartJson.toString();
	}

	private Object getPlotOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	private JSONObject getTitle(String titleString) throws JSONException {
		JSONObject titleJson = new JSONObject();
		titleJson.put("text", titleString);
		return titleJson;
	}

	private JSONArray getSeriesJson(String seriesName, List<PieceOfPie> data) throws JSONException {
		JSONArray seriesArray = new JSONArray();
		JSONObject seriesJson = new JSONObject();
		seriesJson.put("text", seriesName);
		seriesJson.put("colorByPoint", true);
		JSONArray dataArray = new JSONArray();
		for (PieceOfPie pieceOfPie : data) {
			JSONObject dataJson = new JSONObject();
			dataJson.put("name", pieceOfPie.getName());
			dataJson.put("y", pieceOfPie.getValue());
			dataArray.put(dataJson);
		}

		seriesJson.put("data", dataArray);
		seriesArray.put(seriesJson);
		return seriesArray;
	}

}
