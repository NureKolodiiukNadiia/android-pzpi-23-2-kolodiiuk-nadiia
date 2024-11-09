package nadiia.kolodiiuk.practtask2.part3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private static final String EDIT_TEXT_KEY = "editTextKey"; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("Practical task 2", "onCreate called");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        editText = findViewById(R.id.editTextText);
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("Practical task 2", "onStart called");
    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d("Practical task 2", "onResume called");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.d("Practical task 2", "onPause called");
    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.d("Practical task 2", "onStop called");
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        Log.d("Practical task 2", "onRestart called");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d("Practical task 2", "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(EDIT_TEXT_KEY, editText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            String savedText = bundle.getString(EDIT_TEXT_KEY);
            if (savedText != null) {
                editText.setText(savedText);
            }
        }
    }
    
    public void onToSecondActivityClick(View v) {
        
        startActivity(new Intent(this, SecondActivity.class));
    }
}