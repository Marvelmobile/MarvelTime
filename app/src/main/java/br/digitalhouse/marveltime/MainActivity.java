package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView imageViewPersonagens;
    private CircleImageView imageViewFavoritos;
    private CircleImageView imageViewQuiz;

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
        imageViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });

        imageViewPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PersonagensActivity.class));
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

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                //Home
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                //Favoritos
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.item3:
                //Personagens
                startActivity(new Intent(MainActivity.this, PersonagensActivity.class));
                break;
            case R.id.item4:
                //Quiz
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
                break;
        }
    }
}
