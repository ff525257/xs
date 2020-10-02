package com.fast.fastxs.http;

public interface XhttpCallback {
    void onFailure(Exception e, int code);
    void onResponse(String body, int code);
    void onProgress(long current, long total);
}
