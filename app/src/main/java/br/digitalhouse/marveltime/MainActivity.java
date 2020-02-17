package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button homemAranha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homemAranha = findViewById(R.id.homemAranha);

        homemAranha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direcionaPerguntas();
            }
        });

    }

    private void direcionaPerguntas(){
        startActivity(new Intent(MainActivity.this, RecebePerguntasQuizActivity.class));
    }
}
