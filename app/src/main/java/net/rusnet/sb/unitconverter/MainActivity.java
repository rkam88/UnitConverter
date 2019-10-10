package net.rusnet.sb.unitconverter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.rusnet.sb.unitconverter.models.Conversion;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements IMainItemCLickListener {


    public static final String CONVERSION = "CONVERSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        LinearLayoutManager layoutManager;

        recyclerView = findViewById(R.id.unit_list_recycler);
        layoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        UnitListAdapter adapter = new UnitListAdapter(Arrays.asList(Conversion.values()), this::onMainItemCLick);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public void onMainItemCLick(Conversion conversion) {
        Intent intent = new Intent(this, ConverterActivity.class);
        intent.putExtra(CONVERSION, conversion);
        startActivity(intent);
    }
}
