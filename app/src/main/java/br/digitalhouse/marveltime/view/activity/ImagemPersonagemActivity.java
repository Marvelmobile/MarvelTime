package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.PersonagemResult;
import static br.digitalhouse.marveltime.util.Constantes.IMAGEM_KEY;

public class ImagemPersonagemActivity extends AppCompatActivity {
    private ImageView civImagemDetalhe;
    private Button bntFechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background_padrao);
        setContentView(R.layout.activity_imagem_personagem);
        initViews();
        if (getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            PersonagemResult personagemResult = bundle.getParcelable(IMAGEM_KEY);
            String imageURL = personagemResult.getThumbnail()
                    .getPath().replace(getString(R.string.http), getString(R.string.https));
            Picasso.get().load(imageURL+"."
                    + personagemResult.getThumbnail().getExtension()).into(civImagemDetalhe);
        }
        bntFechar.setOnClickListener(view -> finish());
    }

    private void initViews(){
        civImagemDetalhe = findViewById(R.id.circleImageViewDetalhe);
        bntFechar = findViewById(R.id.buttonFechar);
    }
}