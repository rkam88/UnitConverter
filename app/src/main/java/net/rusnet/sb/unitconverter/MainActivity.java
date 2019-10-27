package net.rusnet.sb.unitconverter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.rusnet.sb.unitconverter.models.Conversion;

public class MainActivity extends AppCompatActivity implements UnitListFragment.OnConversionSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.placeholder_layout, new UnitListFragment())
                .commit();
    }

    @Override
    public void onConversionSelected(Conversion conversion) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.placeholder_layout, ConverterFragment.newInstance(conversion))
                .addToBackStack(null)
                .commit();
    }
}
