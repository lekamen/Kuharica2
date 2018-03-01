package hr.math.android.kuharica;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    }

    public void setData(List<Kategorija> kategorije) {
        this.kategorije = kategorije;
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
        int vrijednost = 0;
        String path = kategorija.getPhotoKategorije();
        boolean uDrawableFolderu = true;
        if(path == null) {
            vrijednost = R.drawable.default_kategorija;
        } else {
            //vidi jel slika spremljena vani ili u drawable
            try {
                vrijednost = Integer.parseInt(path);
            } catch (Exception e){
                uDrawableFolderu = false;
            }
        }

        if(uDrawableFolderu) {
            Picasso.with(context).load(vrijednost)
                    .placeholder(R.drawable.default_kategorija).into(holder.image);
        } else {
            loadImageFromStorage(kategorija.getPhotoKategorije(), holder);
            /*Picasso.with(context).load(kategorija.getPhotoKategorije())
                    .placeholder(R.drawable.default_kategorija).into(holder.image);*/
        }

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

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "long click", Toast.LENGTH_SHORT).show();

                //izbrisi kategoriju, izbrisi sve iz tablice recepti u kategoriji

                return true;
            }
        });

    }

    private void loadImageFromStorage(String path, ViewHolder holder)
    {

        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            holder.image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return kategorije.size();
    }
}
