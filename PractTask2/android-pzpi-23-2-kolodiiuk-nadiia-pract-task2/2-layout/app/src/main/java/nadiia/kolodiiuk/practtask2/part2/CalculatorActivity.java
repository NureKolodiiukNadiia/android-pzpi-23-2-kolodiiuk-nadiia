package nadiia.kolodiiuk.practtask2.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    private String textToDisplay = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button oneBtn = findViewById(R.id.btn_1);
        Button twoBtn = findViewById(R.id.btn_2);
        Button threeBtn = findViewById(R.id.btn_3);
        TextView textView = findViewById(R.id.input_display);
        
        oneBtn.setOnClickListener((view) -> {
            textToDisplay += "1";
            textView.setText(textToDisplay);
        });
        
        twoBtn.setOnClickListener((view) -> {
            textToDisplay += "2";
            textView.setText(textToDisplay);
        });
        
        threeBtn.setOnClickListener((view) -> {
            textToDisplay += "3";
            textView.setText(textToDisplay);
        });
    }
}