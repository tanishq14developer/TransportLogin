package com.example.transportlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class Verify extends AppCompatActivity {
    private  TextView displaynumber;
    TextView verifynext;

    ImageView bckarr;
    public String Number_entered_byUser,code_by_system;
    public TextView Verify;
    public TextView Resend;
    public PinView Otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        displaynumber = findViewById(R.id.displaynumber);
        String number = getIntent().getStringExtra("number");
        displaynumber.setText(number);
        bckarr = findViewById(R.id.bckarr);
        Number_entered_byUser = getIntent().getStringExtra("number");
        Verify = findViewById(R.id.verifycont);
        Resend = findViewById(R.id.resend);
        Otp =findViewById(R.id.otpView);
        sendcode_to_user(Number_entered_byUser);
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend_otp(Number_entered_byUser);
            }


        });
        bckarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movebck = new Intent(Verify.this,Contact.class);
                startActivity(movebck);
                finishAffinity();
            }
        });
        verifynext = findViewById(R.id.verifycont1);
        verifynext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_code();
                Intent user = new Intent(Verify.this,userdetails.class);
                startActivity(user);
            }
        });

    }
    private void resend_otp(String number_entered_byUser) {
        sendcode_to_user(number_entered_byUser);

    }
    private void check_code(){
        String user_entered_otp = Otp.getText().toString();
        if (user_entered_otp.isEmpty() || user_entered_otp.length()<6){
            Toast.makeText(this,"Invalid Otp",Toast.LENGTH_SHORT).show();
            return;
        }
        finish_everything(user_entered_otp);

    }

    private void sendcode_to_user(String number_entered_byUser) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + number_entered_byUser,60, TimeUnit.SECONDS,this,mCallback);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull  String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            code_by_system = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                finish_everything(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(com.example.transportlogin.Verify.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };
    private void finish_everything(String code){
        Otp.setText(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_by_system,code);
        verifyandcontinue(credential);
    }

    private void verifyandcontinue(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(Verify.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(com.example.transportlogin.Verify.this,"Verified Successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(com.example.transportlogin.Verify.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}