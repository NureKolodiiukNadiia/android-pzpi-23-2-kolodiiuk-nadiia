package nadiia.kolodiiuk.practtask4;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    public static <T> void writeToSharedPreferences(
            Context context, String prefsName, String key, T value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                prefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else {
            editor.putInt(key, (int) value);
        }
        editor.apply();
    }

    public static String readStringFromSharedPreferences(Context context, String prefsName, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "default_value");
    }

    public static int readIntFromSharedPreferences(Context context, String prefsName, String key) {
        
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
}