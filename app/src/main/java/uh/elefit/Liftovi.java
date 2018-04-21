package uh.elefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Liftovi extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerViewAdapter adapter;

    String url = "http://jospudja.pythonanywhere.com/dizalaZaPrikazUFragmentu";

    final ArrayList<PodaciZaLiftFragment> liftovi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liftovi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //toolbar.setLogo(R.drawable.logo);
        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle(R.string.liftovi);


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
                postaviFragment();
            }
        });

    }

    public void postaviFragment(){

        RecyclerView recyclerView = findViewById(R.id.listaLiftova);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, liftovi);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Kliknuli ste " + adapter.getItem(position) + " u redu broj " + (position+1), Toast.LENGTH_SHORT).show();
    }
}
