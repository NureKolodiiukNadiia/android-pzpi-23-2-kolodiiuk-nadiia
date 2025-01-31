package nadiia.kolodiiuk.labtask4;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteActionListener, NoteDataListener {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        noteRepository = ((NotesApplication) getApplication()).getNoteRepository();
        noteRepository.setCallback(this);
        setupRecyclerViewWithNotes(savedInstanceState);
        adapter.setNotesData(noteRepository.getFilteredNotes());
        adapter.setOnNoteActionListener(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onStop() {
        super.onStop();
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(updateBaseContextLocale(newBase));
    }

    private Context updateBaseContextLocale(Context context) {
        Locale locale = Locale.getDefault();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private void setupRecyclerViewWithNotes(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void applyLocale() {
        Locale locale = Locale.getDefault();
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteRepository.filterNotesByText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteRepository.filterNotesByText(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            loadFragment(new SettingsFragment());
            return true;
        } else if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_add_note) {
            openNoteEditingFragment(null);
            return true;
        } else if (id == R.id.action_filter) {
            new FilterFragment().show(getSupportFragmentManager(), "filter");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openNoteEditingFragment(Note note) {
        NoteEditingFragment fragment = new NoteEditingFragment();

        if (note != null) {
            Bundle args = new Bundle();
            args.putSerializable("note", note);
            fragment.setArguments(args);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        recyclerView.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onEditNoteClicked(Note note) {
        openNoteEditingFragment(note);
    }

    @Override
    public void onDeleteNoteClicked(Note note) {
        noteRepository.deleteNote(note.getId());
    }

    @Override
    public void onDataChanged(List<Note> notes) {
        adapter.setNotesData(notes);
    }
}