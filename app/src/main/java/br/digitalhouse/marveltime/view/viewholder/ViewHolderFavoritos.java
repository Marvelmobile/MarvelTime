package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;

public class ViewHolderFavoritos extends RecyclerView.ViewHolder {
    public ImageView imageFavoritado;
    public TextView texto;
    public ImageView desfavoritar;
    public Button bntAbrirFavorito;

    public ViewHolderFavoritos(@NonNull View itemView) {
        super(itemView);
        desfavoritar = itemView.findViewById(R.id.desfavoritar);
        imageFavoritado = itemView.findViewById(R.id.imagem_fav);
        texto = itemView.findViewById(R.id.textFav);
        bntAbrirFavorito = itemView.findViewById(R.id.proximo_fav);
    }

    public void bind(Favoritos favoritos) {
        if (favoritos.getCardModelquestao() != null) {
            imageFavoritado.setImageResource(favoritos.getCardModelquestao().getImagem());
            texto.setText(favoritos.getCardModelquestao().getNome());
        }

        if (favoritos.getPersonagemResult() != null) {
            String imageURL = favoritos.getPersonagemResult().getThumbnail().getPath().replace("http://", "https://");
            Picasso.get().load(imageURL + "." + favoritos.getPersonagemResult().getThumbnail().getExtension()).into(imageFavoritado);
            texto.setText(favoritos.getPersonagemResult().getName());
        }
    }
}