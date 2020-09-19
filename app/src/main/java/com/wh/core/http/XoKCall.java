package com.wh.core.http;

import okhttp3.Call;

public class XoKCall {

    private Call mCall;
    private int status = UNFINISH;
    public static final int FINISH = 0;
    public static final int UNFINISH = -1;

    public XoKCall() {
    }

    public Call getCall() {
        return mCall;
    }

    public void setCall(Call call) {
        this.mCall = call;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
