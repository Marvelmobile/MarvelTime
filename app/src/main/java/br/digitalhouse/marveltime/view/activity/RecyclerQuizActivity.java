package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import br.digitalhouse.marveltime.util.Helper;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.view.Interfaces.OnClickQuiz;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerQuiz;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.viewmodel.FirebaseViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;

public class RecyclerQuizActivity extends AppCompatActivity implements OnClickQuiz {
    private List<CardModel> listaCardQuiz = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterRecyclerQuiz adapterRecyclerQuiz;
    private FirebaseViewModel viewModel;
    private ImageView imageViewSair;

    @BindView(R.id.tapBarMenu)
    public TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_quiz);
        getWindow().setBackgroundDrawableResource(R.drawable.background_padrao);
        ButterKnife.bind(this);
        initViews();

        imageViewSair.setOnClickListener(v ->  Helper.logout(this));

        viewModel.favoritado.observe(this, favoritos -> {
            if (favoritos != null) {
                Snackbar snackbar = Snackbar.make(recyclerView, "Quiz " + getString(R.string.favoritado), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.GREEN);
                snackbar.show();
            }
        });

        viewModel.liveDataErro.observe(this, error -> {
            Snackbar snackbar = Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        });
    }

    private void initViews() {
        initCardModel();
        imageViewSair = findViewById(R.id.img_sair_quiz);
        recyclerView = findViewById(R.id.recycler_view);
        adapterRecyclerQuiz = new AdapterRecyclerQuiz(listaCardQuiz, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapterRecyclerQuiz);
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
    }

    private void initCardModel() {
        listaCardQuiz.add(new CardModel(R.drawable.img_homem_aranha, R.string.quiz_homem_aranha));
        listaCardQuiz.add(new CardModel(R.drawable.img_thor_ultimate, R.string.quiz_thor));
        listaCardQuiz.add(new CardModel(R.drawable.img_homem_ferro, R.string.quiz_homem_ferro));
        listaCardQuiz.add(new CardModel(R.drawable.img_capitao_america, R.string.quiz_capitao));
    }

    @Override
    public void clickAbreQuiz(CardModel cardModel) {
        Intent intent = new Intent(this, RecebePerguntasQuizActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(CHAVE_NOME, Helper.buscaChaveQuiz(cardModel.getNome()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void clickFavoritar(CardModel cardModel) {
        salvarFavorito(cardModel);
    }

    private void salvarFavorito(CardModel cardQuestao) {
        Favoritos favoritos = new Favoritos();
        favoritos.setCardModelquestao(cardQuestao);
        viewModel.salvarFavoritoFirebase(favoritos);
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
                startActivity(new Intent(RecyclerQuizActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(RecyclerQuizActivity.this, RecyclerFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(RecyclerQuizActivity.this, RecyclerPersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(RecyclerQuizActivity.this, RecyclerQuizActivity.class));
                break;
        }
    }
}