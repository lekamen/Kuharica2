package hr.math.android.kuharica;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by ivana on 2/28/18.
 */

//SelectableReceptViewHolder
public class SelectableReceptVH extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;

    CheckedTextView textView;
    ImageView imageView;
    SelectableRecept mRecept;
    OnItemSelectedListener itemSelectedListener;
    LinearLayout parent;

    public SelectableReceptVH(View view, OnItemSelectedListener listener){
        super(view);

        parent = view.findViewById(R.id.selectRecept);
        itemSelectedListener = listener;
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (CheckedTextView) view.findViewById(R.id.nameRecipe);

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if()
                Toast.makeText(view.getContext(), "Otvori recept", Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view){
                if(mRecept.isSelected()){
                    setChecked(false);
                    textView.setCheckMarkDrawable(null);
                } else{
                    setChecked(true);

                    //napravi mu ikonicu
                    TypedValue value = new TypedValue();
                    view.getContext().getTheme()
                            .resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
                    int checkMarkDrawableResId = value.resourceId;

                    textView.setCheckMarkDrawable(checkMarkDrawableResId);
                }
                itemSelectedListener.onItemSelected(mRecept);
                return true;
            }
        });

    }

    public void setChecked(boolean value){
        if(value){
            parent.setBackgroundColor(Color.LTGRAY);
        } else{
            parent.setBackground(null);
        }

        mRecept.setSelected(value);
        textView.setChecked(value);
    }

    public interface OnItemSelectedListener{
        void onItemSelected(SelectableRecept recept);
    }
}
