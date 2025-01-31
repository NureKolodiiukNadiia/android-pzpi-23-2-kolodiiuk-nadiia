package nadiia.kolodiiuk.labtask5;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_THEME = "theme";
    private static final String KEY_COLOR_SCHEME = "color_scheme";
    private static final String KEY_FONT_SIZE = "font_size";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setupLanguageOptions(view);
        setupThemeAndFontSizeOptions(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void setupThemeAndFontSizeOptions(View view) {
        Spinner spinnerTheme = view.findViewById(R.id.spinnerTheme);
        Spinner spinnerColorScheme = view.findViewById(R.id.spinnerColorScheme);
        Spinner spinnerFontSize = view.findViewById(R.id.spinnerFontSize);

        String currentTheme = sharedPreferences.getString(KEY_THEME, "light");
        String currentColorScheme = sharedPreferences.getString(KEY_COLOR_SCHEME, "blue");
        String currentFontSize = sharedPreferences.getString(KEY_FONT_SIZE, "medium");

        spinnerTheme.setSelection(currentTheme.equals("dark") ? 1 : 0);
        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newTheme = position == 1 ? "dark" : "light";
                if (!newTheme.equals(currentTheme)) {
                    sharedPreferences.edit().putString(KEY_THEME, newTheme).apply();
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerColorScheme.setSelection(currentColorScheme.equals("brown") ? 1 : 0);
        spinnerColorScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newColorScheme = position == 1 ? "brown" : "blue";
                if (!newColorScheme.equals(currentColorScheme)) {
                    sharedPreferences.edit().putString(KEY_COLOR_SCHEME, newColorScheme).apply();
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerFontSize.setSelection(getFontSizePosition(currentFontSize));
        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSize = getFontSizeValue(position);
                if (!newSize.equals(currentFontSize)) {
                    sharedPreferences.edit().putString(KEY_FONT_SIZE, newSize).apply();
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String getFontSizeValue(int position) {
        switch (position) {
            case 0: return "small";
            case 2: return "large";
            default: return "medium";
        }
    }

    private int getFontSizePosition(String fontSize) {
        switch (fontSize) {
            case "small": return 0;
            case "large": return 2;
            default: return 1;
        }
    }

    private void setupLanguageOptions(View view) {
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String[] languageCodes = {"en", "uk"};

        String currentLanguageCode = Locale.getDefault().getLanguage();
        int currentPosition = currentLanguageCode.equals("uk") ? 1 : 0;
        spinner.setSelection(currentPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguageCode = languageCodes[position];
                String currentLanguageCode = Locale.getDefault().getLanguage();

                if (!selectedLanguageCode.equals(currentLanguageCode)) {
                    updateLocale(selectedLanguageCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(config,
                requireActivity().getResources().getDisplayMetrics());

        new Handler(Looper.getMainLooper())
                .post(() -> requireActivity().recreate());
    }
}
