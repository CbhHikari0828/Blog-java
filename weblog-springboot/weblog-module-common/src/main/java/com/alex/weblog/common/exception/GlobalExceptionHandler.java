package com.alex.weblog.common.exception;

import com.alex.weblog.common.enums.ResponseCodeEnum;
import com.alex.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**\
     * 业务异常捕获
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({ BizException.class })
    @ResponseBody
    public Response<Object> handBizException(HttpServletRequest request, BizException e){
        log.warn("{} request fail,errorCode: {}, errorMessage: {}",request.getRequestURI(),e.getErrorCode(),e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 通用异常捕获
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Response<Object> handException(HttpServletRequest request, Exception e){
        log.warn("{} request fail",request.getRequestURI(),e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseBody
    public Response<Object> handMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e){
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        // 获取BuildResult
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        // 获取校验不通过字段，下为固定格式
        Optional.ofNullable(bindingResult.getFieldError()).ifPresent(errors -> {
            sb.append(errors.getField())
                    .append("  ")
                    .append(errors.getDefaultMessage())
                    .append(",当前值： '")
                    .append(errors.getRejectedValue())
                    .append("';");
                }
        );
        String errorMessage = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorCode,errorMessage);
    }
}
