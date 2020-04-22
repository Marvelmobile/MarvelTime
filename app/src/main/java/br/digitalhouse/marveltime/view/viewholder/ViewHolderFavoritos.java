package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderFavoritos extends RecyclerView.ViewHolder {
    public CircleImageView imageFavoritado;
    public TextView texto;
    public ImageView desfavoritar;

    public ViewHolderFavoritos(@NonNull View itemView) {
        super(itemView);
        desfavoritar = itemView.findViewById(R.id.desfavoritar);
        imageFavoritado = itemView.findViewById(R.id.imagemFavoritado);
        texto = itemView.findViewById(R.id.textViewfavoritado);
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