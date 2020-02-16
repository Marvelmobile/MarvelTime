package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton bntLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INGHRIDY - 03/02/20 - SET01- Criação das Branchs GITHUB
        Log.i("Teste", "Teste");
        Log.i("Teste", "Teste - branch epic");
        Log.i("Teste", "Teste - branch epic");
        Log.i("Teste", "Teste - branch epic");

        bntLogin = findViewById(R.id.btnTest);

        bntLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PerguntasQuizActivity.class));
            }
        });
    }

}
