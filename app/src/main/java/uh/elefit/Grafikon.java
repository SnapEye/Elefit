package uh.elefit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
    List<Entry> entries;
    HashMap<Integer, String>Ocjene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafikon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle(R.string.grafikon);


        numMap=new HashMap<>();
        entries = new ArrayList<Entry>();
        Ocjene= new HashMap<>();

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

        LineDataSet dataSet = new LineDataSet(entries, getIntent().getStringExtra("ID"));
        LineData lineData= new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    protected void dohvatiServiseZaGraf(){
        url = "http://jospudja.pythonanywhere.com/servisiPoFaza" + getIntent().getStringExtra("dizalo");

        api.pozovi(url, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject objekt = result.getJSONObject(i);

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
}
