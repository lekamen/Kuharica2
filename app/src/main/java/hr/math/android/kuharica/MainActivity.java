package hr.math.android.kuharica;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DBRAdapter db;
    private KategorijaAdapter kategorijaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initializeDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeDatabase() {
         db = new DBRAdapter(this);
         db.open();
         if(db.getAllKategorije().size() == 0) {

             Kategorija kategorija = new Kategorija("Slatko", String.valueOf(R.drawable.cake));
             Recept r = new Recept();
             r.setImeRecepta("palačinke");
             r.setPhotoRecept(String.valueOf(R.drawable.pancakes));
             r.setNotes(null);
             r.setSastojci(Arrays.asList("300g brašna", "3 kašike šećera", "3 jaja"));
             r.setUpute(Arrays.asList("umiješati brašno sa šećerom", "zagrijati tavu 3 minute", "peći palačinke"));

             kategorija.setRecepti(Arrays.asList(r));
             db.insertRecept(r);
             db.insertKategorija(kategorija);
             db.insertReceptUKategoriju(kategorija, r);

             Kategorija kat = new Kategorija("slano", null);
             Recept r1 = new Recept();
             r1.setImeRecepta("kifla");
             r1.setPhotoRecept(null);
             r1.setNotes(null);
             r1.setSastojci(Arrays.asList("300g brašna", "3 jaja"));
             r1.setUpute(Arrays.asList("peći u pećnici 15min"));
             kat.setRecepti(Arrays.asList(r1));
             db.insertRecept(r1);
             db.insertKategorija(kat);
             db.insertReceptUKategoriju(kat, r1);

        }

        kategorijaAdapter = new KategorijaAdapter(db.getAllKategorije(), this, recyclerView);
        recyclerView.setAdapter(kategorijaAdapter);
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
