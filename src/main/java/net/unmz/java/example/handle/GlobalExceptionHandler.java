package net.unmz.java.example.handle;

import lombok.extern.slf4j.Slf4j;
import net.unmz.java.example.constants.APIResponseCode;
import net.unmz.java.example.exception.BizException;
import net.unmz.java.example.vo.APIResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(BizException.class)
    public APIResponse<?> bizExceptionErrorHandler(HttpServletRequest request, BizException e) {
        log.warn("请求业务错误. url: {}, errCode: {}, errMsg: {}", request.getRequestURI(), e.getErrCode(), e.getMessage());
        return APIResponse.fail(APIResponseCode.getCodeByCode(e.getErrCode()), e.getMessage());
    }

    /**
     * 拦截所有非自定义的异常
     */
    @ExceptionHandler(Exception.class)
    public APIResponse<?> exceptionHandle(HttpServletRequest request, Exception ex) {
        log.error("请求异常. url: {}", request.getRequestURI(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, "系统繁忙，请稍后重试");
    }

    /**
     * 拦截不支持的请求方式的异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResponse<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error("不支持当前请求方法. url: " + request.getRequestURI(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_NO_SUPPORTED_REQUEST_METHOD, APIResponseCode.GLOBAL_NO_SUPPORTED_REQUEST_METHOD.getDesc());
    }

    /**
     * 拦截找不到地址栏中的参数的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public APIResponse<?> handleMMissingPathVariableException(HttpServletRequest request, MissingPathVariableException ex) {
        log.error("请求异常. url: {} 找不到路径中的参数,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, "参数错误");
    }

    /**
     * 拦截参数校验后的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public APIResponse<?> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("请求异常. url: {} 参数有误,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, "参数错误");
    }

    /**
     * 拦截地址不存在的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public APIResponse<?> handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        log.error(" 请求异常. url: {} 地址有误,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_ACCESS_DENIED, APIResponseCode.GLOBAL_ACCESS_DENIED.getDesc());// 不在暴露地址不存在的异常,以防有心人遍历
    }

    /**
     * 拦截地址不存在的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public APIResponse<?> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request, HttpMediaTypeNotAcceptableException ex) {
        log.error(" 请求异常. url: {} 地址有误,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_ACCESS_DENIED);// 不在暴露地址不存在的异常,以防有心人遍历
    }

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public APIResponse<?> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            log.error("请求异常. url: {}  参数错误ConstraintViolationException,{}", request.getRequestURI(), ex.getMessage(), ex);
            return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, errorMessage);
        }
        log.error("请求异常. url: {}  参数错误ConstraintViolationException,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, ex.getMessage());
    }

    /**
     * 捕获注解校验参数的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            String errorMessage = getErrorMessage(objectErrors);
            log.error("请求异常. url: {} 参数错误MethodArgumentNotValidException,{}", request.getRequestURI(), ex.getMessage(), ex);
            return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, errorMessage);
        }
        log.error("请求异常. url: {} 参数错误MethodArgumentNotValidException,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, ex.getMessage());
    }

    /**
     * 捕获注解校验参数的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public APIResponse<?> handleBindException(HttpServletRequest request, BindException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            String errorMessage = getErrorMessage(objectErrors);
            log.error("请求异常. url: {} 参数错误BindException,{}", request.getRequestURI(), ex.getMessage(), ex);
            return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, errorMessage);
        }
        log.error("请求异常. url: {} 参数错误BindException,{}", request.getRequestURI(), ex.getMessage(), ex);
        return APIResponse.fail(APIResponseCode.GLOBAL_EXCEPTION, ex.getMessage());
    }

    private String getErrorMessage(List<ObjectError> objectErrors) {
        StringBuilder msgBuilder = new StringBuilder();
        for (ObjectError objectError : objectErrors) {
            msgBuilder.append(objectError.getDefaultMessage()).append(",");
        }
        String errorMessage = msgBuilder.toString();
        if (errorMessage.length() > 1) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        return errorMessage;
    }
}
