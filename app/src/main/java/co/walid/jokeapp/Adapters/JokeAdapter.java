package co.walid.jokeapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

 // Replace with your actual SecondActivity
import co.walid.jokeapp.SecondLine;
import co.walid.jokeapp.model.Joke;
import co.walid.jokeapp.R;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {
    private List<Joke> jokes;
    private Context context;

    public JokeAdapter(Context context, List<Joke> jokes) {
        this.context = context;
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jokes_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Joke currentJoke = jokes.get(position);
        holder.firstLine.setText(currentJoke.getFirstLine());

        holder.itemView.setOnClickListener(view -> {
            String secondLine = currentJoke.getSecondLine();
            if (secondLine != null && !secondLine.isEmpty()) {
                // Launch SecondActivity and pass secondLine content
                Intent intent = new Intent(context, SecondLine.class);
                intent.putExtra("SECOND_LINE", secondLine);
                context.startActivity(intent);
            } else {

                Toast.makeText(context, "It's a single Joke ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstLine = itemView.findViewById(R.id.firstLine);
        }
    }
}
