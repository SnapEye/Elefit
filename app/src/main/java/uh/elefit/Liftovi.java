package uh.elefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Liftovi extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liftovi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //toolbar.setLogo(R.drawable.logo);
        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle(R.string.liftovi);


        ArrayList<PodaciZaLiftFragment> liftovi = new ArrayList<>();
        liftovi.add(new PodaciZaLiftFragment("BT9291AV", "13.04.2015", "B", "5"));
        liftovi.add(new PodaciZaLiftFragment("BT9291AV", "13.04.2015", "B", "5"));
        liftovi.add(new PodaciZaLiftFragment("BT9291AV", "13.04.2015", "B", "5"));

        RecyclerView recyclerView = findViewById(R.id.listaLiftova);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, liftovi);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
