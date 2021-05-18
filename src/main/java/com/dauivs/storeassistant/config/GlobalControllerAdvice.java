package com.dauivs.storeassistant.config;

import com.dauivs.storeassistant.common.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        return ResponseData.fail(ResponseData.MESSAGE_FAIL01, e.getMessage());
    }

}
