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
import com.google.android.material.snackbar.Snackbar;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.util.Helper;
import br.digitalhouse.marveltime.view.Interfaces.OnClickFavoritos;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerFavoritos;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.viewmodel.FirebaseViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class RecyclerFavoritosActivity extends AppCompatActivity implements OnClickFavoritos {
    private List<Favoritos> listaFvoritos = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterRecyclerFavoritos adapter;
    private FirebaseViewModel viewModel;
    private ImageView imageViewSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_favoritos);
        getWindow().setBackgroundDrawableResource(R.drawable.background_padrao);
        ButterKnife.bind(this);
        initViews();

        viewModel.carregarFavoritoFirebase();
        viewModel.liveDatafavorito.observe(this, favoritos -> adapter.atualizaLista(favoritos));
        imageViewSair.setOnClickListener(v -> Helper.logout(this));
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view_favoritos);
        imageViewSair = findViewById(R.id.img_sair_fav);
        adapter = new AdapterRecyclerFavoritos(listaFvoritos, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
    }

    public void removeFavoritoClickListener(Favoritos favorito) {
        if (favorito != null){
            viewModel.deletarFavoritoFirebase(favorito);
            viewModel.favoritado.observe(this, favoritos -> adapter.removeItem(favorito));
            Snackbar snackbar = Snackbar.make(recyclerView, R.string.desfavoritado, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.GREEN);
            snackbar.show();
        }
    }

    public void abrirFavoritoClickListener(Favoritos favoritos) {
        if (favoritos.getPersonagemResult() != null) {
            Intent intent = new Intent(RecyclerFavoritosActivity.this, PersonagensTelaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(PERSONAGEM_KEY, favoritos.getPersonagemResult());
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (favoritos.getCardModelquestao() != null) {
            Intent intent = new Intent(RecyclerFavoritosActivity.this, RecebePerguntasQuizActivity.class);
            intent.putExtra(CHAVE_NOME, Helper.buscaChaveQuiz(favoritos.getCardModelquestao().getNome()));
            startActivity(intent);
        }
    }

    @BindView(R.id.tapBarMenu)
    public TapBarMenu tapBarMenu;

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({R.id.item1, R.id.item2, R.id.item3, R.id.item4})
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                startActivity(new Intent(RecyclerFavoritosActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(RecyclerFavoritosActivity.this, RecyclerFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(RecyclerFavoritosActivity.this, RecyclerPersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(RecyclerFavoritosActivity.this, RecyclerQuizActivity.class));
                break;
        }
    }
}