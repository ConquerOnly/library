package i.android.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 * @author
 * @class PermissionManager
 * @date 2016-3-25 下午3:54:14
 */
public class PermissionManager {

    private Object mObject;
    private String[] mPermissions;
    private int mRequestCode;
    private PermissionListener mListener;
    // 用户是否确认了解释框的
    private boolean mIsPositive = false;

    public static boolean check(Context context, String  permission){
       if( ActivityCompat.checkSelfPermission(context,permission)!=PackageManager.PERMISSION_GRANTED)
           return false;
       else return true;
    }
    public  void camera(final View snackView){
                //添加权限请求码
                addRequestCode(Constants.REQUEST_CODE_CAMERA)
                //设置权限，可以添加多个权限
                .permissions(Manifest.permission.CAMERA)
                //设置权限监听器
                .setPermissionsListener(new PermissionListener() {

                    @Override
                    public void onGranted() {
                        //当权限被授予时调用
                        ToastUtil.showShort( "Camera Permission granted");
                    }

                    @Override
                    public void onDenied() {
                        //用户拒绝该权限时调用
                        ToastUtil.showShort( "Camera Permission denied");
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）
                        Snackbar.make(snackView, "需要相机权限去拍照", Snackbar.LENGTH_INDEFINITE)
                                .setAction("ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //必须调用该`setIsPositive(true)`方法
                                        setIsPositive(true);
                                        request();
                                    }
                                }).show();
                    }
                })
                //请求权限
                .request();
    }

    public static PermissionManager with(Activity activity) {
        return new PermissionManager(activity);
    }

    public static PermissionManager with(Fragment fragment) {
        return new PermissionManager(fragment);
    }

    public PermissionManager permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionManager addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionManager setPermissionsListener(PermissionListener listener) {
        this.mListener = listener;
        return this;
    }

    public PermissionManager(Object object) {
        this.mObject = object;
    }

    /**请求权限
     * @return PermissionManager
     */
    public PermissionManager request() {
        request(mObject, mPermissions, mRequestCode);
        return this;
    }

    private void request(Object object, String[] permissions, int requestCode) {
        // 根据权限集合去查找是否已经授权过
        Map<String, List<String>> map = findDeniedPermissions(getActivity(object), permissions);
        List<String> deniedPermissions = map.get("deny");
        List<String> rationales = map.get("rationale");
        if (deniedPermissions.size() > 0) {
            // 第一次点击deny才调用，mIsPositive是为了防止点确认解释框后调request()递归调onShowRationale
            if (rationales.size() > 0 && mIsPositive == false) {
                if (mListener != null ) {
                    mListener.onShowRationale(rationales.toArray(new String[rationales.size()]));
                }
                return;
            }
            if (object instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) object, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            if (mListener != null) {
                mListener.onGranted();
            }
        }
    }

    /**根据requestCode处理响应的权限
     * @param permissions
     * @param results
     */
    public void onPermissionResult(String[] permissions, int[] results) {
        List<String> deniedPermissions = new ArrayList<String>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {//未授权
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            if (mListener != null) {
                mListener.onDenied();
            }
        } else {
            if (mListener != null) {
                mListener.onGranted();
            }
        }
    }

    private Map<String, List<String>> findDeniedPermissions(Activity activity, String... permissions) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> denyList = new ArrayList<String>();//未授权的权限
        List<String> rationaleList = new ArrayList<String>();//需要显示提示框的权限
        for (String value : permissions) {
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                denyList.add(value);
                if (shouldShowRequestPermissionRationale(value)) {
                    rationaleList.add(value);
                }
            }
        }
        map.put("deny", denyList);
        map.put("rationale", rationaleList);
        return map;
    }

    private Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * 当用户拒绝某权限时并点击就不再提醒的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如给个展示对话框）
     * @param permission
     */
    private boolean shouldShowRequestPermissionRationale(String permission) {
        if (mObject instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) mObject, permission);
        } else if (mObject instanceof Fragment) {
            return ((Fragment) mObject).shouldShowRequestPermissionRationale(permission);
        } else {
            throw new IllegalArgumentException(mObject.getClass().getName() + " is not supported");
        }
    }

    public void setIsPositive(boolean isPositive) {
        this.mIsPositive = isPositive;
    }
    /**权限监听器*/
    public interface PermissionListener{

        /**
         * 用户授权后调用
         */
        public void  onGranted();

        /**
         * 用户禁止后调用
         */
        public void  onDenied();

        /**是否显示阐述性说明
         * @param permissions 返回需要显示说明的权限数组
         */
        public void onShowRationale(String[] permissions);
    }
}
