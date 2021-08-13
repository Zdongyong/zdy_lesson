package com.zdy.log.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Pair;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

public final class LogUtils {
    private LogUtils() {
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        } else {
            for (Throwable t = tr; t != null; t = t.getCause()) {
                if (t instanceof UnknownHostException) {
                    return "";
                }
            }

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
    }

    public static String logLevel(int value) {
        switch (value) {
            case 2:
                return "VERBOSE";
            case 3:
                return "DEBUG";
            case 4:
                return "INFO";
            case 5:
                return "WARN";
            case 6:
                return "ERROR";
            case 7:
                return "ASSERT";
            case 8:
                return "NET";
            default:
                return "UNKNOWN";
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionName : null;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return info;
    }
}
