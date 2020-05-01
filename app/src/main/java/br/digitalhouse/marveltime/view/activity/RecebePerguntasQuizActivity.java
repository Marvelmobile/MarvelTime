package br.digitalhouse.marveltime.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.Interfaces.HelperQuiz;
import br.digitalhouse.marveltime.view.fragment.PerguntasQuizFragment;
import br.digitalhouse.marveltime.view.fragment.ResultadoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;

public class RecebePerguntasQuizActivity extends AppCompatActivity implements HelperQuiz {
    public static String nome;
    private ResultadoFragment fragmentResultado;
    private PerguntasQuizFragment fragmentQuiz;
    private int mcorreto = 0;
    private int merrado = 0;
    private String mtitulo= "";

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);
        getWindow().setBackgroundDrawableResource(R.drawable.background_padrao);
        ButterKnife.bind(this);

        initViews();
        if (getIntent() != null){
            Intent intent = getIntent();
            nome = intent.getExtras().getString(CHAVE_NOME);
        }

        replaceFragments(R.id.container, fragmentQuiz);
    }

    private void initViews() {
        fragmentResultado = new ResultadoFragment();
        fragmentQuiz = new PerguntasQuizFragment();
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
        bundle.putInt(getString(R.string.correto), mcorreto);
        bundle.putInt(getString(R.string.errado), merrado);
        bundle.putString(getString(R.string.titulo), mtitulo);
        fragmentResultado.setArguments(bundle);
        replaceFragments(R.id.container, fragmentResultado);
    }

    @Override
    public void titulo(String titulo) {
        mtitulo=titulo;
    }
}