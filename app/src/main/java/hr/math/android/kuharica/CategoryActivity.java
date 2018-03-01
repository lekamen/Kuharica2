package hr.math.android.kuharica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReceptAdapter adapter;
    private DBRAdapter db;
    private Kategorija mcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        Intent categoryIntent = getIntent();
        db = new DBRAdapter(this);
        db.open();
        mcategory = db.getKategorija(categoryIntent.getExtras().getLong("categoryId"));
        List<Recept> categoryRecipes = db.getAllReceptiFromKategorija(mcategory.getId());
        db.close();

        adapter = new ReceptAdapter(categoryRecipes, this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setTitle(mcategory.getImeKategorije());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new FAMFragment()).commit();
        }

        navigationView.setCheckedItem(R.id.home);

    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment = null;
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new FAMFragment();
            ft.replace(R.id.fragment, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_category, menu);
        MenuItem splitViewItem = menu.findItem(R.id.split_view_settings);
        splitViewItem.setChecked(getFromSP(getString(R.string.splitViewEnabled)));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.split_view_settings:
                if(item.isChecked()){
                    item.setChecked(false);
                    saveInSP(getString(R.string.splitViewEnabled), false);
                }
                else{
                    item.setChecked(true);
                    saveInSP(getString(R.string.splitViewEnabled), true);
                }
                break;
            case R.id.search:
                openSearchActivity();
                break;
            case R.id.sort_AZ:
                sortAlphabetically();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private boolean getFromSP(String key){
        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences(
                getString(R.string.mPrefs), MODE_PRIVATE);
        return mPrefs.getBoolean(key, false);
    }

    private void saveInSP(String key, boolean value){
        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences(
                getString(R.string.mPrefs), MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("categoryId", mcategory.getId());
        startActivity(intent);
    }

    private void sortAlphabetically(){
        db.open();
        List<Recept> recepti = db.getAllReceptiFromKategorija(mcategory.getId());
        db.close();

        Collections.sort(recepti);

        adapter = new ReceptAdapter(recepti, this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
