package br.digitalhouse.marveltime.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.fragment.PerguntasQuizFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecebePerguntasQuizActivity extends AppCompatActivity{
    public static String nome;
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);
        Intent intent = getIntent();
        nome = intent.getExtras().getString("NOME");
        replaceFragments(R.id.container, new PerguntasQuizFragment());
        ButterKnife.bind(this);
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
                startActivity(new Intent(RecebePerguntasQuizActivity.this, RecyclerFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(RecebePerguntasQuizActivity.this, RecyclerPersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(RecebePerguntasQuizActivity.this, RecyclerQuizActivity.class));
                break;
        }
    }
}
