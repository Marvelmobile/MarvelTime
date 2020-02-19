package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity {
    private ImageView imgHomemAranha;
    private ImageView imgHomemDeFerro;
    private ImageView imgThor;

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

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

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                break;
            case R.id.item2:
               startActivity(new Intent(QuizActivity.this, TelaFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(QuizActivity.this, PersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(QuizActivity.this, QuizActivity.class));
                break;
        }
    }
}