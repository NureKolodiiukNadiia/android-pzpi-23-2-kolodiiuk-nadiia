package nadiia.kolodiiuk.labtask1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lab 1", "onCreate called");
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("Lab 1", "onStart called");
    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d("Lab 1", "onResume called");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.d("Lab 1", "onPause called");
    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.d("Lab 1", "onStop called");
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        Log.d("Lab 1", "onRestart called");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d("Lab 1", "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {

        super.onSaveInstanceState(bundle);
        Log.d("Lab 1", "onSaveInstanceState called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {

        super.onRestoreInstanceState(bundle);
        Log.d("Lab 1", "onRestoreInstanceState called");

    }
}