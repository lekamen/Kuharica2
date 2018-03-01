package hr.math.android.kuharica;

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivana on 3/1/18.
 */

public class SelectableReceptAdapter extends RecyclerView.Adapter implements SelectableReceptVH.OnItemSelectedListener{
    private final List<SelectableRecept> mRecepti;
    private boolean isMultiSelectionEnabled = true;
    SelectableReceptVH.OnItemSelectedListener listener;


    private static final int TYPE_INACTIVE = 0;
    private static final int TYPE_ACTIVE = 1;

    public SelectableReceptAdapter(SelectableReceptVH.OnItemSelectedListener listener,
                                   List<Recept> recepti, boolean isMultiSelectionEnabled){

        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        mRecepti = new ArrayList<>();
        for(Recept r : recepti){
            mRecepti.add(new SelectableRecept(r, false));
        }
    }

    @Override
    public SelectableReceptVH onCreateViewHolder(ViewGroup parent, int viewType){
        final int layout = viewType == TYPE_INACTIVE ? R.layout.single_row_recipe : R.layout.single_row_recipe_active;
        View receptView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_recipe, parent, false);

        return new SelectableReceptVH(receptView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        SelectableReceptVH holder = (SelectableReceptVH)viewHolder;
        SelectableRecept selectableRecept = mRecepti.get(position);

        String imeRecepta = selectableRecept.getImeRecepta();

        holder.textView.setText(imeRecepta);

        int vrijednost;
        if(selectableRecept.getPhotoRecept() == null) {
            vrijednost = R.drawable.default_recept;
        } else {
            vrijednost = Integer.parseInt(selectableRecept.getPhotoRecept());
        }
        Picasso.with(holder.imageView.getContext()).load(vrijednost)
                .placeholder(R.drawable.default_recept).into(holder.imageView);

        /*
        if(isMultiSelectionEnabled){
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme()
                    .resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else{
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme()
                    .resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }*/

        holder.mRecept = selectableRecept;
        holder.setChecked(holder.mRecept.isSelected());
    }

    @Override
    public int getItemCount(){
        return mRecepti.size();
    }

    public List<Recept> getSelectedRecepti(){
        List<Recept> selectedRecepts = new ArrayList<>();
        for(SelectableRecept sr : mRecepti){
            if(sr.isSelected())
                selectedRecepts.add(sr);
        }

        return selectedRecepts;
    }

    @Override
    public int getItemViewType(int position){
        final SelectableRecept recept = mRecepti.get(position);

        return recept.isSelected() ? TYPE_ACTIVE : TYPE_INACTIVE;
    }

    @Override
    public void onItemSelected(SelectableRecept recept){
        if (!isMultiSelectionEnabled) {

            for (SelectableRecept selectableItem : mRecepti) {
                if (!selectableItem.equals(recept)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(recept)
                        && recept.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(recept);
    }
}
