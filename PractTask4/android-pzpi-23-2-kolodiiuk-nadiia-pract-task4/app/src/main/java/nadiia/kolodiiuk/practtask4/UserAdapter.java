package nadiia.kolodiiuk.practtask4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        public TextView textViewName;
        public TextView textViewAge;

        public ViewHolder(View v) {
            
            super(v);
            textViewName = v.findViewById(R.id.textViewName);
            textViewAge = v.findViewById(R.id.textViewAge);
        }
    }

    private String[] mNames;
    private int[] mAges;

    public UserAdapter(String[] names, int[] ages) {
        
        mNames = names;
        mAges = ages;
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
        
        holder.textViewName.setText(mNames[position]);
        holder.textViewAge.setText(String.valueOf(mAges[position]));
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }
}

