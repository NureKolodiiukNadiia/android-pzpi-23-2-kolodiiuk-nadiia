package nadiia.kolodiiuk.labtask5;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class NotesApplication extends Application {

    private NoteRepository noteRepository;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_THEME = "selected_theme";
    private static final String KEY_FONT_SIZE = "selected_font_size";

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new NoteRepository(this);
        applyUserSettings();
    }

    private void applyUserSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedTheme = sharedPreferences.getString(KEY_THEME, "Light");
        String savedFontSize = sharedPreferences.getString(KEY_FONT_SIZE, "Medium");

        if (savedTheme.equals("Dark")) {
            setTheme(R.style.DarkBrownTheme);
        } else {
            setTheme(R.style.LightBrownTheme);
        }

        float scale;
        switch (savedFontSize) {
            case "Small":
                scale = 0.85f;
                break;
            case "Large":
                scale = 1.15f;
                break;
            default:
                scale = 1.0f;
        }

        Configuration configuration = new Configuration();
        configuration.fontScale = scale;
        Locale locale = Locale.getDefault();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
