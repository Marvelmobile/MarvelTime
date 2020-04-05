package br.digitalhouse.marveltime.Activitys;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import br.digitalhouse.marveltime.Fragments.PerguntasQuizFragment;
import br.digitalhouse.marveltime.Fragments.ResultadoFragment;
import br.digitalhouse.marveltime.Interfaces.HelperQuiz;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecebePerguntasQuizActivity extends AppCompatActivity implements HelperQuiz {
    public static String nome;
    private ResultadoFragment fragmentResultado;
    private PerguntasQuizFragment fragmentQuiz;
    int mcorreto = 0, merrado = 0;
    String mtitulo= " ";
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);
        fragmentResultado = new ResultadoFragment();
        fragmentQuiz = new PerguntasQuizFragment();
        Intent intent = getIntent();
        nome = intent.getExtras().getString("NOME");
        ButterKnife.bind(this);
        replaceFragments(R.id.container, fragmentQuiz);
    }

    private void replaceFragments(int container, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({R.id.item1, R.id.item2, R.id.item3, R.id.item4})
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

    @Override
    public void correto(int correto) {
        mcorreto = correto;
    }

    @Override
    public void errado(int errado) {
        merrado = errado;
    }

    @Override
    public void troca() {
        Bundle bundle = new Bundle();
        bundle.putInt("correto", mcorreto);
        bundle.putInt("errado", merrado);
        bundle.putString("titulo", mtitulo);
        fragmentResultado.setArguments(bundle);
        replaceFragments(R.id.container, fragmentResultado);
    }
    @Override
    public void titulo(String titulo) {
        mtitulo=titulo;
    }
}