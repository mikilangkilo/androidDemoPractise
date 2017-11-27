package com.example.administrator.dagger2practise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName().toString();
    @Inject
    Person person;
    @Inject
    Person person2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainComponent component = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        component.inject(this);
        Log.i(TAG, "person1 = "+person.toString()+" person2 = "+person2.toString());
        View text = findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SwitchActivity.class);
                startActivity(intent);
            }
        });
    }
}
