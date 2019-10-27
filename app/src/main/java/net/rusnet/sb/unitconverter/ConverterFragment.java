package net.rusnet.sb.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.rusnet.sb.unitconverter.models.Conversion;
import net.rusnet.sb.unitconverter.models.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConverterFragment extends Fragment {

    private final static String ARG_CONVERSION = "conversion";

    public ConverterFragment() {
        super(R.layout.fragment_converter);
    }

    public static ConverterFragment newInstance(Conversion conversion) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONVERSION, conversion);
        ConverterFragment fragment = new ConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private EditText mInputEditText;
    private EditText mOutputEditText;

    Spinner inputSpinner;
    Spinner outputSpinner;

    private Conversion mCurrentConversion;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = super.onCreateView(inflater, container, savedInstanceState);

        mCurrentConversion = (Conversion) getArguments().getSerializable(ARG_CONVERSION);

        String[] unitList = getUnitList(mCurrentConversion);

        inputSpinner = initSpinner(unitList, R.id.from_units_spinner);
        outputSpinner = initSpinner(unitList, R.id.to_units_spinner);

        mInputEditText = root.findViewById(R.id.input_edit_text);

        mOutputEditText = root.findViewById(R.id.output_edit_text);

        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity().getCurrentFocus() == mInputEditText) {
                    updateResult(mInputEditText, mOutputEditText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mOutputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity().getCurrentFocus() == mOutputEditText) {
                    updateResult(mOutputEditText, mInputEditText, true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }


    private Spinner initSpinner(String[] unitList, int spinnerResourceID) {
        Spinner spinner = (Spinner) root.findViewById(spinnerResourceID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (spinnerResourceID == R.id.to_units_spinner) {
            spinner.setSelection(1);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().getCurrentFocus() == mOutputEditText) {
                    updateResult(mOutputEditText, mInputEditText, true);
                } else {
                    updateResult(mInputEditText, mOutputEditText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return spinner;
    }

    private void updateResult(EditText sourceEditText, EditText targerEditText) {
        updateResult(sourceEditText, targerEditText, false);
    }

    private void updateResult(EditText sourceEditText, EditText targerEditText, boolean isReverseConversion) {
        String sourceText = sourceEditText.getText().toString();
        if (!sourceText.isEmpty()) {
            double sourceNumber = Double.parseDouble(sourceText);
            Spinner sourceSpinner;
            Spinner targetSpinner;
            if (isReverseConversion) {
                sourceSpinner = outputSpinner;
                targetSpinner = inputSpinner;
            } else {
                sourceSpinner = inputSpinner;
                targetSpinner = outputSpinner;
            }
            double toBase = mCurrentConversion.mUnits.get(sourceSpinner.getSelectedItemPosition()).mConversionToBase;
            double fromBase = mCurrentConversion.mUnits.get(targetSpinner.getSelectedItemPosition()).mConversionFromBase;
            double result = sourceNumber * toBase * fromBase;
            String textResult = String.format(Locale.US, "%f", result);
            targerEditText.setText(textResult);
        } else {
            targerEditText.setText("");
        }
    }

    private String[] getUnitList(Conversion currentConversion) {
        List<Unit> currentUnitList = new ArrayList<>(currentConversion.mUnits);
        String[] result = new String[currentUnitList.size()];
        for (int i = 0; i < currentUnitList.size(); i++) {
            Unit unit = currentUnitList.get(i);
            result[i] = getString(unit.mLabelResource);
        }
        return result;
    }
}
