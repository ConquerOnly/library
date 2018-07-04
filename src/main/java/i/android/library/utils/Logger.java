package i.android.library.utils;

import android.text.TextUtils;
import android.util.Log;

public class Logger {
    public static void e(String tag, String message){
        if(TextUtils.isEmpty(message)) Log.e(tag,"空");
        else Log.e(tag,message);
    }
}
