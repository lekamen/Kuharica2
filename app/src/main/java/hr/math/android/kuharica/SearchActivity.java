package hr.math.android.kuharica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchCategory;
    private RecyclerView.LayoutManager layoutManager;
    private DBRAdapter db;
    private KategorijaAdapter kategorijaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchCategory = (RecyclerView)findViewById(R.id.searchCategory);
        searchCategory.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        searchCategory.setLayoutManager(layoutManager);
    }

    public void pretraziKuharicu(View view) {
        String pretraga = ((EditText)findViewById(R.id.searchText)).getText().toString();
        pretraziKategorije(pretraga);
    }

    private void pretraziKategorije(String pretraga) {
        db = new DBRAdapter(this);
        db.open();
        kategorijaAdapter = new KategorijaAdapter(db.searchKategorijeByFilter(pretraga), this, searchCategory);
        searchCategory.setAdapter(kategorijaAdapter);
        db.close();
    }

    private void pretraziImenaRecepata(String pretraga) {

    }
}
