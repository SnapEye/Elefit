package uh.elefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

public class MojiServisi extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    RecyclerViewAdapter adapter;
    final ArrayList<PodaciZaLiftFragment> liftovi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moji_servisi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle(R.string.moji_servisi);

        String url = "http://jospudja.pythonanywhere.com/mojiServisi?id="+Korisnik.id;
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
        //toolbar.setLogo(R.drawable.logo);
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
        promijeniFragment(adapter.getItem(position));
    }

    public  void promijeniFragment(String ID){

        Intent intent= new Intent(MojiServisi.this, DetaljiLifta.class);
        intent.putExtra("ID", ID);
        startActivity(intent);

    }

    public void ReturnHome(View view) {
        super.onBackPressed();
    }
}
