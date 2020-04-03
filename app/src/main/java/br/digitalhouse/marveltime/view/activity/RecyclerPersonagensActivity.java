package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerPersonagens;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerPersonagensActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ArrayList<CardModel> listaCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_personagens);
        ButterKnife.bind(this);
        initListas();
    }

    public void initListas() {
        listaCards.add(new CardModel(id, R.drawable.tony, R.string.homem_de_ferro));
        listaCards.add(new CardModel(id, R.drawable.capita, R.string.capita));
        listaCards.add(new CardModel(id, R.drawable.gamorra, R.string.gamora));
        listaCards.add(new CardModel(id, R.drawable.thorx, R.string.thor));
        listaCards.add(new CardModel(id, R.drawable.dr, R.string.doutor_estranho));
        listaCards.add(new CardModel(id, R.drawable.panter, R.string.pantera_negra));

        initAdapter();
    }

    public void initAdapter() {
        recycler = findViewById(R.id.recycler_view_personagens);
        AdapterRecyclerPersonagens adapter = new AdapterRecyclerPersonagens(listaCards, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
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

