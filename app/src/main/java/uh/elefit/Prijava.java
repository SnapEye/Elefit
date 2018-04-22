package uh.elefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                provjera();

            }
        });

    }


    protected void provjera(){
        RequestQueue queue = Volley.newRequestQueue(this);

        CallAPI api= new CallAPI(queue);

        EditText korime=findViewById(R.id.korisnicko_ime);
        EditText lozinka=findViewById(R.id.lozinka);
        System.out.println(korime.getText().toString());

        String url = "http://jospudja.pythonanywhere.com/prijava?korime="+korime.getText().toString()+"&lozinka="+lozinka.getText().toString();

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if(result!=null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt=result.getJSONObject(i);
                            if(objekt.getString("id").isEmpty()){

                            }
                            else{
                                Korisnik.id=objekt.getString("id");
                                Intent intent = new Intent(Prijava.this, Main2.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch(JSONException e){

                        }
                    }
                    if(result.length()<1) {
                        izbaciTost();
                    }
                }
            }
        });
    }

    protected void izbaciTost(){
        Toast.makeText(this, "Lozinka ili korisnicko ime nisu ispravni", Toast.LENGTH_SHORT).show();
    }

}
