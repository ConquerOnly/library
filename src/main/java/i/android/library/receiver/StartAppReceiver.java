package i.android.library.receiver;

/**
 * Created by root on 1/28/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 启动程序广播接收器
 *
 */
public class StartAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName()));
    }

}
