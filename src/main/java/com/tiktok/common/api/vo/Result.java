package com.tiktok.common.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiktok.common.contants.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *   接口返回数据格式
 * @author scott
 * @email jeecgos@163.com
 * @date  2019年1月19日
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class Result<T>  {

	private static final long serialVersionUID = 1L;


	/**
	 * 返回处理消息
	 */
	@ApiModelProperty(value = "返回处理消息")
	private String status_msg = "";

	/**
	 * 返回代码
	 */
	@ApiModelProperty(value = "返回代码")
	private Integer status_code = 0;
	
	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;
	
	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	private long timestamp = System.currentTimeMillis();


	public Result() {
	}

    /**
     * 兼容VUE3版token失效不跳转登录页面
     * @param status_msg
     */
	public Result(Integer status_code, String status_msg) {
		this.status_code = status_code;
		this.status_msg = status_msg;
	}


	public static<T> Result<T> ok() {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		return r;
	}

	public static<T> Result<T> ok(String msg) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		//Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
		r.setResult((T) msg);
		r.setStatus_msg(msg);
		return r;
	}

	public static<T> Result<T> ok(T data) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	public static<T> Result<T> OK() {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		return r;
	}

	/**
	 * 此方法是为了兼容升级所创建
	 *
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static<T> Result<T> OK(String msg) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		r.setStatus_msg(msg);
		//Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
		r.setResult((T) msg);
		return r;
	}

	public static<T> Result<T> OK(T data) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}
	public static<T> Result<T> OKORERROE(Boolean isSucess ,String successMsg,T successData,String errorMsg,String code,String errcode) {
		Result<T> r = new Result<T>();
		if(isSucess){
			r.setStatus_msg(successMsg);
			r.setStatus_code(Integer.parseInt(code));
			r.setResult(successData);
		}else{
			r.setStatus_code(Integer.parseInt(errcode));
			r.setStatus_msg(errorMsg);
			return r;
		}
		return r;

	}
	public static<T> Result<T> OKORERROE(Boolean isSucess ,String successMsg,T successData,String errorMsg) {
		return (Result<T>) OKORERROE(isSucess , successMsg, successData, errorMsg,"200","500");
	}
	public static<T> Result<T> OK(String msg, T data) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_OK_200);
		r.setStatus_msg(msg);
		r.setResult(data);
		return r;
	}

	public static<T> Result<T> error(String msg, T data) {
		Result<T> r = new Result<T>();
		r.setStatus_code(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
		r.setStatus_msg(msg);
		r.setResult(data);
		return r;
	}

	public static<T> Result<T> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static<T> Result<T> error(int code, String msg) {
		Result<T> r = new Result<T>();
		r.setStatus_code(code);
		r.setStatus_msg(msg);
		return r;
	}

	public Result<T> error500(String status_msg) {
		this.status_msg = status_msg;
		this.status_code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		return this;
	}



	/**
	 * 无权限访问返回结果
	 */
	public static<T> Result<T> noauth(String msg) {
		return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
	}

	@JsonIgnore
	private String onlTable;

}