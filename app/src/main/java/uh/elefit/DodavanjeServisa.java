package uh.elefit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DodavanjeServisa extends AppCompatActivity {
    Spinner s;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_servisa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button unos = findViewById(R.id.gumb_dodaj_novi);
        unos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unesiPodatke();

            }
        });

        String[] arraySpinner = new String[] {
                "A","B","C"
        };
        s= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }

    private void unesiPodatke() {
        int ciklus= getIntent().getIntExtra("ciklus", 1);
        String faza= getIntent().getStringExtra("faza");
        if(!(getIntent().getStringExtra("ocjena").equals("C"))){
            if(faza.equals("INIT")) faza="SMALL1";
            else if(faza.equals("SMALL1")) faza="LARGE";
            else if(faza.equals("LARGE")) faza="SMALL2";
            else if(faza.equals("SMALL2")){
                faza="INIT";
                ciklus++;
            }
        }
        EditText opis=findViewById(R.id.novi_opaska);
        this.url="http://jospudja.pythonanywhere.com/unosServisa?id_dizalo=" +getIntent().getStringExtra("ID")+
                "&id_korisnik=" + Korisnik.id +
                "&ocjena=" + s.getSelectedItem().toString()+
                "&faza=" +  faza+
                "&ciklus=" + ciklus+
                "&opis=" + opis.getText().toString();

        provediUnos();

    }

    private void provediUnos(){
        RequestQueue queue = Volley.newRequestQueue(this);

        CallAPI api= new CallAPI(queue);




        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if(result!=null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt=result.getJSONObject(i);


                        }catch(JSONException e){

                        }
                    }
                }
            }
        });
        finish();
    }

}
