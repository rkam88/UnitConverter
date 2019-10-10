package net.rusnet.sb.unitconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.rusnet.sb.unitconverter.models.Conversion;

import java.util.ArrayList;
import java.util.List;

class UnitListAdapter
        extends RecyclerView.Adapter<UnitListAdapter.UnitHolder> {

    private List<Conversion> mConversions;
    private IMainItemCLickListener mMainItemCLickListener;

    public UnitListAdapter(@NonNull List<Conversion> conversions, @NonNull IMainItemCLickListener mainItemCLickListener) {
        mConversions = new ArrayList<>(conversions);
        mMainItemCLickListener = mainItemCLickListener;
    }

    @NonNull
    @Override
    public UnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit, parent, false);
        return new UnitHolder(view, mMainItemCLickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitHolder holder, int position) {
        holder.bindView(mConversions.get(position));
    }

    @Override
    public int getItemCount() {
        return mConversions.size();
    }

    public class UnitHolder extends RecyclerView.ViewHolder {
        final TextView mUnitName;
        final IMainItemCLickListener mMainItemClickListener;

        public UnitHolder(@NonNull View itemView, IMainItemCLickListener mainItemCLickListener) {
            super(itemView);
            mUnitName = itemView.findViewById(R.id.unit_name_text);
            mMainItemClickListener = mainItemCLickListener;
        }

        void bindView(final Conversion conversion) {
            mUnitName.setText(conversion.mLabelRes);
            itemView.setOnClickListener(v -> mMainItemClickListener.onMainItemCLick(conversion));
        }

    }

}

/*
getAdaptePosition показывает по какому элементу произоел клик


 */


