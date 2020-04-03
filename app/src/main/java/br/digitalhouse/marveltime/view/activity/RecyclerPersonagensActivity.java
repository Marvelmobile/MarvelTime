package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class RecyclerPersonagensActivity extends AppCompatActivity implements OnClickListenerPersonagem {
    private RecyclerView recycler;
    private AdapterRecyclerPersonagens adapter;
    private List<PersonagemResult> personagemResultsLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_personagens);
        ButterKnife.bind(this);

        MarvelViewModel marvelViewModel = new MarvelViewModel(getApplication());
        marvelViewModel.getPersongens();
        marvelViewModel.getPersonagensLista().observe(this, personagemResults -> {
            adapter.atualizaLista(personagemResults);
        });

        recycler = findViewById(R.id.recycler_view_personagens);
        adapter = new AdapterRecyclerPersonagens(personagemResultsLista, this);
        recycler.setAdapter(adapter);
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
    }
}

