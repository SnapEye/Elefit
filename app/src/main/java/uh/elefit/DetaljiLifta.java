package uh.elefit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetaljiLifta extends AppCompatActivity {
    String url="http://jospudja.pythonanywhere.com/dizala";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_lifta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getIntent().getStringExtra("ID"));
<<<<<<< HEAD


        RequestQueue queue = Volley.newRequestQueue(this);
        CallAPI api= new CallAPI(queue);

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if(result!=null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt=result.getJSONObject(i);

                            liftovi.add(new PodaciZaLiftFragment(objekt.getString("id"), objekt.getString("datum"), objekt.getString("ocjena"),  objekt.getString("faza")));
                        }catch(JSONException e){

                        }
                    }
                }

            }
        });



=======
>>>>>>> a7c23b7be45facdc865ac2151bae1f228d629078
    }

}
