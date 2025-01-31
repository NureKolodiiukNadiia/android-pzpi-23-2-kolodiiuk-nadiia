package nadiia.kolodiiuk.labtask5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

public class NoteEditingFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private byte[] selectedImage;

    private EditText editTextName;
    private EditText editTextDescription;
    private Spinner spinnerPriority;
    private Button buttonSave;
    private TextView selectedDateTime;
    private String selectedDate = "";
    private String selectedTime = "";

    private Note note;
    private NoteRepository noteRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_editing, container, false);

        setUpUiElements(view);
        setUpRepo();

        buttonSave.setOnClickListener(v -> saveNote());

        return view;
    }

    private void setUpRepo() {

        noteRepository = ((NotesApplication) requireActivity().getApplication()).getNoteRepository();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        if (getArguments() != null && getArguments().containsKey("note")) {

            note = (Note) getArguments().getSerializable("note");
            populateFields(note);
        }
    }

    private void setUpUiElements(View view) {

        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);
        buttonSave = view.findViewById(R.id.buttonSave);
        imageView = view.findViewById(R.id.noteImageView);
        Button selectImageButton = view.findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(v -> selectImage());

        selectedDateTime = view.findViewById(R.id.selectedDateTime);
        Button selectDateButton = view.findViewById(R.id.selectDateButton);
        Button selectTimeButton = view.findViewById(R.id.selectTimeButton);

        selectDateButton.setOnClickListener(v -> showDatePicker());
        selectTimeButton.setOnClickListener(v -> showTimePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate = String.format(Locale.getDefault(), "%02d.%02d.%d", dayOfMonth, month + 1, year);
                    updateSelectedDateTime();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    updateSelectedDateTime();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateSelectedDateTime() {
        String dateTime = selectedDate + " " + selectedTime;
        selectedDateTime.setText(dateTime);
    }

    private void populateFields(Note note) {

        editTextName.setText(note.getName());
        editTextDescription.setText(note.getDescription());
        spinnerPriority.setSelection(note.getPriority().ordinal());

        if (note.getImage() != null && note.getImage().length > 0) {
            selectedImage = note.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(selectedImage, 0, selectedImage.length);
            imageView.setImageBitmap(bitmap);
        }

        if (note.getDate() != null && !note.getDate().isEmpty()) {
            selectedDate = note.getDate();
        }
        if (note.getTime() != null && !note.getTime().isEmpty()) {
            selectedTime = note.getTime();
        }
        updateSelectedDateTime();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                selectedImage = byteBuffer.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(selectedImage, 0, selectedImage.length);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveNote() {

        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        int selectedPosition = spinnerPriority.getSelectedItemPosition();
        Priority priority;
        if (selectedPosition >= 0 && selectedPosition < Priority.values().length) {

            priority = Priority.values()[selectedPosition];
        } else {

            priority = Priority.MEDIUM;
        }

        if (note == null) {

            noteRepository.createNote(name, description, priority,
                    selectedTime, selectedDate, selectedImage, selectedDate + " " + selectedTime);
        } else {

            note.setName(name);
            note.setDescription(description);
            note.setPriority(priority);
            note.setImage(selectedImage);
            note.setTime(selectedTime);
            note.setDate(selectedDate);
            note.setCreatedFor(selectedDate + " " + selectedTime);
            noteRepository.updateNote(note);
        }

        getActivity().getSupportFragmentManager().popBackStack();
    }
}
