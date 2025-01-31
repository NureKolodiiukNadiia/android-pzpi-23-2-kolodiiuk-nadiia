package nadiia.kolodiiuk.labtask5;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements Serializable {
    private final DBHelper dbHelper;
    private NoteDataListener noteDataListener;

    public NoteRepository(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void setNoteDataListener(NoteDataListener noteDataListener) {
        this.noteDataListener = noteDataListener;
    }

    public List<Note> getFilteredNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = dbHelper.getAllNotes();
        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public Note createNote(String name, String description, Priority priority, 
                           String time, String date, byte[] image, String createdAt) {
        long id = dbHelper.insertNote(name, description, priority.ordinal(), date, time, createdAt, image);
        Note note = new Note(name, description, priority, time, date, image, createdAt);
        note.setId((int) id);
        notifyDataChanged();
        return note;
    }

    public Note updateNote(Note updatedNote) {
        dbHelper.updateNote(updatedNote.getId(), updatedNote.getName(), updatedNote.getDescription(), 
                updatedNote.getPriority().ordinal(), updatedNote.getDate(), updatedNote.getTime(), updatedNote.getCreatedAt(), updatedNote.getImage());
        notifyDataChanged();
        return updatedNote;
    }

    public void deleteNote(int id) {
        dbHelper.deleteNote(id);
        notifyDataChanged();
    }

    public void filterNotesByPriority(Priority priority) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor;
        
        if (priority == null) {
            cursor = dbHelper.getAllNotes();
        } else {
            cursor = dbHelper.filterNotesByPriority(priority.ordinal());
        }
        
        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        if (noteDataListener != null) {
            noteDataListener.onDataChanged(notes);
        }
    }

    public void filterNotesByText(String query) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor;
        
        if (query == null || query.isEmpty()) {
            cursor = dbHelper.getAllNotes();
        } else {
            cursor = dbHelper.filterNotesByText(query);
        }
        
        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        if (noteDataListener != null) {
            noteDataListener.onDataChanged(notes);
        }
    }

    private Note cursorToNote(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
        byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
        Note note = new Note(name, description, Priority.values()[priority], time, date, image, createdAt);
        note.setId(id);
        return note;
    }

    private void notifyDataChanged() {
        if (noteDataListener != null) {
            noteDataListener.onDataChanged(getFilteredNotes());
        }
    }
}
