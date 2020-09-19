package com.wh.core.layoutmodel;


import com.wh.R;

import java.io.Serializable;
import java.util.HashMap;

public class BaseBean implements Serializable {

    private HashMap<String, Object> data;
    protected int layoutId;


    public HashMap<String, Object> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }


    public static class Center_TitleBean extends  BaseBean{
        public Center_TitleBean() {
            layoutId = R.layout.center_text;
        }
    }
}