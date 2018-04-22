package uh.elefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static uh.elefit.Korisnik.id;

public class Prijava extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btnPrijava= findViewById(R.id.btnPrijava);
        btnPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Prijava.this, Main2.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    protected void provjera(){
        RequestQueue queue = Volley.newRequestQueue(this);

        CallAPI api= new CallAPI(queue);


        String url = "http://jospudja.pythonanywhere.com/prijava?korime="+findViewById(R.id.korisnicko_ime);

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if(result!=null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt=result.getJSONObject(i);

                            objekt.getString("id");
                        }catch(JSONException e){

                        }
                    }
                }
                postaviFragment();
            }
        });
    }

}
