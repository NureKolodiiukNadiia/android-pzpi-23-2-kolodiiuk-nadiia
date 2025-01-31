package nadiia.kolodiiuk.labtask4;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setupLanguageOptions(view);

        return view;
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
        requireActivity().getResources()
                .updateConfiguration(config,
                    requireActivity().getResources().
                            getDisplayMetrics());

        new Handler(Looper.getMainLooper())
                .post(() -> requireActivity().recreate());
    }
}