package com.fastxs.simple.adapter.dataobj;

/**
 * 普通的列表数据封装
 * @param <T>
 */
public class GeneralListObj<T> {
    public T data;
    public String title;

    public GeneralListObj(String title, T data) {
        this.data = data;
        this.title = title;
    }
}