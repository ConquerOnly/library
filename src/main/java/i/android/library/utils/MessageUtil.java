package i.android.library.utils;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class MessageUtil {
    // 发送短信的方法
    public static void sendTextMessage(Context context, String content) {
        // 获取监护人的电话
        String destinationAddress = SPUtils.findIParamsString(context, SPKeyConstant.PHONE_NUM);
        // String content = "用户您好,您的亲人在" + LocationService.address +
        // "遇到了紧急情况,需要您的帮助!";
        // 获取SMS管理器
        SmsManager smsManager = SmsManager.getDefault();
        if (destinationAddress != null && !"".equals(destinationAddress.trim())) {
            if (content.length() >= 70) {
                ArrayList<String> list = smsManager.divideMessage(content);
                for (String str : list) {
                    smsManager.sendTextMessage(destinationAddress, null, str, null, null);
                }
            } else {
                smsManager.sendTextMessage(destinationAddress, null, content, null, null);
            }
        }
    }
}
