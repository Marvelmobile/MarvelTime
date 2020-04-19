package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerQuiz;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerQuizActivity extends AppCompatActivity {
    private ArrayList<CardModel> listaCardQuiz = new ArrayList<>();

    private void initCardModel() {
        listaCardQuiz.add(new CardModel(R.drawable.miniaranha, R.string.quiz_homem_aranha));
        listaCardQuiz.add(new CardModel(R.drawable.thor, R.string.quiz_thor));
        listaCardQuiz.add(new CardModel(R.drawable.minihferro, R.string.quiz_homem_ferro));
        listaCardQuiz.add(new CardModel(R.drawable.capitaoamerica, R.string.quiz_capitao));
        initReclycer();
    }
    private void initReclycer(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AdapterRecyclerQuiz adapterRecyclerQuiz = new AdapterRecyclerQuiz(this, listaCardQuiz);
        recyclerView.setAdapter(adapterRecyclerQuiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @BindView(R.id.tapBarMenu)
    public TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_quiz);
        ButterKnife.bind(this);
        initCardModel();
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