package com.fast.fastxs.util;

public class BitUtils {
    /**
     * 判断是否是偶数
     * 运算规则是：当运算符两边相同位置都是相同，结果返回0，不相同时返回1。
     *
     * @param num
     * @return
     */
    public boolean isEven(int num) {
        return (num & 1) == 0;
    }

    /**
     * 添加
     */
    public void enable(int flag, int permission) {
        flag |= permission;
    }

    /**
     * 删除
     */
    public void disable(int flag, int permission) {
        flag &= ~permission;
    }

}
