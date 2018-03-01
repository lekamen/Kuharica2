package hr.math.android.kuharica;

<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> 45aae13605f01c3385ef22934e059e15ffdbb17c
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    private boolean pretragaKategorije = false;
    private long idKategorije = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent intent = getIntent();
        if(intent.getExtras() == null){
            searchCategory = (RecyclerView)findViewById(R.id.searchCategory);
            searchCategory.setHasFixedSize(true);

            layoutManagerKategorija = new LinearLayoutManager(this);
            searchCategory.setLayoutManager(layoutManagerKategorija);
        }

        else{
            pretragaKategorije = true;
            EditText searchText = findViewById(R.id.searchText);
            searchText.setHint("Pretražite kategoriju...");

            idKategorije = intent.getExtras().getLong("categoryId");

            db = new DBRAdapter(this);
            db.open();
            setTitle("Pretraga kategorije " + db.getKategorija(idKategorije).getImeKategorije());
            db.close();

            com.github.clans.fab.Label labelaPretrageKategorije = findViewById(R.id.labelPretragaKategorije);
            labelaPretrageKategorije.setVisibility(View.GONE);
        }

        searchRecepts = (RecyclerView)findViewById(R.id.searchRecepts);
        searchRecepts.setHasFixedSize(true);

        layoutManagerRecept = new LinearLayoutManager(this);
        searchRecepts.setLayoutManager(layoutManagerRecept);
    }

    public void pretraziKuharicu(View view) {
        String pretraga = ((EditText)findViewById(R.id.searchText)).getText().toString();

        if(pretragaKategorije == false)
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

<<<<<<< HEAD
    private void pretraziImenaRecepata(String pretraga) {

=======
    private void pretraziRecepte(String pretraga) {
        db = new DBRAdapter(this);
        db.open();

        List<Recept> receptiIzKategorije = new ArrayList<>();

        if(idKategorije > 0)
            receptiIzKategorije = db.getAllReceptiFromKategorija(idKategorije);

        List<Recept> lista = db.searchReceptiByFilter(pretraga);

        //koristimo skup da se ne ponavljaju
        List<Recept> listaSastojakaIUputa = db.searchSastojkeIUputeByFilter(pretraga);

        for(Recept r : listaSastojakaIUputa) {
            if(!lista.contains(r)) {
                lista.add(r);
            }
        }

        //nadjemo presjek te dvije liste
        if(receptiIzKategorije != null)
            lista.retainAll(receptiIzKategorije);

        db.close();

        receptAdapter = new ReceptAdapter(lista, this, searchRecepts);
        searchRecepts.setAdapter(receptAdapter);
>>>>>>> 45aae13605f01c3385ef22934e059e15ffdbb17c
    }
}
