package hr.math.android.kuharica;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        //String name = savedInstanceState.getString("recipeName");
        String name = "test_ime";

        collapsingToolbar.setTitle(name);

        //long ID = savedInstanceState.getLong("recipeID");
        long ID = 12345;
        DBRAdapter db = new DBRAdapter(this);
        db.open();
        Recept test_recept = new Recept();
        //test_recept.setId(ID);
        test_recept.setImeRecepta(name);
        List<String> temp_list = new ArrayList<String>();
        temp_list.add("prvi");
        temp_list.add("drugi");
        temp_list.add("treci");
        test_recept.setSastojci(temp_list);
        test_recept.setNotes("blabla blabla bla");
        test_recept.setUpute(Arrays.asList("korak 1 min","peci 3 minute", "korak 3: mijesi 2min"));
        test_recept.setPhotoRecept("");

        ID = db.insertRecept(test_recept);

        Recept current = db.getReceptZaId(ID);
        List<String> sastojci = current.getSastojci();
        List<String> upute = current.getUpute();
        String napomena = current.getNotes();

        ListView lv_sastojci = (ListView) findViewById(R.id.sastojci_list);
        ListView lv_upute = (ListView) findViewById(R.id.priprema_list);
        TextView tv_napomene = (TextView) findViewById(R.id.napomene);

        ArrayList<Sastojak> temp = new ArrayList<>();
        for (String s: sastojci) {
            Sastojak sas = new Sastojak();
            sas.setSastojak(s);
            sas.setSelected(false);
            temp.add(sas);
        }
        ArrayList<Uputa> lista_uputa = new ArrayList<>();
        for(String u: upute) {
            Uputa upu = new Uputa();
            upu.setUputa(u);
            upu.setSelected(false);
            lista_uputa.add(upu);
        }
        SastojciAdapter adapter_sastojci = new SastojciAdapter(this, temp);
        lv_sastojci.setAdapter(adapter_sastojci);
        setListViewHeightBasedOnChildren(lv_sastojci);
        UputaAdapter adapter_uputa = new UputaAdapter(this, lista_uputa);
        lv_upute.setAdapter(adapter_uputa);
        setListViewHeightBasedOnChildren(lv_upute);

        tv_napomene.setText(napomena);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
