package com.zorik.altaev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText FName, LName, NGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FName = findViewById(R.id.first_name);
        LName= findViewById(R.id.last_name);
        NGroup = findViewById(R.id.Group);
    }
    public void ButtonAdd(View view) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("FName", FName.getText().toString());
        intent.putExtra("LName", LName.getText().toString());
        intent.putExtra("NGroup", NGroup.getText().toString());

        startActivity(intent);


    }
}
