package net.rusnet.sb.unitconverter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.rusnet.sb.unitconverter.models.Conversion;

import java.util.Arrays;

public class UnitListFragment extends Fragment implements IMainItemCLickListener {

    private OnConversionSelectedListener listener;

    public UnitListFragment() {
        super(R.layout.fragment_unit_list);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnConversionSelectedListener) {
            listener = (OnConversionSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement UnitListFragment.OnConversionSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) super.onCreateView(inflater, container, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                recyclerView.getContext(),
                RecyclerView.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        UnitListAdapter adapter = new UnitListAdapter(Arrays.asList(Conversion.values()), this::onMainItemCLick);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return recyclerView;
    }

    @Override
    public void onMainItemCLick(Conversion conversion) {
        Log.d("TAG", "onMainItemCLick: clicked on conversion: " + getResources().getString(conversion.mLabelRes));
        listener.onConversionSelected(conversion);
    }

    public interface OnConversionSelectedListener {
        public void onConversionSelected(Conversion conversion);
    }
}
