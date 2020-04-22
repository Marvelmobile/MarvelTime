package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;

public class MainActivity extends AppCompatActivity {
    private Button imageViewPersonagens;
    private Button imageViewFavoritos;
    private Button imageViewQuiz;
    private ImageView imageViewSair;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        imageViewQuiz.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerQuizActivity.class)));

        imageViewPersonagens.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerPersonagensActivity.class)));

        imageViewFavoritos.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclerFavoritosActivity.class)));

        imageViewSair.setOnClickListener(v -> {
            Helper.deslogarFirebase();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }

    private void initViews (){
        imageViewFavoritos = findViewById(R.id.img_favoritos);
        imageViewPersonagens = findViewById(R.id.img_personagens);
        imageViewQuiz = findViewById(R.id.img_quiz);
        imageViewSair = findViewById(R.id.img_sair);
    }
}