package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.view.Interfaces.OnClickListenerPersonagem;
import br.digitalhouse.marveltime.viewmodel.MarvelViewModel;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerPersonagens;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class RecyclerPersonagensActivity extends AppCompatActivity implements OnClickListenerPersonagem {
    private RecyclerView recycler;
    private AdapterRecyclerPersonagens adapter;
    private List<PersonagemResult> personagemResultsLista = new ArrayList<>();
    private Integer offset = 0;
    private ProgressBar progressBar;
    private MarvelViewModel marvelViewModel = new MarvelViewModel(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_personagens);
        ButterKnife.bind(this);
        progressBar = findViewById(R.id.progress_bar);
        marvelViewModel.getPersongens(offset);
        marvelViewModel.getPersonagensLista().observe(this, personagemResults -> {
            adapter.atualizaLista(personagemResults);
        });

        marvelViewModel.getLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        recycler = findViewById(R.id.recycler_view_personagens);
        adapter = new AdapterRecyclerPersonagens(personagemResultsLista, this);
        recycler.setAdapter(adapter);
        setScrollView();
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
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
                startActivity(new Intent(RecyclerPersonagensActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(RecyclerPersonagensActivity.this, RecyclerFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(RecyclerPersonagensActivity.this, RecyclerPersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(RecyclerPersonagensActivity.this, RecyclerQuizActivity.class));
                break;
        }
    }

    @Override
    public void click(PersonagemResult personagem) {
        Intent intent = new Intent(RecyclerPersonagensActivity.this, PersonagensTelaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PERSONAGEM_KEY, personagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setScrollView() {
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = lastVisible + 5 >= totalItemCount;

                if (totalItemCount > 0 && ultimoItem) {
                    offset+=20;
                    marvelViewModel.getPersongens(offset);
                }
            }
        });
    }
}

