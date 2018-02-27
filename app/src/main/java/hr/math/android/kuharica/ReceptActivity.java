package hr.math.android.kuharica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReceptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = savedInstanceState.getString("recipeName");
        long ID = savedInstanceState.getLong("recipeID");
        TextView name_text = (TextView) findViewById(R.id.recipeName);
        name_text.setText(name);
        DBRAdapter db = new DBRAdapter(this);
        db.open();
        Recept current = db.getReceptZaId(ID);
        List<String> sastojci = current.getSastojci();
        List<String> upute = current.getUpute();
        ListView lista = (ListView) findViewById(R.id.sastojci_list);

        ArrayList<Sastojak> temp = new ArrayList<>();
        for (String s: sastojci) {
            Sastojak sas = new Sastojak();
            sas.setSastojak(s);
            sas.setSelected(false);
            temp.add(sas);
        }
        SastojciAdapter adapter_sastojci = new SastojciAdapter(this, temp);
        lista.setAdapter(adapter_sastojci);

        setContentView(R.layout.activity_recept);
    }


}
