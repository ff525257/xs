package com.wh.core.http;

import com.wh.core.common.util.JSONUtil;
import com.wh.core.common.util.LogUtils;
import com.wh.core.config.BaseConfig;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 数据为json格式
 */
public class JsonHashMap extends HashMap<String, String> {

    public JsonHashMap(final HashMap<String, Object> content) {
        try {
            put(BaseConfig.Http.JSONTYPE, JSONUtil.mapToJson(content));
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
    }

    public HashMap<String, Object> getJsonData() {
        try {
            HashMap<String, Object> data = (HashMap<String, Object>) JSONUtil.parseJsonResponse(getStringData());
            return data;
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
        return null;
    }

    public String getStringData() {
        return get(BaseConfig.Http.JSONTYPE);
    }
}
