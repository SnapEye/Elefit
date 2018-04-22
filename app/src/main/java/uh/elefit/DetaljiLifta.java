package uh.elefit;

import android.os.Bundle;
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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class DetaljiLifta extends AppCompatActivity {
    String url = "";
    CallAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_lifta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Detalji lifta");
        toolbar.setSubtitle(getIntent().getStringExtra("ID"));

        RequestQueue queue = Volley.newRequestQueue(this);
        this.api = new CallAPI(queue);

        dohvatiDetaljeLifta();
        dohvatiZadnjiServis();
        napraviGraf();

    }

    protected void napraviGraf() {
        /*BarChart chart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        //BarChart chart = new BarChart(this);
        setContentView(chart);
        BarData data = new BarData();
        //BarData data = new BarData((IBarDataSet)labels, dataset);
        chart.setData(data);
*/
        LineChart chart= findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(2, 4));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 6));


        LineDataSet dataSet = new LineDataSet(entries, "Label");
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
