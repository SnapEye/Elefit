package uh.elefit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Grafikon extends AppCompatActivity {

    String url = "";
    CallAPI api;
    HashMap<String, String> vrijednosti;
    HashMap<Integer, String>numMap;
    List<BarEntry> entries;
    HashMap<Integer, String>Ocjene;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafikon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle(R.string.grafikon);

        RequestQueue queue = Volley.newRequestQueue(this);

        api= new CallAPI(queue);

        String[] arraySpinner = new String[] {
                "INIT", "SMALL1", "LARGE", "SMALL2"
        };
        s=findViewById(R.id.odabrifaze);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        numMap=new HashMap<>();
        entries = new ArrayList<BarEntry>();
        Ocjene= new HashMap<>();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dohvatiServiseZaGraf(s.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                dohvatiServiseZaGraf("INIT");
            }

        });
    }

    protected void napraviGraf() {

        BarChart chart= findViewById(R.id.chartSvi);


        YAxis yaxis=chart.getAxisLeft();
        System.out.println(numMap.get(1));
        chart.getAxisRight().setEnabled(false);
        yaxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)value);
            }


        });

        Ocjene.put(2,"A");
        Ocjene.put(1,"B");
        Ocjene.put(0,"C");

        XAxis xaxis=chart.getXAxis();
        //chart.getAxisRight().setEnabled(false);
        xaxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Ocjene.get((int)value);
            }


        });

        BarDataSet dataSet = new BarDataSet(entries, getIntent().getStringExtra("ID"));

        BarData barData= new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate();
    }

    protected void dohvatiServiseZaGraf(String faza){
        this.url = "http://jospudja.pythonanywhere.com/servisiPoFaza?faza="+faza ;
        numMap.clear();
        entries.clear();
        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt = result.getJSONObject(i);

                            System.out.println(objekt.getString("ocjena")+objekt.getString("faza"));

                            numMap.put(i, objekt.getString("dizalo"));

                            if(objekt.getString("ocjena").equals("A"))entries.add(new BarEntry(2, i));
                            else if(objekt.getString("ocjena").equals("B"))entries.add(new BarEntry(1, i));
                            else if(objekt.getString("ocjena").equals("C"))entries.add(new BarEntry(0, i));

                        } catch (JSONException e) {

                        }
                    }
                    napraviGraf();
                }
            }
        });
    }
}
