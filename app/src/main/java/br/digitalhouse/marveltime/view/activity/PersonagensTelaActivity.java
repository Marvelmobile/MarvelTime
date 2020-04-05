package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.PersonagemResult;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class PersonagensTelaActivity extends AppCompatActivity {
    private CircleImageView imagemPersonagem;
    private TextView descricaoPersonagem;
    private PersonagemResult personagemResult;
    private TextView nomePersonagem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagens_tela_activity);
        initView();

        if (getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            personagemResult = bundle.getParcelable(PERSONAGEM_KEY);
            descricaoPersonagem.setText(personagemResult.getDescription());
            String imageURL = personagemResult.getThumbnail()
                    .getPath().replace("http://", "https://");
            Picasso.get().load(imageURL+"."
                    +personagemResult.getThumbnail().getExtension()).into(imagemPersonagem);
            nomePersonagem.setText(personagemResult.getName());
        }
    }

    private void initView(){
        imagemPersonagem = findViewById(R.id.imagem_personagem_historia);
        descricaoPersonagem = findViewById(R.id.texto_historia);
        nomePersonagem = findViewById(R.id.textView_nomePersonagem);
    }

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                startActivity(new Intent(PersonagensTelaActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(PersonagensTelaActivity.this, RecyclerFavoritosActivity.class));
                break;
            case R.id.item3:
                startActivity(new Intent(PersonagensTelaActivity.this, RecyclerPersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(PersonagensTelaActivity.this, RecyclerQuizActivity.class));
                break;
        }
    }
}