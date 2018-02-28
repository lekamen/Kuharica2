package hr.math.android.kuharica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by mabel on 27-Feb-18.
 */

public class KategorijaAdapter extends RecyclerView.Adapter<KategorijaAdapter.ViewHolder> {
    private List<Kategorija> kategorije;
    private Context context;
    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imeKategorije;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = (ImageView) v.findViewById(R.id.image);
            imeKategorije = (TextView) v.findViewById(R.id.name);
        }
    }

    public void add(int position, Kategorija kategorija) {
        kategorije.add(position, kategorija);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        kategorije.remove(position);
        notifyItemRemoved(position);
    }

    public KategorijaAdapter(List<Kategorija> kategorije, Context context, RecyclerView recyclerView) {
        this.kategorije = kategorije;
        this.context = context;
        this.recyclerView = recyclerView;
        Log.w("kategorijaadapter", "velicina " + kategorije.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.single_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Kategorija kategorija = kategorije.get(position);
        holder.imeKategorije.setText(kategorija.getImeKategorije());
        //ako je photoKategorije null onda se uzima defaultni
        int vrijednost;
        if(kategorija.getPhotoKategorije() == null) {
            vrijednost = R.drawable.default_kategorija;
        } else {
            vrijednost = Integer.parseInt(kategorija.getPhotoKategorije());
        }
        Picasso.with(context).load(vrijednost)
                .placeholder(R.drawable.default_kategorija).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "ucitavanje kategorije", Toast.LENGTH_SHORT).show();
                Intent goToCategoryIntent = new Intent(context, CategoryActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putLong("categoryId", kategorija.getId());

                goToCategoryIntent.putExtras(mBundle);
                context.startActivity(goToCategoryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategorije.size();
    }
}
