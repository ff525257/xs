package com.fast.fastxs.http;

import com.fast.fastxs.config.BaseConfig;
import com.fast.fastxs.http.reqeusttype.DownloadMap;
import com.fast.fastxs.http.reqeusttype.FileUpLoadHMap;
import com.fast.fastxs.http.reqeusttype.JsonHashMap;
import com.fast.fastxs.util.LogUtils;
import com.fast.fastxs.util.XStringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 负责版本更新部分的http请求和资源数据不存在时向服务器请求数据 要求异步请求和同步请求，返回请求的数据 可以设置头部信息
 */
public class HttpEngine extends BaseHttpEngine {

    private static final String TAG = "HttpEngine";

    /**
     * @param url    发送通信的地址
     * @param method GET POST 代表当前发送的是get或者post请求
     */
    public XoKCall httpSend(final String url, BaseConfig.Http.Method method, final HashMap<String, String> header, final HashMap<String, String> body, final XhttpCallback callback)
            throws Exception {
        final XoKCall xcall = new XoKCall();
        Request request = null;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(BaseConfig.Http.CONNECTTIMEOUT, TimeUnit.MILLISECONDS).build();
        try {
            LogUtils.w(TAG, "HttpEngine " + " httpSend  url= " + url + " start");
            LogUtils.i(TAG, "httpSend url:" + url);
            LogUtils.i(TAG, "httpSend header:" + header.toString());

            Headers headers = Headers.of(header);
            if (BaseConfig.Http.Method.POST == method) {
                RequestBody requestBody = null;
                if (body instanceof JsonHashMap) {
                    LogUtils.i(TAG, "httpSend body[jsontype] :" + ((JsonHashMap) body).getStringData());
                    requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ((JsonHashMap) body).getStringData());
                } else if (body instanceof FileUpLoadHMap) {
                    MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    HashMap<String, Object> fileDatas = ((FileUpLoadHMap) body).getFileData();
                    for (Map.Entry<String, Object> entry : fileDatas.entrySet()) {
                        String fileName = entry.getKey();
                        String filePath = entry.getValue().toString();
                        multipartBodyBuilder.addFormDataPart("file", fileName, RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath)));
                    }
                    LogUtils.i(TAG, "httpSend body[filetype] :" + fileDatas.toString());
                    requestBody = multipartBodyBuilder.build();
                    requestBody = new ProgressRequestBody(requestBody, callback);
                } else {
                    LogUtils.i(TAG, "httpSend body[generaltype] :" + body.toString());
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    Iterator iter = body.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        if (entry.getKey() != null && entry.getValue() != null) {
                            String key = entry.getKey().toString();
                            String val = entry.getValue().toString();
                            formBodyBuilder.add(key, val);
                        }
                    }
                    requestBody = formBodyBuilder.build();
                }
                request = new Request.Builder().headers(headers).url(url).post(requestBody).build();
            } else {
                LogUtils.i(TAG, "httpSend body[get] :" + body.toString());
                request = new Request.Builder().headers(headers).url(XStringUtils.addParamsToHttpUrl(url, body)).build();
            }
            Call call = okHttpClient.newCall(request);
            xcall.setCall(call);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e, -1);
                    xcall.setStatus(XoKCall.FINISH);
                    LogUtils.w(TAG, "HttpEngine onFailure" + e);
                    LogUtils.w(TAG, "HttpEngine " + " httpSend url= " + url + " end");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    xcall.setStatus(XoKCall.FINISH);
                    if (body instanceof DownloadMap) {
                        HashMap<String, Object> downMap = ((DownloadMap) body).getDownloadFileData();
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        File dir = new File(downMap.get(BaseConfig.Http.DOWNLOADDIR).toString());
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, downMap.get(BaseConfig.Http.KEY_FILENAME).toString());
                        try {
                            is = response.body().byteStream();
                            long total = response.body().contentLength();
                            fos = new FileOutputStream(file);
                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                sum += len;
                                callback.onProgress(sum, total);
                            }
                            fos.flush();
                            //callback.onResponse("", code);
                            LogUtils.w(TAG, "HttpEngine code:" + code);
                            LogUtils.w(TAG, "HttpEngine " + " httpSend url= " + url + " end");
                        } catch (Exception e) {
                            callback.onFailure(e, call.hashCode());
                        } finally {
                            try {
                                if (is != null)
                                    is.close();
                            } catch (IOException e) {
                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                            }
                        }
                    } else {
                        String result = response.body().string();
                        LogUtils.w(TAG, "HttpEngine code:" + code);
                        LogUtils.w(TAG, "HttpEngine body:" + result);
                        LogUtils.w(TAG, "HttpEngine " + " httpSend url= " + url + " end");
                        callback.onResponse(result, code);
                    }
                }
            });
        } catch (Exception e) {
            xcall.setStatus(XoKCall.FINISH);
        }

        return xcall;
    }

}
