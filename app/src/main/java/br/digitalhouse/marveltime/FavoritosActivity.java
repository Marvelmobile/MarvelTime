package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoritosActivity extends AppCompatActivity {

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        ButterKnife.bind(this);
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
                //Home
                startActivity(new Intent(FavoritosActivity.this, MainActivity.class));
                break;
            case R.id.item2:
                //Favoritos
                startActivity(new Intent(FavoritosActivity.this, FavoritosActivity.class));
                break;
            case R.id.item3:
                //Personagens
                startActivity(new Intent(FavoritosActivity.this, FavoritosActivity.class));
                break;
            case R.id.item4:
                //Quiz
                startActivity(new Intent(FavoritosActivity.this, FavoritosActivity.class));
                break;
        }
    }
}
