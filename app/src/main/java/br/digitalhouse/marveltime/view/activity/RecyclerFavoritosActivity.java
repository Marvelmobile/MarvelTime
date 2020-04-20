package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerFavoritos;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerFavoritosActivity extends AppCompatActivity {
    private ArrayList<Favoritos> favoritos = new ArrayList<>();

    private void initBitmaps() {
        favoritos.add(new Favoritos(new CardModel(R.drawable.capitaoamerica, R.string.quiz_capitao)));
        favoritos.add(new Favoritos(new CardModel(R.drawable.minihferro, R.string.quiz_homem_ferro)));
        favoritos.add(new Favoritos(new CardModel(R.drawable.miniaranha, R.string.quiz_homem_aranha)));
        favoritos.add(new Favoritos(new CardModel(R.drawable.thor, R.string.quiz_thor)));
        initReclycer();
    }

    private void initReclycer() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_favoritos);
        AdapterRecyclerFavoritos adaptador = new AdapterRecyclerFavoritos(favoritos, this);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
   }

    @BindView(R.id.tapBarMenu)
    public TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_favoritos);
        ButterKnife.bind(this);
        initBitmaps();
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