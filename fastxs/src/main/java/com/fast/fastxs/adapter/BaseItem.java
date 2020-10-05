package com.fast.fastxs.adapter;

/**
 * @ClassName: BaseItem
 * @Description:适用于LayouModelAdapter
 * @Author: 范明华
 * @Version: 1.0
 */
public class BaseItem<T> {
    protected int layoutId;
    private T data;

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseItem(int layoutId, T data) {
        this.layoutId = layoutId;
        this.data = data;
    }

    public BaseItem(T data) {
        this.data = data;
    }

}
