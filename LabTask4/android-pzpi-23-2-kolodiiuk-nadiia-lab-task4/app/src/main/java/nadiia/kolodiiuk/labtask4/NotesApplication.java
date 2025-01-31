package nadiia.kolodiiuk.labtask4;

import android.app.Application;

public class NotesApplication extends Application {
    private NoteRepository noteRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new NoteRepository(this);
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
