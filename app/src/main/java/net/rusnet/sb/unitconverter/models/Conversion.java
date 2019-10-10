package net.rusnet.sb.unitconverter.models;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;


import java.util.Arrays;
import java.util.List;

import net.rusnet.sb.unitconverter.R;

public enum Conversion {
    Length(R.string.length, Arrays.asList(Unit.METER, Unit.KILOMETRE, Unit.CENTIMETRE)),
    Area(R.string.area, Arrays.asList(Unit.SQ_METER, Unit.SQ_KILOMETRE, Unit.SQ_CENTIMETRE));

    @StringRes
    public final int mLabelRes;
    public final List<Unit> mUnits;

    Conversion(@StringRes int labelRes, @NonNull List<Unit> units) {
        mLabelRes = labelRes;
        mUnits = units;
    }

}
