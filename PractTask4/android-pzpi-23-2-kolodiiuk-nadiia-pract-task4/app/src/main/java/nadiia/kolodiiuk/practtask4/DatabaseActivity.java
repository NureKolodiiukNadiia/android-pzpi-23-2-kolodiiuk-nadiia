package nadiia.kolodiiuk.practtask4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_age;
    Button btn_add_user;
    Button btn_show_users;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.editTextNameDatabase);
        et_age = findViewById(R.id.editTextNumber);
        btn_add_user = findViewById(R.id.addUser);
        btn_show_users = findViewById(R.id.showAllUsers);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        initializeRecyclerView();

        btn_add_user.setOnClickListener(v -> {
            String name = et_name.getText().toString();
            int age = Integer.parseInt(et_age.getText().toString());
            databaseHelper.insertUser(name, age);
        });

        btn_show_users.setOnClickListener(v -> {
            initializeRecyclerView();
        });
    }

    public void initializeRecyclerView() {

        List<User> users = databaseHelper.getUsers();
        List<String> names = new ArrayList<String>();
        List<Integer> ages = new ArrayList<Integer>();
        for (User user : users) {

            names.add(user.getName());
            ages.add(user.getAge());
        }

        String[] namesArray = names.toArray(new String[0]);
        int[] agesArray = new int[ages.size()];
        for (int i = 0; i < agesArray.length; i++) {
            agesArray[i] = ages.get(i);
        }

        var adapter = new UserAdapter(namesArray, agesArray);
        recyclerView.setAdapter((adapter));
    }
}