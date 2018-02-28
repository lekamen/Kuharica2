package hr.math.android.kuharica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.clans.fab.Label;

import java.io.File;
import java.util.List;

public class NovaKategorijaActivity extends AppCompatActivity {

    EditText imeKategorije;
    String path;
    boolean imePostoji = false;
    Label label;
    Label statusSlike;
    Button novaKategorija;
    DBRAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_kategorija);
        db = new DBRAdapter(this);
        label = (Label)findViewById(R.id.imePostojiLabel);
        statusSlike = (Label)findViewById(R.id.statusSlike);
        novaKategorija = (Button)findViewById(R.id.novaKategorija);

        imeKategorije = (EditText)findViewById(R.id.imeKategorije);
        imeKategorije.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ime = imeKategorije.getText().toString();

                db.open();
                List<Kategorija> lista = db.getAllKategorije();
                db.close();

                for(Kategorija k : lista) {
                    if(k.getImeKategorije().toLowerCase().equals(ime.toLowerCase())) {
                        imePostoji = true;
                        break;
                    }
                    imePostoji = false;
                }

                if(imePostoji) {
                    label.setVisibility(View.VISIBLE);
                    novaKategorija.setEnabled(false);
                } else {
                    label.setVisibility(View.INVISIBLE);
                    novaKategorija.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ime = imeKategorije.getText().toString();
                if(ime.equals("")) {
                    novaKategorija.setEnabled(false);
                }
            }
        });
    }

    public void odustani(View view) {
        finish();
    }

    public void napraviNovuKategoriju(View view) {
        db.open();
        Kategorija k = new Kategorija(imeKategorije.getText().toString(), path);
        db.insertKategorija(k);
        db.close();
        finish();
    }

    public static final int PICK_IMAGE = 1;
    public void birajSliku(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data == null) {
                statusSlike.setText("Slika nije uspješno odabrana");
                statusSlike.setVisibility(View.VISIBLE);
                return;
            }

            statusSlike.setText("Slika uspješno odabrana");
            statusSlike.setVisibility(View.VISIBLE);

            path = data.getData().toString();
        } else {
            statusSlike.setText("Slika nije odabrana");
            statusSlike.setVisibility(View.VISIBLE);
        }
    }
}
