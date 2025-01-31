package nadiia.kolodiiuk.labtask5;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT," +
                " priority INTEGER, " +
                "date TEXT, time TEXT, " +
                "created_at TEXT, " +
                "image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public long insertNote(String name, String description, int priority,
                           String date, String time, String createdAt, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("priority", priority);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("created_at", createdAt);
        contentValues.put("image", image);
        return db.insert("notes", null, contentValues);
    }

    public void updateNote(int id, String name, String description, int priority, 
                           String date, String time, String createdAt, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("priority", priority);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("created_at", createdAt);
        contentValues.put("image", image);
        db.update("notes", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("notes", null, null, null, null, null, null);
    }

    public Cursor filterNotesByPriority(int priority) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("notes", null, "priority = ?", new String[]{String.valueOf(priority)}, null, null, null);
    }

    public Cursor filterNotesByText(String query) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "name LIKE ? OR description LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%"};
        return db.query("notes", null, selection, selectionArgs, null, null, null);
    }
}
