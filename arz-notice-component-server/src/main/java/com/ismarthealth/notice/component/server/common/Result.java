package com.ismarthealth.notice.component.server.common;

import com.ismarthealth.notice.component.server.utils.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: Result
 * @Description: 统一返回结果
 * @date: 2017年2月24日 下午3:51:59
 * 200  Success
 * 201	Created
 * 401	Unauthorized
 * 403	Forbidden
 * 404	Not Found
 */
@ApiModel(description = "返回响应数据")
public class Result implements Serializable {

    @ApiModelProperty(value = "请求结果编码 200成功")
    private int returncode;
    @ApiModelProperty(value = "对象结果")
    private Object data;
    @ApiModelProperty(value = "结果条数")
    private Integer size;
    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "备注")
    private String remark;

    //构造方法
    public Result() {
    }

    //有参构造方法
    public Result(int code, Object data, Integer size, String message, String remark) {
        setReturncode(code);
        setData(data == null ? "" : data);
        setSize(size == null ? Integer.valueOf(0) : size);
        setMessage(message == null ? "" : message);
        setRemark(remark == null ? "" : remark);
    }

    //有参构造方法
    public Result(int code, Object data, String message, String remark) {
        setReturncode(code);
        setData(data);
        setMessage(message);
        setRemark(remark);
    }

    //有参构造方法
    public Result(int code, String message) {
        setReturncode(code);
        setMessage(message);
    }

    //有参构造方法
    public Result(int code, Object data) {
        setReturncode(code);
        setData(data);
    }

    //将Relust对象转换为json字符串
    public String toJson() {
        return JsonUtils.obj2String(this);
    }

    //测试
    public static void main(String[] args) {
        List<String> data = new ArrayList<String>();
        data.add("问卷");
        data.add("问题");
        data.add("选项");
        Result r = new Result(200, data, 0, "", "");
        System.out.println(r.toJson());
    }


    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
