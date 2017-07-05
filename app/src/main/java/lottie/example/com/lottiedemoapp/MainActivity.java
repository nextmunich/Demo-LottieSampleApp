package lottie.example.com.lottiedemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openShape1Activity(View view) {
        startActivity(new Intent(this,Shape1Activity.class));
    }

    public void openShape2Activity(View view) {
        startActivity(new Intent(this,Shape2Activity.class));
    }

    public void openShape3Activity(View view) {
        startActivity(new Intent(this,Shape3Activity.class));
    }
}
