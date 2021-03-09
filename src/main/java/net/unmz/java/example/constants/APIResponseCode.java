package net.unmz.java.example.constants;

/**
 * Project Name:
 * 功能描述：
 *
 * @author faritor
 * @version 1.0
 * @date 2020/11/26 14:30
 * @since JDK 1.8
 */
public enum APIResponseCode {

    /**
     * 全局异常码
     */
    GLOBAL_SUCCESS(0, "成功"),
    GLOBAL_FAIL(1, "失败"),
    GLOBAL_UNAUTHORIZED(401, "登陆信息已失效，请重新登陆"),
    GLOBAL_ACCESS_DENIED(403, "访问被拒绝"),
    GLOBAL_NO_SUPPORTED_REQUEST_METHOD(405, "不支持的请求方式"),
    GLOBAL_EXCEPTION(500, "系统繁忙，请稍后重试"),

    ;

    private final int code;
    private final String desc;

    APIResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static APIResponseCode getCodeByCode(int code) {
        for (APIResponseCode e : values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
