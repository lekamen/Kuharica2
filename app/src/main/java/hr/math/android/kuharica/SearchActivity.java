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
    private RecyclerView searchRecepts;

    private RecyclerView.LayoutManager layoutManagerKategorija;
    private RecyclerView.LayoutManager layoutManagerRecept;

    private DBRAdapter db;
    private KategorijaAdapter kategorijaAdapter;
    private ReceptAdapter receptAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchCategory = (RecyclerView)findViewById(R.id.searchCategory);
        searchCategory.setHasFixedSize(true);

        searchRecepts = (RecyclerView)findViewById(R.id.searchRecepts);
        searchRecepts.setHasFixedSize(true);

        layoutManagerKategorija = new LinearLayoutManager(this);
        layoutManagerRecept = new LinearLayoutManager(this);
        searchCategory.setLayoutManager(layoutManagerKategorija);
        searchRecepts.setLayoutManager(layoutManagerRecept);
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

        List<Recept> lista = db.searchReceptiByFilter(pretraga);
        //koristimo skup da se ne ponavljaju
        List<Recept> listaSastojakaIUputa = db.searchSastojkeIUputeByFilter(pretraga);

        for(Recept r : listaSastojakaIUputa) {
            if(!lista.contains(r)) {
                lista.add(r);
            }
        }

        receptAdapter = new ReceptAdapter(lista, this, searchRecepts);
        searchRecepts.setAdapter(receptAdapter);
        db.close();
    }
}
