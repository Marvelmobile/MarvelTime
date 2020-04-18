package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
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
    private MarvelViewModel marvelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_personagens);
        ButterKnife.bind(this);
        initViews();

        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(adapter);
        setScrollView();

        marvelViewModel.getPersongens(offset);
        marvelViewModel.personagemLista.observe(this, personagemResults -> adapter.atualizaLista(personagemResults));


        marvelViewModel.getLoading.observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        marvelViewModel.liveDataErro.observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_LONG).show());
    }

    private void initViews() {
        recycler = findViewById(R.id.recycler_view_personagens);
        progressBar = findViewById(R.id.progress_bar);
        adapter = new AdapterRecyclerPersonagens(personagemResultsLista, this);
        marvelViewModel = ViewModelProviders.of(this).get(MarvelViewModel.class);
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
}