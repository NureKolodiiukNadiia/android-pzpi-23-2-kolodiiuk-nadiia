package nadiia.kolodiiuk.labtask4;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteRepository implements Serializable {
    private final List<Note> allNotes = new ArrayList<>();
    private final List<Note> filteredNotes = new ArrayList<>();
    private final Context context;
    private NoteDataListener callback;

    public void setCallback(NoteDataListener callback) {
        this.callback = callback;
    }

    public NoteRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public List<Note> getFilteredNotes() {
        return new ArrayList<>(filteredNotes);
    }

    public Note createNote(String name, String description,
                           Priority priority, String time, String date,
                           byte[] image, String createdAt) {

        Note note = new Note(name, description, priority, time, date, image, createdAt);
        allNotes.add(note);
        filteredNotes.clear();
        filteredNotes.addAll(allNotes);
        notifyDataChanged();

        return note;
    }

    public Note updateNote(Note updatedNote) {

        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).getId() == updatedNote.getId()) {
                allNotes.set(i, updatedNote);
                filteredNotes.clear();
                filteredNotes.addAll(allNotes);
                notifyDataChanged();
                return updatedNote;
            }
        }

        return null;
    }

    public void deleteNote(int id) {

        allNotes.removeIf(note -> note.getId() == id);
        filteredNotes.clear();
        filteredNotes.addAll(allNotes);
        notifyDataChanged();
    }

    public void filterNotesByPriority(Priority priority) {
        filteredNotes.clear();
        if (priority == null) {
            filteredNotes.addAll(allNotes);
        } else {
            filteredNotes.addAll(allNotes.stream()
                    .filter(note -> note.getPriority() == priority)
                    .collect(Collectors.toList()));
        }
        // Call notifyDataChanged instead of direct callback
        notifyDataChanged();
    }

    public void filterNotesByText(String query) {
        if (query == null || query.isEmpty()) {
            return;
        }
        filteredNotes.clear();
        for (Note note : allNotes) {
            if (note.getName().toLowerCase().contains(query.toLowerCase())
                    || note.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        if (callback != null) {
            callback.onDataChanged(filteredNotes);
        }
    }
}