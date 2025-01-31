package nadiia.kolodiiuk.practtask4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn_to_database;
    Button btn_write_to_file;
    Button btn_read_from_file;
    Button btn_write_to_shared_preferences;
    Button btn_read_from_shared_preferences;
    EditText et_text_to_file;
    EditText et_name;
    EditText et_age;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_to_database = findViewById(R.id.btn_to_database);
        btn_write_to_file = findViewById(R.id.btn_write_to_file);
        btn_read_from_file = findViewById(R.id.btn_read_from_file);
        btn_write_to_shared_preferences = findViewById(R.id.btn_write_to_shared_preferences);
        btn_read_from_shared_preferences = findViewById(R.id.btn_read_from_shared_preferences);
        et_text_to_file = findViewById(R.id.et_text_to_file);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);

        initializeSharedPreferences();
        
        btn_to_database.setOnClickListener(v -> {
            
            startActivity(new Intent(this, DatabaseActivity.class));
        });

        btn_write_to_file.setOnClickListener(v -> {
            
            String text = et_text_to_file.getText().toString();
            FileHelper.writeToFile(this, "file.txt", text);
        });

        btn_read_from_file.setOnClickListener(v -> {
            
            String text = FileHelper.readFromFile(this, "file.txt");
            et_text_to_file.setText(text);
        });
 
        btn_write_to_shared_preferences.setOnClickListener(v -> {
            
            String name = et_name.getText().toString();
            int age = Integer.parseInt(et_age.getText().toString());
            SharedPreferencesHelper.writeToSharedPreferences(this, "MyPrefs", "name", name);
            SharedPreferencesHelper.writeToSharedPreferences(this, "MyPrefs", "age", age);
        });

        btn_read_from_shared_preferences.setOnClickListener(v -> {

            initializeSharedPreferences();
        });
    }

    public void initializeSharedPreferences() {
        String name = SharedPreferencesHelper.readStringFromSharedPreferences(this, "MyPrefs", "name");
        int age = SharedPreferencesHelper.readIntFromSharedPreferences(this, "MyPrefs", "age");
        et_name.setText(name);
        et_age.setText(String.valueOf(age));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}