package com.ningmeng.framework.exception;

import com.ningmeng.framework.model.response.ResultCode;

public class CustomExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
