package com.barclays.metrics.charts;

import org.json.JSONException;
import org.junit.Test;

public class PieChartCreatorTest {

	@Test
	public void test_getChart() throws JSONException{
		PieChartCreator pieChartCreator = new PieChartCreator();
		pieChartCreator.getChart();
	}
}
