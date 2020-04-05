package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import br.digitalhouse.marveltime.R;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonagensTelaActivity extends AppCompatActivity {
    private CircleImageView personagem;
    private TextView nomeP;
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagens_tela_activity);
        Intent intent = getIntent();
        int nome = intent.getExtras().getInt("NOME");
        int imagem = intent.getExtras().getInt("IMAGEM");
        initExtras();

        personagem.setImageResource(imagem);
        nomeP.setText(nome);
    }

    private void initExtras() {
        personagem = findViewById(R.id.imagem_personagem_historia);
        nomeP = findViewById(R.id.resultado);
    }

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