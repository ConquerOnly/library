package i.android.library.utils;

/**
 * Created by root on 1/28/18.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
/**
 * an util class for sharing text and image
 * */
public class ShareUtil {



    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void shareText(Context context, int stringRes) {
        shareText(context, context.getString(stringRes));
    }

    public static void shareText(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, "分享"));
    }
}