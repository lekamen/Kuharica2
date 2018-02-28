package hr.math.android.kuharica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

/**
 * Created by ivana on 2/27/18.
 */

public class RecipeInCategoryAdapter extends RecyclerView.Adapter<RecipeInCategoryAdapter.ViewHolder>{
    private List<Recept> mRecipes;
    private Context mContext;
    private RecyclerView mRecyclerView;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView recipeNameTxtV;
        public ImageView recipeImageImgV;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            recipeNameTxtV = (TextView) v.findViewById(R.id.nameRecipe);
            recipeImageImgV = (ImageView) v.findViewById(R.id.image);
        }
    }

    public void add(int position, Recept recipe){
        mRecipes.add(position, recipe);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mRecipes.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeInCategoryAdapter(List<Recept> myDataset, Context context, RecyclerView recyclerView) {
        mRecipes = myDataset;
        mContext = context;
        mRecyclerView = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeInCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_row_recipe, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        final Recept recipe = mRecipes.get(position);
        holder.recipeNameTxtV.setText(recipe.getImeRecepta());
        Picasso.with(mContext).load(recipe.getPhotoRecept()).placeholder(R.mipmap.ic_launcher).into(holder.recipeImageImgV);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecipeActivity(recipe.getId(), recipe.getImeRecepta());
            }

        });

        //listen to single view layout long click
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.alertDialogTitle1);
                builder.setMessage(R.string.alertDialogMessage1);
                builder.setPositiveButton(
                        mContext.getString(R.string.alertDialogPositive),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext,
                                        "Ovdje se treba pokrenuti activity za azuriranje receptra",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNeutralButton(
                        R.string.alertDialogNeutral,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBRAdapter db = new DBRAdapter(mContext);
                                db.open();
                                db.deleteRecept(recipe.getId());
                                db.close();

                                mRecipes.remove(position);
                                mRecyclerView.removeViewAt(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mRecipes.size());
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(
                        R.string.alertDialogNegative,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.create().show();
                return true;
            }
        });
    }

    private void goToRecipeActivity(long recipeId, String recipeName) {

        Toast.makeText(mContext, "ovdje se treba otvorit novi activity s receptom",
                        Toast.LENGTH_SHORT).show();
        /*Intent goToRecipeIntent = new Intent(mContext, RecipeActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putLong("recipeId", recipeId);
        mBundle.putString("recipeName", recipeName);

        goToRecipeIntent.putExtras(mBundle);
        mContext.startActivity(goToRecipeIntent);
        */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}
