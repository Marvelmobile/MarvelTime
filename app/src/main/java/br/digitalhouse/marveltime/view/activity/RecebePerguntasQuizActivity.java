package br.digitalhouse.marveltime.view.activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
<<<<<<< HEAD:app/src/main/java/br/digitalhouse/marveltime/Activitys/RecebePerguntasQuizActivity.java
import br.digitalhouse.marveltime.Fragments.PerguntasQuizFragment;
import br.digitalhouse.marveltime.view.Interfaces.HelperQuiz;
=======
>>>>>>> origin/pre_epic:app/src/main/java/br/digitalhouse/marveltime/view/activity/RecebePerguntasQuizActivity.java
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.fragment.PerguntasQuizFragment;
import br.digitalhouse.marveltime.view.fragment.ResultadoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecebePerguntasQuizActivity extends AppCompatActivity implements HelperQuiz {
    public static String nome;
    private br.digitalhouse.marveltime.view.fragment.ResultadoFragment.ResultadoFragment fragmentResultado;
    private PerguntasQuizFragment fragmentQuiz;
    int mcorreto = 0, merrado = 0;
    String mtitulo= " ";
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);
        fragmentResultado = new ResultadoFragment.ResultadoFragment();
        fragmentQuiz = new PerguntasQuizFragment();
        Intent intent = getIntent();
        nome = intent.getExtras().getString("NOME");
        ButterKnife.bind(this);
        replaceFragments(R.id.container, fragmentQuiz);
    }

<<<<<<< HEAD:app/src/main/java/br/digitalhouse/marveltime/Activitys/RecebePerguntasQuizActivity.java
    private void replaceFragments(int container, Fragment fragment) {
=======
    private void replaceFragments(int container, Fragment fragment){
>>>>>>> origin/pre_epic:app/src/main/java/br/digitalhouse/marveltime/view/activity/RecebePerguntasQuizActivity.java
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