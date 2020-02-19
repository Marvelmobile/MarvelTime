package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class QuizActivity extends AppCompatActivity {
    private ImageView imgHomemAranha;
    private ImageView imgHomemDeFerro;
    private ImageView imgThor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();

        imgHomemAranha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, RecebePerguntasQuizActivity.class));
            }
        });

        imgHomemDeFerro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, RecebePerguntasQuizActivity.class));
            }
        });

        imgThor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, RecebePerguntasQuizActivity.class));
            }
        });

    }

    private void initViews (){
        imgHomemAranha = findViewById(R.id.img_spider_man);
        imgHomemDeFerro = findViewById(R.id.img_iron_man);
        imgThor = findViewById(R.id.img_thor);
    }
}