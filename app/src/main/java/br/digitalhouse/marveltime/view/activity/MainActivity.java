package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import br.digitalhouse.marveltime.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView imageViewPersonagens;
    private CircleImageView imageViewFavoritos;
    private CircleImageView imageViewQuiz;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        imageViewQuiz.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerQuizActivity.class)));

        imageViewPersonagens.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerPersonagensActivity.class)));

        imageViewFavoritos.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerFavoritosActivity.class)));

    }

    private void initViews (){
        imageViewFavoritos = findViewById(R.id.img_favoritos);
        imageViewPersonagens = findViewById(R.id.img_personagens);
        imageViewQuiz = findViewById(R.id.img_quiz);
    }
}