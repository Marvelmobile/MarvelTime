package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.util.Helper;
import br.digitalhouse.marveltime.view.Interfaces.OnClickFavoritos;
import br.digitalhouse.marveltime.view.adapter.AdapterRecyclerFavoritos;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class RecyclerFavoritosActivity extends AppCompatActivity implements OnClickFavoritos {
    private List<Favoritos> listaFvoritos = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private AdapterRecyclerFavoritos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_favoritos);
        ButterKnife.bind(this);
        initViews();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("marvelTimeTeste" + "/favoritos");

        carregarFavoritos();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view_favoritos);
        adapter = new AdapterRecyclerFavoritos(listaFvoritos, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void carregarFavoritos() {
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Favoritos> favoritos = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Favoritos result = child.getValue(Favoritos.class);
                    favoritos.add(result);
                }
                adapter.update(favoritos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void removeFavoritoClickListener(Favoritos result) {
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (result.getPersonagemResult() != null && child.getValue(Favoritos.class).getPersonagemResult() != null) {
                        if (child.getValue(Favoritos.class).getPersonagemResult().getId().equals(result.getPersonagemResult().getId())){
                            child.getRef().removeValue();
                            adapter.removeItem(result);
                        }
                    }else if (result.getCardModelquestao() != null && child.getValue(Favoritos.class).getCardModelquestao() != null) {
                        if (child.getValue(Favoritos.class).getCardModelquestao().getNome() == (result.getCardModelquestao().getNome())){
                            child.getRef().removeValue();
                            adapter.removeItem(result);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
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