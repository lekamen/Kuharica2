package hr.math.android.kuharica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mabel on 28-Feb-18.
 */

public class ReceptAdapter extends RecyclerView.Adapter<ReceptAdapter.ViewHolder>{
    private List<Recept> recepti;
    private Context context;
    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imeRecepta;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = (ImageView) v.findViewById(R.id.image);
            imeRecepta = (TextView) v.findViewById(R.id.name);
        }
    }

    public void add(int position, Recept recept) {
        recepti.add(position, recept);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        recepti.remove(position);
        notifyItemRemoved(position);
    }

    public ReceptAdapter(List<Recept> recepti, Context context, RecyclerView recyclerView) {
        this.recepti = recepti;
        this.context = context;
        this.recyclerView = recyclerView;
        Log.w("kategorijaadapter", "velicina " + recepti.size());
    }

    @Override
    public ReceptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.single_row, parent, false);
        ReceptAdapter.ViewHolder vh = new ReceptAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReceptAdapter.ViewHolder holder, int position) {
        final Recept recept = recepti.get(position);
        holder.imeRecepta.setText(recept.getImeRecepta());
        //ako je photoKategorije null onda se uzima defaultni
        int vrijednost;
        if(recept.getPhotoRecept() == null) {
            vrijednost = R.drawable.default_recept;
        } else {
            vrijednost = Integer.parseInt(recept.getPhotoRecept());
        }
        Picasso.with(context).load(vrijednost)
                .placeholder(R.drawable.default_recept).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ucitavanje recepta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recepti.size();
    }
}
