package com.boot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息类，具体使用可以参照{@link MessageHandler}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回数据")
public class BaseMessage<T> {
	@ApiModelProperty("true成功，false失败")
	private boolean success;
	
	@ApiModelProperty("数据")
	private T data;
	
	@ApiModelProperty("提示信息")
	private String message;
	
	@ApiModelProperty("数据总条数")
	private Integer count;
	
	@ApiModelProperty("1成功，0失败")
	private Integer code;

}
