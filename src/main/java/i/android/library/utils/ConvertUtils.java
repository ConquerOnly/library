package i.android.library.utils;

public class ConvertUtils {

    public static int parseInt(String sInt){
        try{
           return Integer.parseInt(sInt);
        }catch (Exception e){
            ToastUtil.showShort(e.getMessage());
            return 5;
        }
    }
}
