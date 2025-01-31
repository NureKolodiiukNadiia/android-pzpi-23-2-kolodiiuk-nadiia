package nadiia.kolodiiuk.labtask5;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    
    private static final int[] PRIORITY_FLAGS = {

            R.drawable.blue_flag,
            R.drawable.yellow_flag,
            R.drawable.red_flag
    };
    private final List<Note> notesData = new ArrayList<>(); 
    private OnNoteActionListener listener;

    public void setNotesData(List<Note> newNotesData) {
        notesData.clear();
        if (newNotesData != null) {
            notesData.addAll(newNotesData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Note note = notesData.get(position);
        holder.textView.setText(note.getName());
        holder.dateView.setText(note.getCreatedAt());
        int flagResourceId = PRIORITY_FLAGS[note.getPriority().ordinal()];
        holder.priorityFlag.setImageResource(flagResourceId);

        if (note.getImage() != null && note.getImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
            holder.galleryPicture.setVisibility(View.VISIBLE);
            holder.galleryPicture.setImageBitmap(bitmap);
        } else {
            holder.galleryPicture.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(v -> {

            v.showContextMenu();
            return true;
        });

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {

            MenuItem edit = menu.add(Menu.NONE, 1, 1, R.string.edit);
            MenuItem delete = menu.add(Menu.NONE, 2, 2, R.string.delete);

            edit.setOnMenuItemClickListener(item -> {

                listener.onEditNoteClicked(notesData.get(holder.getAdapterPosition()));
                return true;
            });

            delete.setOnMenuItemClickListener(item -> {

                listener.onDeleteNoteClicked(notesData.get(holder.getAdapterPosition()));
                return true;
            });
        });
    }

    @Override
    public int getItemCount() {
        
        return notesData.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView dateView;
        public ImageView priorityFlag;
        public ImageView galleryPicture;

        public ViewHolder(View v) {

            super(v);
            textView = v.findViewById(R.id.noteNameText);
            dateView = v.findViewById(R.id.createdDateText);
            priorityFlag = v.findViewById(R.id.imageViewPriority);
            galleryPicture = v.findViewById(R.id.galleryPictureImageView);
        }
    }

    public interface OnNoteActionListener {
        
        void onEditNoteClicked(Note note);
        void onDeleteNoteClicked(Note note);
    }
    
    public void setOnNoteActionListener(OnNoteActionListener listener) {
        
        this.listener = listener;
    }
}
