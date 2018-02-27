package hr.math.android.kuharica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = savedInstanceState.getString("recipeName");
        int ID = savedInstanceState.getInt("recipeID");
        TextView name_text = (TextView) findViewById(R.id.recipeName);
        name_text.setText(name);
        setContentView(R.layout.activity_recept);
    }
}
