package com.fast.fastxs.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 上传
 */
public class ProgressRequestBody extends RequestBody {

    public static final int UPDATE = 0x01;
    private RequestBody requestBody;
    private XhttpCallback mListener;
    private BufferedSink bufferedSink;
    private MyHandler myHandler;

    public ProgressRequestBody(RequestBody body, final XhttpCallback callback) {
        requestBody = body;
        mListener = callback;
        if (myHandler == null) {
            myHandler = new MyHandler();
        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    if (mListener != null) mListener.onProgress(progressModel.getCurrentBytes(), progressModel.getContentLength());
                    break;
            }
        }
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        //刷新
        bufferedSink.flush();
    }

    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                bytesWritten += byteCount;
                Message msg = Message.obtain();
                msg.what = UPDATE;
                msg.obj = new ProgressModel(bytesWritten, contentLength);
                myHandler.sendMessage(msg);
            }
        };
    }

    public class ProgressModel {
        private long currentBytes;
        private long contentLength;

        public long getCurrentBytes() {
            return currentBytes;
        }

        public long getContentLength() {
            return contentLength;
        }


        public ProgressModel(long currentBytes, long contentLength) {
            this.currentBytes = currentBytes;
            this.contentLength = contentLength;
        }
    }


}
