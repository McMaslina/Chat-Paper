package com.zodiac.user1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendNews extends AppCompatActivity {
    EditText etTo, etSubject, etMessage;
    Button bSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);

        etTo = findViewById(R.id.etTo);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
        bSend = findViewById(R.id.bSend);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + etTo.getText().toString()));
                i2.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                i2.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
                startActivity(i2);
            }
        });
    }
}