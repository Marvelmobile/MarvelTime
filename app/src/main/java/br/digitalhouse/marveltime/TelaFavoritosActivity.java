package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TelaFavoritosActivity extends AppCompatActivity {
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_favoritos);
        replaceFragments(new FavoritosFragment());
        ButterKnife.bind(this);

    }

    public void replaceFragments(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerFavoritos, fragment);
        transaction.commit();
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
                startActivity(new Intent(TelaFavoritosActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(TelaFavoritosActivity.this, TelaFavoritosActivity.class));
                break;
            case R.id.item3:
                 startActivity(new Intent(TelaFavoritosActivity.this, PersonagensActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(TelaFavoritosActivity.this, QuizActivity.class));
                break;
        }
    }
}
