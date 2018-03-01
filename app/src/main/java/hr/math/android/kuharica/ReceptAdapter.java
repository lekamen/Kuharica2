package hr.math.android.kuharica;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabel on 28-Feb-18.
 */

public class ReceptAdapter extends RecyclerView.Adapter<ReceptAdapter.ViewHolder>{
    private List<Recept> recepti;
    private List<SelectableRecept> selRecepti;

    private Context context;
    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView imeRecepta;

        public View layout;

        public ViewHolder(View v){
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

        selRecepti = new ArrayList<>();
        for(Recept r : recepti){
            this.selRecepti.add(new SelectableRecept(r, false));
        }

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
        final SelectableRecept selRecept = selRecepti.get(position);

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

        if(getSelectedItemCount() > 0){
            holder.layout.findViewById(R.id.kartica).setBackgroundColor(Color.MAGENTA);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ako smo odabrali neke recepte
                //s kratkim clickom ih opet biramo
                if(getSelectedItemCount() > 0){
                    if(!selRecept.isSelected()){
                        selRecept.setSelected(true);
                        view.findViewById(R.id.kartica).setBackgroundColor(Color.MAGENTA);
                    }
                    else{
                        selRecept.setSelected(false);
                        view.findViewById(R.id.kartica).setBackgroundColor(Color.WHITE);
                    }
                }
                //pritisnuli smo samo jedan recept
                else{
                    Toast.makeText(context, "ucitavanje recepta", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                if(!selRecept.isSelected()){
                    selRecept.setSelected(true);
                    view.findViewById(R.id.kartica).setBackgroundColor(Color.MAGENTA);
                }
                else{
                    selRecept.setSelected(false);
                    view.findViewById(R.id.kartica).setBackgroundColor(Color.WHITE);
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recepti.size();
    }

    public void toggleSelection(int position){
        if(selRecepti.get(position).isSelected())
            selRecepti.get(position).setSelected(false);
        else
            selRecepti.get(position).setSelected(true);

        notifyItemChanged(position);
    }

    public void clearSelection(){
        for(SelectableRecept r : selRecepti){
            if(r.isSelected()) {
                r.setSelected(false);
                notifyItemChanged(selRecepti.indexOf(r));
            }
        }
    }

    public List<Recept> getSelectedItems(){
        List<Recept> oznaceni = new ArrayList<>();
        for(SelectableRecept r : selRecepti){
            if(r.isSelected())
                oznaceni.add(r);
        }

        return oznaceni;
    }

    public int getSelectedItemCount(){
        return getSelectedItems().size();
    }

}
