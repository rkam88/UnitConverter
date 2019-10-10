package net.rusnet.sb.unitconverter.models;

import androidx.annotation.StringRes;

import net.rusnet.sb.unitconverter.R;

public enum Unit {
    KILOMETRE(R.string.unit_kilometre, 1000.0, 0.001),
    METER(R.string.unit_metre, 1, 1),
    CENTIMETRE(R.string.unit_centimetre,0.01,100.0),

    SQ_KILOMETRE(R.string.unit_sq_kilometre, 1000000.0, 0.000001),
    SQ_METER(R.string.unit_sq_metre, 1, 1),
    SQ_CENTIMETRE(R.string.unit_sq_centimetre,0.0001,10000.0);

    @StringRes
    public int mLabelResource;
    public double mConversionToBase;
    public double mConversionFromBase;

    Unit(@StringRes int labelResource, double conversionToBase, double conversionFromBase) {
        mLabelResource = labelResource;
        mConversionToBase = conversionToBase;
        mConversionFromBase = conversionFromBase;
    }

}
