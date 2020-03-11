package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecebePerguntasQuizActivity extends AppCompatActivity implements Selecionavel {
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);
        replaceFragments(R.id.container, new PerguntasQuizFragment());
        ButterKnife.bind(this);
    }

    @Override
    public void selecionar(int id) {

        if(id == R.id.fragment_layout_quiz_outra){
            replaceFragments(R.id.container, new OutraPerguntaQuizFragment());
        }
        if (id == R.id.fragment_layout_quiz){
            replaceFragments(R.id.container, new PerguntasQuizFragment());
        }

    }

    private void replaceFragments(int container, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .commit();
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
                startActivity(new Intent(RecebePerguntasQuizActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(RecebePerguntasQuizActivity.this, TelaFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(RecebePerguntasQuizActivity.this, PersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(RecebePerguntasQuizActivity.this, QuizActivity.class));
                break;
        }
    }
}
