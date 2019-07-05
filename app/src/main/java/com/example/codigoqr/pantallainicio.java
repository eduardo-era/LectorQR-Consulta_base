package com.example.codigoqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pantallainicio extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);

        Button btnid = (Button) findViewById(R.id.btnid);
        btnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(),pantallaid.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btnesca = (Button) findViewById(R.id.btnesca);
        btnesca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent2,0);
            }
        });
    }
}
