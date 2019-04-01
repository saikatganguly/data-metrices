package com.barclays.metrics.web;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.barclays.metrics.charts.PieChartCreator;

@Controller
public class ChartsController {

	private String chart = "{\"chart\" : {\"type\" : \"pie\",\"margin\" : 40,\"options3d\" : {\"enabled\" : true,\"alpha\" : 45,\"beta\" : 0}},\"title\" : {\"text\" : \"Lines of code\"},\"tooltip\" : {\"pointFormat\" : \"{point.y:,.0f}\"},\"plotOptions\" : {\"pie\" : {\"allowPointSelect\" : true,\"depth\" : 35}},\"series\": [{\"name\": \"Regions\",\"colorByPoint\":true,\"data\": [{\"name\": \"CardNG1\",\"y\": 17089 },{\"name\": \"BAPI\",\"y\": 10603},{\"name\": \"CL\",\"y\": 5223},{\"name\": \"BNP\",\"y\": 10111}]}]}";

	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String chart(Model model) throws JSONException {

		// first, add the regional sales
		/*Integer northeastSales = 17089;
		Integer westSales = 10603;
		Integer midwestSales = 5223;
		Integer southSales = 10111;*/

		/*model.addAttribute("cardNG", northeastSales);
		model.addAttribute("bapi", southSales);
		model.addAttribute("cl", midwestSales);*/
		model.addAttribute("pieData", new PieChartCreator().getPieData().toString());

		// now add sales by lure type
		List<Integer> clCodecoverage = Arrays.asList(4074, 3455, 4112);
		List<Integer> bapiCodecoverage = Arrays.asList(3222, 3011, 3788);
		List<Integer> cardNGCodecoverage = Arrays.asList(7811, 7098, 6455);

		model.addAttribute("CardNG", cardNGCodecoverage);
		model.addAttribute("BAPI", bapiCodecoverage);
		model.addAttribute("CL", clCodecoverage);

		return "chart";
	}

	// redirect to demo if user hits the root
	@RequestMapping("/")
	public String home(Model model) {
		return "redirect:chart";
	}
}
