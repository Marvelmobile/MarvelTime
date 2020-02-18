package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private CircleImageView imageViewPersonagens;
    private CircleImageView imageViewFavoritos;
    private CircleImageView imageViewQuiz;
  
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
        initViews();
        imageViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
        imageViewPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
        imageViewFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }

    private void initViews (){
        imageViewFavoritos = findViewById(R.id.img_favoritos);
        imageViewPersonagens = findViewById(R.id.img_personagens);
        imageViewQuiz = findViewById(R.id.img_quiz);
    }
}
