package nadiia.kolodiiuk.labtask5;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteActionListener, NoteDataListener {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyTheme();  
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

        noteRepository = ((NotesApplication) getApplication()).getNoteRepository();
        noteRepository.setNoteDataListener(this);
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
        Configuration configuration = new Configuration(newBase.getResources().getConfiguration());
        Context context = newBase.createConfigurationContext(configuration);

        super.attachBaseContext(context);
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

    private void applyTheme() {
        SharedPreferences prefs = getSharedPreferences("app_settings", MODE_PRIVATE);
        String theme = prefs.getString("theme", "light");
        String colorScheme = prefs.getString("color_scheme", "blue");
        String fontSize = prefs.getString("font_size", "medium");

        int baseThemeResId;
        if ("dark".equals(theme)) {
            baseThemeResId = "brown".equals(colorScheme) 
                ? R.style.DarkBrownTheme 
                : R.style.DarkBlueTheme;
        } else {
            baseThemeResId = "brown".equals(colorScheme) 
                ? R.style.LightBrownTheme 
                : R.style.LightBlueTheme;
        }

        setTheme(baseThemeResId);

        int fontSizeStyleResId;
        switch (fontSize) {
            case "small":
                fontSizeStyleResId = R.style.SmallTextSize;
                break;
            case "large":
                fontSizeStyleResId = R.style.LargeTextSize;
                break;
            default:
                fontSizeStyleResId = R.style.MediumTextSize;
                break;
        }
        getTheme().applyStyle(fontSizeStyleResId, true);
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteRepository.filterNotesByText(newText);
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                noteRepository.filterNotesByText(""); 
                return true;
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
        if (adapter != null) {
            runOnUiThread(() -> {
                adapter.setNotesData(notes);
                adapter.notifyDataSetChanged();
            });
        }
    }
}
