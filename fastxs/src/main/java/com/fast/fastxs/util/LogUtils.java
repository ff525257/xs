package com.fast.fastxs.util;

import android.util.Log;

public class LogUtils {

    public final static String LOGTAG = "AlarmClock";


    public static void v(String message, Object... args) {
        Log.v(LOGTAG, args == null || args.length == 0
                ? message : String.format(message, args));
    }

    public static void v(String tag, String message, Object... args) {
        Log.v(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void d(String message, Object... args) {
        Log.d(LOGTAG, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void d(String tag, String message, Object... args) {
        Log.d(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void i(String message, Object... args) {
        Log.i(LOGTAG, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void i(String tag, String message, Object... args) {
        Log.i(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void w(String message, Object... args) {
        Log.w(LOGTAG, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void w(String tag, String message, Object... args) {
        Log.w(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void w(String tag, String message, Exception e) {
        Log.w(LOGTAG + "/" + tag, message, e);
    }

    public static void e(String message, Object... args) {
        Log.e(LOGTAG, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void e(String tag, String message, Object... args) {
        Log.e(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void e(String message, Exception e) {
        Log.e(LOGTAG, message, e);
    }

    public static void e(String tag, String message, Exception e) {
        Log.e(tag, message, e);
    }

    public static void wtf(String message, Object... args) {
        Log.wtf(LOGTAG, args == null || args.length == 0 ? message
                : String.format(message, args));
    }

    public static void wtf(String tag, String message, Object... args) {
        Log.wtf(tag, args == null || args.length == 0 ? message
                : String.format(message, args));
    }
}
