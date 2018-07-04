package i.android.library.utils;

/**
 * Created by root on 2/11/18.
 */

public class ConvertUtil {

    /**convert an object to int
     * @param value the object to convert
     * @param defaultValue the default value
     **/
    public final static int convertToInt(Object value, int defaultValue) {
        if (StringUtil.isNull(value)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                /*remove the decimal point*/
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }

        }
    }
}