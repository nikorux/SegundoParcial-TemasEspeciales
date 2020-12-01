package com.pucmm.segundoparcial_temasespeciales.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pucmm.segundoparcial_temasespeciales.Fragments.All_Products_Fragment;
import com.pucmm.segundoparcial_temasespeciales.Fragments.Products_Fragment;
import com.pucmm.segundoparcial_temasespeciales.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_product) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment,
                            new Products_Fragment(), "PRODUCTS_ACT").addToBackStack("PRODUCTS_ACT").commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, new All_Products_Fragment()).commit();

        }
    }
}