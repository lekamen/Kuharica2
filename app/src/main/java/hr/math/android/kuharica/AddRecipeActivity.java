package hr.math.android.kuharica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {

    ArrayList<String> ingredientList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ingredientList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientList);
        ListView listView = (ListView)findViewById(R.id.ingredientsListView);
        listView.setAdapter(adapter);
    }

    public void addBtnClick(View view){
        EditText editText = (EditText)findViewById(R.id.ingredientEditText);
        String ingerdient = editText.getText().toString();

        if(ingerdient.isEmpty()) return;

        ingredientList.add(ingerdient);
        editText.setText(R.string.addRecipe_ingredientHint);
        adapter.clear();
        adapter.addAll(ingredientList);
        adapter.notifyDataSetChanged();
    }
}
