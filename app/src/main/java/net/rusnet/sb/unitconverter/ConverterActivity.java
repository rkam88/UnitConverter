package net.rusnet.sb.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import net.rusnet.sb.unitconverter.models.Conversion;
import net.rusnet.sb.unitconverter.models.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConverterActivity extends AppCompatActivity {

    private EditText mInputEditText;
    private EditText mOutputEditText;

    Spinner inputSpinner;
    Spinner outputSpinner;

    private Conversion currentConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        currentConversion = (Conversion) extras.get(MainActivity.CONVERSION);

        String[] unitList = getUnitList(currentConversion);

        inputSpinner = initSpinner(unitList, R.id.from_units_spinner);
        outputSpinner = initSpinner(unitList, R.id.to_units_spinner);

        mInputEditText = findViewById(R.id.input_edit_text);

        mOutputEditText = findViewById(R.id.output_edit_text);

        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getCurrentFocus() == mInputEditText) {
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
                if (getCurrentFocus() == mOutputEditText) {
                    updateResult(mOutputEditText, mInputEditText, true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private Spinner initSpinner(String[] unitList, int spinnerResourceID) {
        Spinner spinner = (Spinner) findViewById(spinnerResourceID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (spinnerResourceID == R.id.to_units_spinner) {
            spinner.setSelection(1);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getCurrentFocus() == mOutputEditText) {
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
            double toBase = currentConversion.mUnits.get(sourceSpinner.getSelectedItemPosition()).mConversionToBase;
            double fromBase = currentConversion.mUnits.get(targetSpinner.getSelectedItemPosition()).mConversionFromBase;
            double result = sourceNumber * toBase * fromBase;
            String textResult = String.format(Locale.US,"%f", result);
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
