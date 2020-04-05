package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.PersonagemResult;

public class ViewHolderPersonagens extends RecyclerView.ViewHolder {
    public ImageView imagemPersonagem;
    public TextView nomePersonagem;

    public ViewHolderPersonagens(@NonNull View itemView) {
        super(itemView);
        imagemPersonagem = itemView.findViewById(R.id.imagem_personagens);
        nomePersonagem = itemView.findViewById(R.id.texto_personagens);
    }

    public void onBind(PersonagemResult personagemResult) {
        String imageURL = personagemResult.getThumbnail()
                .getPath().replace("http://", "https://");
        nomePersonagem.setText(personagemResult.getName());
        Picasso.get().load(imageURL+ "." + personagemResult.getThumbnail().getExtension()).into(imagemPersonagem);
    }
}
