package com.stylefeng.guns.common.constant.tips;

import java.util.Map;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.code = 200;
        super.message = "操作成功";
    }


    public SuccessTip(Object obj) {
        super.code = 200;
        super.message = "操作成功";
        super.data = obj;
    }
}
