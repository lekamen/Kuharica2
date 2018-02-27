package hr.math.android.kuharica;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabel on 26-Feb-18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer default_category = R.drawable.default_kategorija;

    public ImageAdapter(Context c) {
        mContext = c;
        setData();
        Log.w("image", "tu " + data.length);

    }

    private Integer[] data;

    private static Integer getImage(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    private void setData() {
        DBAdapter db = new DBAdapter(mContext);
        List<Integer> list = new ArrayList<Integer>();

        db.open();
        Cursor cursor = db.getAllKategorije();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                String path = cursor.getString(1);
                if(path != null) {
                    list.add(getImage(mContext, path));
                    Log.w("image", getImage(mContext, path) + " " + R.drawable.cake);
                }
                else {
                    list.add(default_category);
                    Log.w("image", default_category + " " + R.drawable.default_kategorija);
                }
            } while(cursor.moveToNext());
        }
        db.close();
        this.data = new Integer[list.size()];
        list.toArray(this.data);

    }


    @Override
    public int getCount() {
        if(data == null)
            return 0;
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) view;
        }

        imageView.setImageResource(data[i]);
        return imageView;
    }
}
