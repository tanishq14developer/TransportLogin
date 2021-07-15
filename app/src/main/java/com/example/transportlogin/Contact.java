package com.example.transportlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;


public class Contact extends AppCompatActivity {
    private EditText mobile;
    private TextView cont;
    CountryCodePicker cpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mobile = findViewById(R.id.mobile);
        cont = findViewById(R.id.conti);
        cpp = findViewById(R.id.cpp);
        cpp.registerCarrierNumberEditText(mobile);


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
                if (mobile.equals("") || mobile.equals(null) || mobile.length()<11){
                   mobile.setError("Enter the Number");
                   return;
                }
                String number = mobile.getText().toString();
                Intent intent = new Intent(Contact.this,Verify.class);
                Intent intent1 = intent.putExtra("Mobile_number", cpp.getFullNumberWithPlus().replace("", ""));
                Intent init = intent.putExtra("number",number);
                startActivity(intent);

            }
        });
    }

}