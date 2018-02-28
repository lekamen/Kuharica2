package hr.math.android.kuharica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchCategory;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView searchRecepts;
    private DBRAdapter db;
    private KategorijaAdapter kategorijaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchCategory = (RecyclerView)findViewById(R.id.searchCategory);
        searchCategory.setHasFixedSize(true);

        searchRecepts = (RecyclerView)findViewById(R.id.searchRecepts);
        searchRecepts.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        searchCategory.setLayoutManager(layoutManager);
        searchRecepts.setLayoutManager(layoutManager);
    }

    public void pretraziKuharicu(View view) {
        String pretraga = ((EditText)findViewById(R.id.searchText)).getText().toString();
        pretraziKategorije(pretraga);
        pretraziRecepte(pretraga);
    }

    private void pretraziKategorije(String pretraga) {
        db = new DBRAdapter(this);
        db.open();
        kategorijaAdapter = new KategorijaAdapter(db.searchKategorijeByFilter(pretraga), this, searchCategory);
        searchCategory.setAdapter(kategorijaAdapter);
        db.close();
    }

    private void pretraziRecepte(String pretraga) {
        db = new DBRAdapter(this);
        db.open();

        //koristimo skup da se ne ponavljaju
        Set<Recept> skup = new HashSet<>();
        skup.addAll(db.searchReceptiByFilter(pretraga));
        skup.addAll(db.searchSastojkeIUputeByFilter(pretraga));
        db.close();

        List<Recept> lista = new ArrayList<>();
        lista.addAll(skup);

        //adapter za recepte treba
        //searchRecepts.setAdapter(receptAdapter);

    }
}
