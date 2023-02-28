package com.douzone.mysite.dto;

public class JsonResult {
	//Data 포맷
	private String result;	/*success or fail */
	private Object data;    /* success 했다면 set */
	private String message; /* fail 했으면 set success 시에는 null*/
	
	//기본 생성자 private으로 생성 못하게 하는 Factory method 제공
	private JsonResult() {};
	
	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	private JsonResult(String message) {
		this.result = "fail";
		this.message = message;
	}
	public static JsonResult success(Object data) {
		//성공시 success 함수 불러서 사용
		return new JsonResult(data);
	}
	
	public static JsonResult fail(String message) {
		//실패시
		return new JsonResult(message);
	}
	
	public String getResult() {
		return result;
	}
	public Object getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}
}
