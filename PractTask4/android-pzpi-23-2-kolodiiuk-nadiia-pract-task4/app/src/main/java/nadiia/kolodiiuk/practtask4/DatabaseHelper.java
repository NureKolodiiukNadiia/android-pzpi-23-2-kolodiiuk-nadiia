package nadiia.kolodiiuk.practtask4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "MyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "age INTEGER)"); 
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertUser(String name, int age) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)";
        db.execSQL(sql, new Object[]{name, age});
        db.close();
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            if (nameIndex != -1 && ageIndex != -1) {
                String name = cursor.getString(nameIndex);
                int age = cursor.getInt(ageIndex); 
                users.add(new User(name, age));
            }
        }
        cursor.close();

        return users;
    }
}