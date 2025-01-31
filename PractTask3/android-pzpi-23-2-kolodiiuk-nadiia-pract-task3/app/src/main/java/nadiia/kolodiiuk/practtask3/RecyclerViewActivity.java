package nadiia.kolodiiuk.practtask3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] myDataset = new String[20];
        for (int i = 0; i < myDataset.length;) {
            myDataset[i] = getString(R.string.item) + ++i;
        }
        int[] images = new int[20];
        for (int i = 0; i < images.length; i++) {
            if (i % 4 == 0) {
                images[i] = R.drawable.success;
            } else if (i % 2 == 0) {
                images[i] = R.drawable.star;
            } else {
                images[i] = R.drawable.user;
            }
        }
        MyAdapter adapter = new MyAdapter(myDataset, images);
        recyclerView.setAdapter((adapter));
    }
}