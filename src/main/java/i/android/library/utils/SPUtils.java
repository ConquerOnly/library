package i.android.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

    /**
     * SharedPreference
     *
     * @author cheyanxu
     *
     */
    public class SPUtils {
        private final static String SharedPreferencesNAME = "cam2mms_setting";
        public final static int TYPE_INT = 0;
        public final static int TYPE_STRING = 1;
        public final static int TYPE_BOOLEAN = 2;
        public final static int TYPE_FLOAT = 3;
        public final static int TYPE_LONG = 4;

        public static void saveIMSettingParams(Context context, Object key, Object value, int type) {
            SharedPreferences sp = context.getSharedPreferences(SharedPreferencesNAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            switch (type) {
                case 0:
                    editor.putInt(key.toString(), Integer.valueOf(String.valueOf(value)));
                    break;
                case 1:
                    editor.putString(key.toString(), String.valueOf(value));
                    break;
                case 2:
                    editor.putBoolean(key.toString(), Boolean.parseBoolean(String.valueOf(value)));
                    break;
                case 3:
                    editor.putFloat(key.toString(), Float.valueOf(String.valueOf(value)));
                    break;
                case 4:
                    editor.putLong(key.toString(), Long.valueOf(String.valueOf(value)));
                    break;
            }
            editor.commit();
        }

        public static int findIParamsInt(Context context, String key) {
            SharedPreferences sp = context.getSharedPreferences(SharedPreferencesNAME, Context.MODE_PRIVATE);
            return sp.getInt(key, -1);
        }

        public static String findIParamsString(Context context, String key) {
            SharedPreferences sp = context.getSharedPreferences(SharedPreferencesNAME, Context.MODE_PRIVATE);
            return sp.getString(key, "");
        }
        public static boolean findIParamsBoolean(Context context, String key) {
            SharedPreferences sp = context.getSharedPreferences(SharedPreferencesNAME, Context.MODE_PRIVATE);
            return sp.getBoolean(key, false);
        }
}
