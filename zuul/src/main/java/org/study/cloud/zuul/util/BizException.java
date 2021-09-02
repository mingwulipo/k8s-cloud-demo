package org.study.cloud.zuul.util;

/**
 * 业务异常
 *
 * @author <a href="mailto:simonzbf@163.com">大兵</a>
 * @version 1.0 18/4/23
 * @since 1.0
 */
public class BizException extends RuntimeException {
    private Integer code;

    /**
     * 无参构造
     */
    public BizException() {
        super();
    }

    public BizException(Integer status, String message) {
        super(message);
        this.code = status;
    }

    public BizException(String message) {
        super(message);
        this.code = ResultCodeEnum.FAILURE.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
