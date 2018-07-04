package i.android.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BMUtils {
    /* 给定一个Bitmap，进行保存 */
    public static String saveJpeg(Bitmap bm) {
        String tag=".method:saveJpeg";
        String savePath = "/mnt/sdcard/rectPhoto/";
        File folder = new File(savePath);
        if (!folder.exists()) {// 如果文件夹不存在则创建
            folder.mkdir();
        }
        long dataTake = System.currentTimeMillis();
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        // String str = sdf.format(new Date(dataTake));
        String jpegName = savePath + dataTake + ".jpg";
        Log.i(tag, "saveJpeg:jpegName--" + jpegName);
        // File jpegFile = new File(jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);

            // //如果需要改变大小(默认的是宽960×高1280),如改成宽600×高800
            // Bitmap newBM = bm.createScaledBitmap(bm, 600, 800, false);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i(tag, "saveJpeg：存储完毕！");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i(tag, "saveJpeg:存储失败！");
            e.printStackTrace();
            return null;
        }
        return jpegName;
    }

    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static Bitmap readBitmapFromAssets(InputStream is) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 从SD卡文件中读取Bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap readBitmapFromFile(String filePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeFile(filePath, opt);
    }

    // 计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(byte[] bytes) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 320, 240);      //640     480
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return BitmapFactory.decodeByteArray(b, 0, b.length, options);
    }

    /**
     * 从字节中读取Bitmap
     *
     * @param bytes
     * @return
     */
    public static Bitmap readBitmapFromBytes(byte[] bytes) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
    }
}
