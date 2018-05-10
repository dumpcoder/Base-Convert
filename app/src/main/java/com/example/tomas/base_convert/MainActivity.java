package com.example.tomas.base_convert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    int base1;
    int base2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner1 =  findViewById(R.id.spinner1);
        final Spinner spinner2 =  findViewById(R.id.spinner2);
        final EditText editText1 = findViewById(R.id.editText1);
        final EditText editText2 = findViewById(R.id.editText2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter) ;
        spinner2.setAdapter(adapter) ;

        spinner1.setSelection(adapter.getPosition("10")); //sets to second item of the array
        spinner2.setSelection(adapter.getPosition("2")); //set to first item of the array

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                base1 = Integer.parseInt(spinner1.getSelectedItem().toString());

                if(base1 > 10)
                    editText1.setInputType(InputType.TYPE_CLASS_TEXT);

                else
                    editText1.setInputType(InputType.TYPE_CLASS_NUMBER);

                editText1.setFilters(new InputFilter[] {getFilter(base1)});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                base2 = Integer.parseInt(spinner2.getSelectedItem().toString());
                if(base2 > 10)
                    editText2.setInputType(InputType.TYPE_CLASS_TEXT);
                else
                    editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

                editText2.setFilters(new InputFilter[] {getFilter(base2)});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(getCurrentFocus() == editText1) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(getCurrentFocus() == editText2) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static InputFilter getFilter(final int base)
    {
         return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for(int i = start; i < end; i++)
                {
                    char x = source.charAt(i);

                    if(!Character.isDigit(x) && !(x >= 65 && x <= (65+base-11)) && !(x >= 97 && x <= (97+base-11))) {

                        return "";
                    }
                }

                return null;
            }
        };
    }
}
