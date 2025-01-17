package com.fast.fastxs.http.reqeusttype;

import com.fast.fastxs.config.BaseConfig;
import com.fast.fastxs.util.JSONUtil;
import com.fast.fastxs.util.LogUtils;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 上传格式
 */
public class FileUpLoadHMap extends HashMap<String, String> {

    /**
     * key是文件名称,
     * value是文件地址
     * @param content
     */
    public FileUpLoadHMap(final HashMap<String, Object> content) {
        try {
            put(BaseConfig.Http.FILETYPE, JSONUtil.mapToJson(content));
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
    }

    public HashMap<String, Object> getFileData() {
        try {
            HashMap<String, Object> data = (HashMap<String, Object>) JSONUtil.parseJsonResponse(getStringData());
            return data;
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
        return null;
    }

    public String getStringData() {
        return get(BaseConfig.Http.FILETYPE);
    }
}
