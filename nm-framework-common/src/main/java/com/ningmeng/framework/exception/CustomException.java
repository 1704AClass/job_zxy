package com.ningmeng.framework.exception;

import com.ningmeng.framework.model.response.ResultCode;

/**
 * 自定义CustomException异常，用于程序员自己抛出使用
 */
public class CustomException extends  RuntimeException{
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }
}
