package nadiia.kolodiiuk.labtask3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Evaluator evaluator;
    private TextView display;
    private static final String DISPLAY_KEY = "display_key";
    private static final String EVALUATOR_KEY = "evaluator_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        evaluator = new Evaluator();
        display = findViewById(R.id.result_display);

        if (savedInstanceState != null) {
            display.setText(savedInstanceState.getString(DISPLAY_KEY));
            evaluator = (Evaluator) savedInstanceState.getSerializable(EVALUATOR_KEY);
        }

        setupNumberButtons();
        setupOperatorButtons();
        setupControlButtons();
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
        outState.putString(DISPLAY_KEY, display.getText().toString());
        outState.putSerializable(EVALUATOR_KEY, evaluator);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display.setText(savedInstanceState.getString(DISPLAY_KEY));
        evaluator = (Evaluator) savedInstanceState.getSerializable(EVALUATOR_KEY);
    }
    
    private void setupNumberButtons() {
        int[] numberIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int id : numberIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                evaluator.appendDigit(((Button) v).getText().toString());
                updateDisplay();
            });
        }
    }

    private void setupOperatorButtons() {
        int[] operatorIds = {
                R.id.btn_add, R.id.btn_subtract,
                R.id.btn_multiply, R.id.btn_divide
        };
        String[] operators = {"+", "-", "*", "/"};

        for (int i = 0; i < operatorIds.length; i++) {
            Button button = findViewById(operatorIds[i]);
            String operator = operators[i];
            button.setOnClickListener(v -> {
                display.setText(evaluator.applyOperator(operator));
            });
        }
    }

    private void setupControlButtons() {
        findViewById(R.id.btn_dot).setOnClickListener(v -> {
            evaluator.appendDecimalPoint();
            updateDisplay();
        });
        findViewById(R.id.btn_equals).setOnClickListener(v -> {
            display.setText(evaluator.calculate());
        });
        findViewById(R.id.btn_clear).setOnClickListener(v -> {
            evaluator.clear();
            updateDisplay();
        });
    }

    private void updateDisplay() {
        display.setText(evaluator.getCurrentDisplay());
    }
}