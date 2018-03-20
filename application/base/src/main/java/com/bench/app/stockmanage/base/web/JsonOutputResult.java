package com.bench.app.stockmanage.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.RequestContext;

import com.bench.app.stockmanage.base.EnumBase;
import com.bench.app.stockmanage.base.ErrorCode;
import com.bench.app.stockmanage.base.utils.ListUtils;
import com.bench.app.stockmanage.base.utils.ToStringObject;

/**
 * JSON输出结果
 * 
 * @author chenbug
 * 
 * @version $Id: JsonOutputResult.java, v 0.1 2014-4-25 下午2:27:31 chenbug Exp $
 */
public class JsonOutputResult extends ToStringObject {

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 错误
	 */
	private ErrorEntry error;

	/**
	 * 详细信息
	 */
	private String detailMessage;

	/**
	 * 其他输出属性
	 */
	private Map<String, Object> outputParameters = new HashMap<String, Object>();

	/**
	 * 字段错误
	 */
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	/**
	 * 设置错误枚举
	 * 
	 * @param errorEnum
	 */
	public void setErrorEnum(EnumBase errorEnum) {
		if (errorEnum == null) {
			return;
		}
		this.error = new ErrorEntry(errorEnum);
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * 设置验证结果集
	 * 
	 * @param result
	 * @param request
	 */
	public void setBindingResult(BindingResult result, HttpServletRequest request) {
		if (ListUtils.size(result.getAllErrors()) > 0) {
			RequestContext requestContext = new RequestContext(request);
			for (org.springframework.validation.ObjectError objectError : result.getAllErrors()) {
				if (objectError instanceof org.springframework.validation.FieldError) {
					org.springframework.validation.FieldError originalError = (org.springframework.validation.FieldError) objectError;
					FieldError error = new FieldError();
					error.setName(originalError.getField());
					error.setError(new ErrorEntry(originalError.getCode(), requestContext.getMessage(originalError)));
					fieldErrors.add(error);
				} else {
					this.setError(new ErrorEntry(objectError.getCode(), requestContext.getMessage(objectError)));
				}
			}
			this.setSuccess(false);
		}
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	/**
	 * 将结果集合push到modelMap
	 * 
	 * @param model
	 */
	public void pushToModel(ModelMap model) {
		model.addAttribute("success", this.success);
		model.addAttribute("error", this.error);
		model.addAttribute("detailMessage", this.detailMessage);
		model.addAttribute("outputParameters", this.outputParameters);
		model.addAttribute("fieldErrors", this.fieldErrors);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorEntry getError() {
		return error;
	}

	public void setError(ErrorEntry error) {
		this.error = error;
	}

	public void setError(ErrorCode errorCode) {
		if (errorCode == null) {
			return;
		}
		this.error = new ErrorEntry(errorCode);
	}

	public Map<String, Object> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Map<String, Object> outputParameters) {
		this.outputParameters = outputParameters;
	}

	public void setOutputParameter(String key, Object value) {
		this.outputParameters.put(key, value);
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
}
