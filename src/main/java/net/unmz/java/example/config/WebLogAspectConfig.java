package net.unmz.java.example.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.*;
import java.util.stream.Stream;

/**
 * Project Name:
 * 功能描述：接口请求与响应日志打印
 *
 * @author faritor
 * @version 1.0
 * @date 2020/11/24 14:59
 * @since JDK 1.8
 */
@Slf4j
@Aspect
@Component
@Order(0)
public class WebLogAspectConfig {

    /**
     * 忽略打印的参数key
     */
    private static final Set<String> IGNORE_KYE_SET = new HashSet<>();
    public static final String CONSTANTS_REQUEST = "request";


    static {
        IGNORE_KYE_SET.add("password");
        IGNORE_KYE_SET.add("pwd");
        IGNORE_KYE_SET.add("passwd");
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * net.unmz..*.controller..*.*(..)) " +
            "|| execution(public * net.unmz..*.web..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Map<String, String[]> paramsMap = new HashMap<>(request.getParameterMap());//将请求参数放入一个新的Map中,为了可以将密码字段移除

            // 记录下请求内容
            log.info("=================================== http request params start ===================================");
            log.info("|| Request Ip:      {}", request.getRemoteAddr());
            log.info("|| Request Url:     {}", request.getRequestURL());
            log.info("|| Request Path:    {}", request.getServletPath());
            log.info("|| Request Method:  {}", request.getMethod());
            log.info("|| Request Params:  {}", printParams(joinPoint, paramsMap));
            log.info("=================================== http request params end   ===================================");
        } else {
            log.error("初始化请求失败,ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()");
        }
    }

    private String printParams(JoinPoint joinPoint, Map<String, String[]> paramsMap) {
        // 打印请求内容
        // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
        Object[] objArray = joinPoint.getArgs();
        Stream<?> stream = ArrayUtils.isEmpty(objArray) ? Stream.empty() : Arrays.stream(objArray);
        Object[] args = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))).toArray();

        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名

        for (int i = 0; i < args.length; i++) {
            String argsName = argNames[i];
            Object argsValue = args[i];
            if (!(argsValue instanceof ExtendedServletRequestDataBinder) && !(argsValue instanceof HttpServletResponseWrapper)) {
                if (!IGNORE_KYE_SET.contains(argsName) && !CONSTANTS_REQUEST.equalsIgnoreCase(argsName)) {
                    String[] argsString = new String[1];
                    argsString[0] = JSONObject.toJSONString(argsValue);
                    paramsMap.put(argsName, argsString);
                }
            }
        }
        return JSONObject.toJSONString(paramsMap);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 记录下响应内容
        log.info("=================================== http response params start ==================================");
        log.info("|| Request to Response time: {} ms", (System.currentTimeMillis() - startTime.get()));
        log.info("|| Response info: {} ", JSONObject.toJSONString(ret));
        log.info("=================================== http response params end   ==================================");
    }

}
