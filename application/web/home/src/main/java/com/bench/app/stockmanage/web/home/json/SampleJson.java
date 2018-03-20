/**
 * Bench.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.web.home.json;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bench.app.stockmanage.base.web.JsonOutputResult;

/**
 * A sample controller.
 */
@Controller
public class SampleJson {
	
	@RequestMapping("sample.json")
	@ResponseBody
	public String doGet(ModelMap modelMap) {
		JsonOutputResult jsonResult = new JsonOutputResult();
		jsonResult.setSuccess(true);
		jsonResult.pushToModel(modelMap);
		return jsonResult.toString();
	}
}
