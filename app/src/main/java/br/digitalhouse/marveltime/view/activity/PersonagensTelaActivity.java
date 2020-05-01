package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.model.Url;
import br.digitalhouse.marveltime.viewmodel.MarvelViewModel;
import de.hdodenhof.circleimageview.CircleImageView;
import static br.digitalhouse.marveltime.util.Constantes.IMAGEM_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class PersonagensTelaActivity extends AppCompatActivity {
    private CircleImageView imagemPersonagem;
    private TextView descricaoPersonagem;
    private PersonagemResult personagemResult;
    private TextView nomePersonagem;
    private ImageView share_Personagem;
    private ImageView imgFavorito;
    private MarvelViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagens_tela_activity);
        initView();

        if (getIntent() != null){
            Bundle bundle = getIntent().getExtras();           
            personagemResult = bundle.getParcelable(PERSONAGEM_KEY);            
            descricaoPersonagem.setText(personagemResult.getDescription());
            nomePersonagem.setText(personagemResult.getName());
            String imageURL = personagemResult.getThumbnail().getPath().replace("http://", "https://");
            Picasso.get().load(imageURL+"."+personagemResult.getThumbnail().getExtension()).into(imagemPersonagem);

            clickBtnShared();

            imgFavorito.setOnClickListener(v -> salvarFavorito(personagemResult));
        }

        imagemPersonagem.setOnClickListener(view -> {
            Intent intent = new Intent(PersonagensTelaActivity.this, ImagemPersonagemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(IMAGEM_KEY, personagemResult);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        viewModel.favoritado.observe(this, favoritos -> {
            if (favoritos != null){
                Snackbar snackbar = Snackbar.make(imgFavorito, favoritos.getPersonagemResult().getName() + " " + getString(R.string.favoritado), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.GREEN);
                snackbar.show();
            }
        });

        viewModel.liveDataErro.observe(this, error -> {
            Snackbar snackbar = Snackbar.make(imgFavorito, error, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        });
    }

    private void salvarFavorito(PersonagemResult personagemResult) {
        Favoritos favoritos = new Favoritos();
        favoritos.setPersonagemResult(personagemResult);
        viewModel.salvarFavorito(favoritos);
    }

    private void initView(){
        imagemPersonagem = findViewById(R.id.imagem_personagem_historia);
        descricaoPersonagem = findViewById(R.id.texto_historia);
        nomePersonagem = findViewById(R.id.textView_nomePersonagem);
        share_Personagem = findViewById(R.id.share_resultado);
        imgFavorito = findViewById(R.id.favorito_personagem);
        viewModel = ViewModelProviders.of(this).get(MarvelViewModel.class);
    }

    private void shareMarvel(PersonagemResult personagemResult) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,  getString(R.string.marvel)+"\n"+ getLinkPersonagem(personagemResult));
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, null));
    }

    private String getLinkPersonagem(PersonagemResult personagemResult) {
       if(personagemResult.getUrls()!=null){
            for (Url url : personagemResult.getUrls()){
                if(url.getType().equalsIgnoreCase("wiki")){
                    return url.getUrl();
                }
            }
            return  personagemResult.getUrls().get(0).getUrl();
       }else{
           return personagemResult.getThumbnail().getPath().concat(".").concat(personagemResult.getThumbnail().getExtension());
       }
    }

    private void clickBtnShared() {
        share_Personagem.setOnClickListener(v -> shareMarvel(personagemResult));
    }
}