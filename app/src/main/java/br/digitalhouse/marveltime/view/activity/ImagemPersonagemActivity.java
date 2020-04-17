package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.PersonagemResult;
import de.hdodenhof.circleimageview.CircleImageView;
import static br.digitalhouse.marveltime.util.Constantes.IMAGEM_KEY;

public class ImagemPersonagemActivity extends AppCompatActivity {
    private ImageView civImagemDetalhe;
    private Button bntFechar;
    private PersonagemResult personagemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_personagem);
        initViews();
        if (getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            personagemResult = bundle.getParcelable(IMAGEM_KEY);
            String imageURL = personagemResult.getThumbnail()
                    .getPath().replace("http://", "https://");
            Picasso.get().load(imageURL+"."
                    +personagemResult.getThumbnail().getExtension()).into(civImagemDetalhe);
        }
        bntFechar.setOnClickListener(view -> finish());
    }

    private void initViews(){
        civImagemDetalhe = findViewById(R.id.circleImageViewDetalhe);
        bntFechar = findViewById(R.id.buttonFechar);
    }
}
