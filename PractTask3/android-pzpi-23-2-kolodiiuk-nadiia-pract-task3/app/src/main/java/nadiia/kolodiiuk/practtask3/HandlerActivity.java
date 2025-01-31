package nadiia.kolodiiuk.practtask3;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HandlerActivity extends AppCompatActivity {
    private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_handler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        simpleHandler();
        updateUiFromBackgroundThread();
        sendMessage();
        handlerThreadUsage();
    }

    private void simpleHandler() {
        Button startHandlerButton = findViewById(R.id.startHandlerButton);
        TextView handlerMessageTextView = findViewById(R.id.handlerMessageTextView);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg){
                handlerMessageTextView.setText(getString(R.string.message_received) + msg.what);
            }
        };
        startHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.handlerMessageTextView);
                        textView.setText(R.string.handler_executed_after_delay);
                    }
                }, 2000);
            }
        });
    }

    private void updateUiFromBackgroundThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.handlerMessageTextView);
                        textView.setText(R.string.updated_from_background_thread);
                    }
                });
            }
        }).start();
    }

    private void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void handlerThreadUsage() {
        HandlerThread handlerThread = new HandlerThread("BackgroundThread");
        handlerThread.start();
        Handler backgroundHandler = new Handler(handlerThread.getLooper());

        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}