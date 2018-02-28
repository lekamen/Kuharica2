package hr.math.android.kuharica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReceptAdapter adapterRecept;
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
        db.close();
        setTitle(mcategory.getImeKategorije());
        populaterecyclerView(mcategory.getId());

    }

    private void populaterecyclerView(long catId){

        db.open();
        List<Recept> categoryRecipes = db.getAllReceptiFromKategorija(catId);
        db.close();

        adapterRecept = new ReceptAdapter(categoryRecipes, this, mRecyclerView);
        mRecyclerView.setAdapter(adapterRecept);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterRecept.notifyDataSetChanged();
    }

    @Override
    protected void onPause(){
        super.onPause();
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
}
