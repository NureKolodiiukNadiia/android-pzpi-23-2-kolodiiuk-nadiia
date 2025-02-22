package nadiia.kolodiiuk.practtask2.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }

    public void onConstraintLayoutClick(View view) {

        startActivity(new Intent(MainActivity.this, ConstraintLayoutActivity.class));
    }

    public void onGridLayoutClick(View view) {

        startActivity(new Intent(MainActivity.this, GridLayoutActivity.class));
    }

    public void onRelativeLayoutClick(View view) {

        startActivity(new Intent(MainActivity.this, RelativeLayoutActivity.class));
    }

    public void onFrameLayoutClick(View view) {

        startActivity(new Intent(MainActivity.this, FrameLayoutActivity.class));
    }
    
    public void onCalculatorLayoutClick(View view) {

        startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
    }
}