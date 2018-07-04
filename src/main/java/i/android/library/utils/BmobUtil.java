package i.android.library.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

public class BmobUtil {


    public static void upload(final Context context, File file){

        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    ToastUtil.showSingle("图片上传成功" );
                    try {
                        MessageUtil.sendTextMessage(context,"图片地址:"+bmobFile.getFileUrl());
                        ToastUtil.showSingle("图片地址短信发送成功:"+bmobFile.getFileUrl());
                    } catch (Exception e1) {
                        ToastUtil.showE("发送图片短信:",e);
                    }
                }else{
                    ToastUtil.showBmob(e);
                    ToastUtil.showSingle("上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                if(value!=100) ToastUtil.showShort("上传中...");
            }
        });
    }


    /**
     * 注：

     1、有多少个文件上传，onSuccess方法就会执行多少次;

     2、通过onSuccess回调方法中的files或urls集合的大小与上传的总文件个数比较，如果一样，则表示全部文件上传成功。*/

    public static void upload(final String [] filePaths){



        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                ToastUtil.showShort("错误码"+statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    public static void configBmob(Context context){
        //设置BmobConfig
        BmobConfig config =new BmobConfig.Builder(context)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(500*1024)
                .build();
                Bmob.initialize(config);
    }
    public static void requestSmsCode(String num,@Nullable String template){
        if(template==null) template="验证码发送";
        BmobSMS.requestSMSCode(num,template, new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                    ToastUtil.showShort("验证码发送成功");
                }
                else ToastUtil.showShort(ex.getMessage());
            }
        });
    }
   public static String errorCode(int code){
        Log.e("code",String.valueOf(code));
        String errorCode;
        switch (code){
            case 202:errorCode="用户名已存在";break;
            case 203:errorCode="邮箱已被存在";break;
            case 207:errorCode="验证码错误";break;
            case 209:errorCode="手机号已存在";break;
            case 9010: errorCode="连接超时";break;
            case 9019: errorCode="格式不正确";break;
            case 101: errorCode="用户名或密码错误";break;
            default:errorCode="未知错误";
        }
        return errorCode;
   }

    public static void verifyEmail(final String email) {
        BmobUser.requestEmailVerify(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.showShort("请求验证邮件成功，请到" + email + "邮箱中进行激活。");
                }else{
                    ToastUtil.showShort("失败:" + e.getMessage());
                }
            }
        });
    }
    public static void resetPassByEmail(final String email){
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.showShort("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                } else {
                    ToastUtil.showShort("失败:" + e.getMessage());
                }
            }
        });
    }
}
