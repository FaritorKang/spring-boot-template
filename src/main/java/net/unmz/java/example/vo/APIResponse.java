package net.unmz.java.example.vo;

import lombok.Data;
import net.unmz.java.example.constants.APIResponseCode;

import java.io.Serializable;

/**
 * Project Name:
 * 功能描述：
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-3-9 15:05
 * @since JDK 1.8
 */
@Data
public class APIResponse<T> implements Serializable {

    private static final long serialVersionUID = -652805772373199612L;

    private int code;
    private String desc;
    private long timestamp;
    private T data;

    public APIResponse() {
    }

    public APIResponse(APIResponseCode code) {
        this.code = code.getCode();
        this.desc = code.getDesc();
    }

    public APIResponse(APIResponseCode code, String desc) {
        this.code = code.getCode();
        this.desc = desc;
    }

    public APIResponse(APIResponseCode code, T t) {
        this.code = code.getCode();
        this.desc = code.getDesc();
        this.data = t;
    }

    public APIResponse(APIResponseCode code, String desc, T t) {
        this.code = code.getCode();
        this.desc = desc;
        this.data = t;
    }


    /**
     * 成功
     */
    public static <T> APIResponse<T> ok(T t) {
        return new APIResponse<>(APIResponseCode.GLOBAL_SUCCESS, t);
    }

    /**
     * 失败
     */
    public static <T> APIResponse<T> fail(APIResponseCode errCode) {
        return new APIResponse<>(errCode);
    }

    /**
     * 失败
     */
    public static <T> APIResponse<T> fail(APIResponseCode errCode, String errMsg) {
        return new APIResponse<>(errCode, errMsg);
    }

}
