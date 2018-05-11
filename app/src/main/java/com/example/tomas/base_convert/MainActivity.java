package com.example.tomas.base_convert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int base1 = 0;
    int base2 = 0;

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

                //gets previous base selection
                int prevBase = 0;
                if(base1 != 0)
                    prevBase = base1;


                base1 = Integer.parseInt(spinner1.getSelectedItem().toString());

                if(base1 > 10)
                    editText1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                else
                    editText1.setInputType(InputType.TYPE_CLASS_NUMBER);

                editText1.setFilters(new InputFilter[] {getFilter(base1)});

                if(!(editText1.getText().toString().equals("")) && prevBase != 0)
                {
                    editText1.setText(baseConvert(editText1.getText().toString(),prevBase,base1));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //gets previous base selection
                int prevBase = 0;
                if(base2 != 0)
                    prevBase = base2;

                base2 = Integer.parseInt(spinner2.getSelectedItem().toString());
                if(base2 > 10)
                    editText2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

                //adds input filters
                editText2.setFilters(new InputFilter[] {getFilter(base2)});

                if(!(editText2.getText().toString().equals("")) && prevBase != 0)
                {
                    editText2.setText(baseConvert(editText2.getText().toString(),prevBase,base2));
                }


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

                    if(editText1.getText().toString().equals(""))
                        editText2.setText("");
                    else {
                        editText2.setText(baseConvert(editText1.getText().toString(), base1, base2));
                    }
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
                    if(editText2.getText().toString().equals(""))
                        editText1.setText("");
                    else {
                        editText1.setText(baseConvert(editText2.getText().toString(), base2, base1));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //function for adding input filter depending on base
    private static InputFilter getFilter(final int base)
    {
         return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source instanceof SpannableStringBuilder) {
                    SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
                    for (int i = end - 1; i >= start; i--) {
                        char x = source.charAt(i);

                        if(base <= 10) {
                            if(!(x >= 48 && x < (48+base)))
                                sourceAsSpannableBuilder.delete(i, i+1);
                        }
                        else if (!Character.isDigit(x) && !(x >= 65 && x < (65+base-10)) && !(x >= 97 && x < (97+base-10))) {
                            sourceAsSpannableBuilder.delete(i, i+1);
                        }
                    }
                    return source;
                } else {
                    StringBuilder filteredStringBuilder = new StringBuilder();
                    for (int i = start; i < end; i++) {
                        char x = source.charAt(i);

                        if(base <= 10 ) {
                            if(x >= 48 && x <= (48+base-1))
                                filteredStringBuilder.append(x);
                        }
                        else if (Character.isDigit(x) || (x >= 65 && x < (65 + base - 10)) || (x >= 97 && x < (97 + base - 10))) {
                            filteredStringBuilder.append(x);
                        }
                    }
                    return filteredStringBuilder.toString();
                }
            }
        };
    }

    private static String baseConvert(String num,int base1, int base2)
    {
        num = num.toUpperCase();
        long decNum = 0;

        //converts to decimal first
        for(int i = 0; i < num.length(); i++ )
        {
            if (num.charAt(i) < 65)
                decNum += (num.charAt(i) - 48)*(long)Math.pow(base1, num.length() - i - 1);

            else
                decNum += (num.charAt(i) - 55)*(long)Math.pow(base1, num.length() - i - 1);
        }


        char [] baseNum = new char[50];
        int index = 0;


        while(decNum != 0)
        {
            long temp = decNum % base2;

            if(temp < 10)
                baseNum[index] = (char) (temp + 48);
            else
                baseNum[index] = (char) (temp +55);

            index++;
            decNum /= base2;
        }

        String numConvert = "";

        for(int i = index; i >= 0; i--)
            numConvert += baseNum[i];

        return numConvert;
    }

}
