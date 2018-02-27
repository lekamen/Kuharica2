package hr.math.android.kuharica;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        GridView gridView = (GridView) findViewById(R.id.grid);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
        gridView.invalidateViews();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "ovdje se treba otvorit novi activity s kategorijama",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeDatabase() {
        DBAdapter db = new DBAdapter(this);

        db.open();
        if(db.getAllKategorije() != null && db.getAllKategorije().getCount() > 0) {
            db.close();
            return;
        }

        long id = db.insertKategorija("Slatko", String.valueOf(R.drawable.cake));
        long id2 = db.insertRecept("palačinke", String.valueOf(R.drawable.pancakes), null);
        db.insertSastojak(id2, "300g brašna");
        db.insertSastojak(id2, "3 kašike šećera");
        db.insertSastojak(id2, "3 jaja");

        db.insertUpute(id2, "umiješati brašno sa šećerom");
        db.insertUpute(id2, "zagrijati tavu 3 minute");
        db.insertUpute(id2, "peći palačinke");
        db.insertReceptUKategoriju(id, id2);

        id = db.insertKategorija("Slano", null);
        id2 = db.insertRecept("kifla", null, null);
        db.insertSastojak(id2, "300g brašna");
        db.insertSastojak(id2, "3 jaja");

        db.insertUpute(id2, "peći u pećnici 15min");
        db.insertReceptUKategoriju(id, id2);
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
