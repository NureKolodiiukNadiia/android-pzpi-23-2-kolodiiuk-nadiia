package nadiia.kolodiiuk.labtask5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class FilterFragment extends DialogFragment {
    private NoteRepository noteRepository;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteRepository = ((NotesApplication) requireActivity().getApplication()).getNoteRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, 
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] priorities = {
                getString(R.string.all_priority), 
                getString(R.string.low_priority), 
                getString(R.string.medium_priority), 
                getString(R.string.high_priority)
        };
        
        return new AlertDialog.Builder(requireActivity())
                .setTitle(getString(R.string.select_filter))
                .setItems(priorities, (dialog, which) -> {
                    Priority selectedPriority;
                    if (which == 0) {
                        selectedPriority = null;
                    } else {
                        selectedPriority = Priority.values()[which - 1];
                    }
                    noteRepository.filterNotesByPriority(selectedPriority);
                })
                .create();
    }
}
