package com.wh.core.http;

import com.wh.core.common.util.JSONUtil;
import com.wh.core.common.util.LogUtils;
import com.wh.core.config.BaseConfig;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 下载格式
 */
public class DownloadMap extends HashMap<String, String> {

    public DownloadMap(String dir, String fileName) {
        try {
            HashMap<String, Object> content = new HashMap<>();
            content.put(BaseConfig.Http.KEY_FILENAME, fileName);
            content.put(BaseConfig.Http.DOWNLOADDIR, dir);
            put(BaseConfig.Http.DOWNLOADTYPE, JSONUtil.mapToJson(content));
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
    }

    public HashMap<String, Object> getDownloadFileData() {
        try {
            HashMap<String, Object> data = (HashMap<String, Object>) JSONUtil.parseJsonResponse(getStringData());
            return data;
        } catch (JSONException e) {
            LogUtils.e("mapToJson err", e);
        }
        return null;
    }

    public String getStringData() {
        return get(BaseConfig.Http.DOWNLOADTYPE);
    }

    public String getDownloadDir() {
        return getDownloadFileData().get(BaseConfig.Http.DOWNLOADDIR).toString();
    }

    public String getDownloadFileName() {
        return getDownloadFileData().get(BaseConfig.Http.KEY_FILENAME).toString();
    }

    public String getPach() {
        return getDownloadDir() + "" + getDownloadFileName();
    }
}
