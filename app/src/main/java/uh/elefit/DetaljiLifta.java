package uh.elefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetaljiLifta extends AppCompatActivity {
    String url = "";
    CallAPI api;
    FloatingActionButton floating_dodaj_novi;
    HashMap<Integer, String>numMap;
    List<Entry> entries;
    HashMap<Integer, String>Ocjene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_lifta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Detalji lifta");
        toolbar.setSubtitle(getIntent().getStringExtra("ID"));

        floating_dodaj_novi = (FloatingActionButton) findViewById(R.id.floating_dodaj_novi_servis);
        floating_dodaj_novi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDodajNoviServis();
            }
        });


        numMap=new HashMap<>();
        entries = new ArrayList<Entry>();
        Ocjene= new HashMap<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        this.api = new CallAPI(queue);

        dohvatiDetaljeLifta();
        dohvatiZadnjiServis();
        dohvatiServiseZaGraf();

    }

    public void openDodajNoviServis(){
        Intent intent = new Intent(DetaljiLifta.this, DodavanjeServisa.class);
        startActivity(intent);
    }

    protected void napraviGraf() {

        LineChart chart= findViewById(R.id.chart);


        XAxis xaxis=chart.getXAxis();
        System.out.println(numMap.get(1));
        xaxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)value);

            }


        });


        Ocjene.put(2,"A");
        Ocjene.put(1,"B");
        Ocjene.put(0,"C");


        YAxis yaxis=chart.getAxisLeft();
        chart.getAxisRight().setEnabled(false);
        yaxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Ocjene.get((int)value);

            }


        });
        //System.out.println(entries.get(1));

        LineDataSet dataSet = new LineDataSet(entries, getIntent().getStringExtra("ID"));
        LineData lineData= new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        



    }

    protected void dohvatiDetaljeLifta(){

        url = "http://jospudja.pythonanywhere.com/dizalaPoId?id=" + getIntent().getStringExtra("ID");

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt = result.getJSONObject(i);
                            TextView view = findViewById(R.id.dizalo_id);
                            view.setText(objekt.getString("id"));

                            view = findViewById(R.id.dizalo_model);
                            view.setText(objekt.getString("model"));


                            view = findViewById(R.id.dizalo_proizvođać);
                            view.setText(objekt.getString("proizvodac"));


                            view = findViewById(R.id.dizalo_lokacija);
                            view.setText(objekt.getString("lokacija"));


                            view = findViewById(R.id.dizalo_nosivost);
                            view.setText(objekt.getString("nosivost(kg)"));

                        } catch (JSONException e) {

                        }
                    }
                }

            }
        });
    }

    protected void dohvatiServiseZaGraf(){
        url = "http://jospudja.pythonanywhere.com/servisiPoId?id=" + getIntent().getStringExtra("ID");

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt = result.getJSONObject(i);
                            /*TextView view = findViewById(R.id.servis_id);
                            view.setText(objekt.getString("id"));*/

                            System.out.println(objekt.getString("ocjena")+objekt.getString("faza"));
                            if(objekt.getString("faza").equals("SMALL1")||objekt.getString("faza").equals("SMALL2")){
                                numMap.put(i, "SMALL");
                            }
                            else {
                                numMap.put(i, objekt.getString("faza"));
                            }
                            if(objekt.getString("ocjena").equals("A"))entries.add(new Entry(i, 2));
                            else if(objekt.getString("ocjena").equals("B"))entries.add(new Entry(i, 1));
                            else if(objekt.getString("ocjena").equals("C"))entries.add(new Entry(i, 0));



                        } catch (JSONException e) {

                        }


                    }
                    napraviGraf();
                }

            }
        });

    }

    protected void dohvatiZadnjiServis(){
        url = "http://jospudja.pythonanywhere.com/detaljiDizalaServis?id=" + getIntent().getStringExtra("ID");
        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt = result.getJSONObject(i);
                            /*TextView view = findViewById(R.id.servis_id);
                            view.setText(objekt.getString("id"));*/

                            TextView view = findViewById(R.id.servis_ciklus);
                            view.setText(objekt.getString("ciklus"));


                            view = findViewById(R.id.servis_zadnji_datum);
                            view.setText(objekt.getString("datum"));


                            view = findViewById(R.id.korisnik_serviser);
                            view.setText(objekt.getString("ime")+" "+objekt.getString("prezime"));


                            view = findViewById(R.id.servis_faza);
                            if(objekt.getString("faza").equals("SMALL1")||objekt.getString("faza").equals("SMALL2")){
                                view.setText("SMALL");
                            }
                            else {
                                view.setText(objekt.getString("faza"));
                            }


                            view = findViewById(R.id.servis_ocjena);
                            view.setText(objekt.getString("ocjena"));

                            view = findViewById(R.id.servis_opaska);
                            view.setText(objekt.getString("opaska"));

                            view = findViewById(R.id.servis_id_servisa);
                            view.setText(objekt.getString("sifra"));

                        } catch (JSONException e) {

                        }
                    }
                }

            }
        });
    }
}
