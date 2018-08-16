package com.stylefeng.guns.common.constant.tips;

import com.stylefeng.guns.common.exception.BizExceptionEnum;

/**
 * 返回给前台的错误提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public static ErrorTip error() {
        return new ErrorTip(999, "系统错误,请联系管理员");
    }

    public static ErrorTip error(String msg) {
        return new ErrorTip(600, msg);
    }

    public ErrorTip(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }
}
