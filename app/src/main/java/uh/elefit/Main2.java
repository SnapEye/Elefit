package uh.elefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main2 extends AppCompatActivity {

    private Button button_moji_servisi;
    private Button button_liftovi;
    private Button button_grafikon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Elefit");
        toolbar.setSubtitle("Početni meni");
        toolbar.setLogo(R.drawable.logo);


        button_moji_servisi = (Button) findViewById(R.id.buttonMojiSevisi);
        button_moji_servisi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMojiServisi();
            }
        });

        button_liftovi = (Button) findViewById(R.id.buttonLiftovi);
        button_liftovi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openLiftovi();
            }
        });

        button_grafikon = (Button) findViewById(R.id.buttonGraf);
        button_grafikon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGrafikon();
            }
        });
    }
    public void openMojiServisi(){
        Intent intent = new Intent(Main2.this, MojiServisi.class);
        startActivity(intent);
    }

    public void openLiftovi(){
        Intent intent = new Intent(Main2.this, Liftovi.class);
        startActivity(intent);
    }

    public void openGrafikon(){
        Intent intent = new Intent(Main2.this, Grafikon.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
