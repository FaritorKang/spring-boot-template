package net.unmz.java.example.exception;


import net.unmz.java.example.constants.APIResponseCode;

/**
 * Project Name:
 * 功能描述：业务异常基类
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-3-9 15:40
 * @since JDK 1.8
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -7766387059695179506L;

    private final int errCode;

    public BizException() {
        super(APIResponseCode.GLOBAL_FAIL.getDesc());
        this.errCode = APIResponseCode.GLOBAL_FAIL.getCode();
    }

    public BizException(APIResponseCode apiResponseCode) {
        super(apiResponseCode.getDesc());
        this.errCode = apiResponseCode.getCode();
    }

    public BizException(APIResponseCode apiResponseCode, String msg) {
        super(msg);
        this.errCode = apiResponseCode.getCode();
    }

    public BizException(APIResponseCode apiResponseCode, Throwable t) {
        super(apiResponseCode.getDesc(), t);
        this.errCode = apiResponseCode.getCode();
    }

    public BizException(APIResponseCode apiResponseCode, String msg, Throwable t) {
        super(msg, t);
        this.errCode = apiResponseCode.getCode();
    }

    public int getErrCode() {
        return errCode;
    }
}
