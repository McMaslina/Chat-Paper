package com.zodiac.user1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity {
    private EditText edLogin, edPassword;
    private FirebaseAuth mAuth;
    private Button bSignUp, bSignIn, bStart, bSignOut, bSendN;
    private TextView tvUserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null) {
            showSigned();
            String userName = "Вы вошли как:" + cUser.getEmail();
            tvUserEmail.setText(userName);
            Toast.makeText(this, "Авторизирован", Toast.LENGTH_SHORT).show();
        }
        else {
            notSignet();
            Toast.makeText(this, "Авторизируйтесь", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        bSignOut = findViewById(R.id.bSignOut);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        bStart = findViewById(R.id.bStart);
        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPassword);
        mAuth = FirebaseAuth.getInstance();
        bSignIn = findViewById(R.id.bSignIn);
        bSignUp = findViewById(R.id.bSignUp);
        bSendN = findViewById(R.id.bSendN);
    }
    public void onClickSignUp(View view) {
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())){
            mAuth.createUserWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        showSigned();
                        sendEmailVer();
                        Toast.makeText(getApplicationContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        notSignet();
                        Toast.makeText(getApplicationContext(), "Регистрация не удалась", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(), "Введите Email и Password", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickSignIn(View view) {
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        showSigned();
                        Toast.makeText(getApplicationContext(), "Пользователь авторизирован", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        notSignet();
                        Toast.makeText(getApplicationContext(), "Авторизация не удалась", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        notSignet();
    }
    private void showSigned() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;

        if (user.isEmailVerified()) {
            String userName = "Вы вошли как:" + user.getEmail();
            tvUserEmail.setText(userName);
            bStart.setVisibility(View.VISIBLE);
            tvUserEmail.setVisibility(View.VISIBLE);
            bSignOut.setVisibility(View.VISIBLE);
            bSendN.setVisibility(View.VISIBLE);
            edLogin.setVisibility(View.GONE);
            edPassword.setVisibility(View.GONE);
            bSignIn.setVisibility(View.GONE);
            bSignUp.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(getApplicationContext(), "Проверьте почту для подтверждения email", Toast.LENGTH_SHORT).show();
        }
    }
    private void notSignet() {
        bStart.setVisibility(View.GONE);
        tvUserEmail.setVisibility(View.GONE);
        bSignOut.setVisibility(View.GONE);
        bSendN.setVisibility(View.GONE);
        edLogin.setVisibility(View.VISIBLE);
        edPassword.setVisibility(View.VISIBLE);
        bSignIn.setVisibility(View.VISIBLE);
        bSignUp.setVisibility(View.VISIBLE);
    }
    public void onClickStart(View view) {
        Intent i3 = new Intent(EmailPasswordActivity.this, MainActivity.class);
        startActivity(i3);
    }
    private void sendEmailVer(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Проверьте почту для подтверждения email", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Send email failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onClickNews(View view) {
        Intent i4 = new Intent(EmailPasswordActivity.this, SendNews.class);
        startActivity(i4);
    }
}