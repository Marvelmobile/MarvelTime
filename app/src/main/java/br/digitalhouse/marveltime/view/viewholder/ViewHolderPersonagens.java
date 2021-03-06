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
    private ImageView imagemPersonagem;
    private TextView nomePersonagem;

    public ViewHolderPersonagens(@NonNull View itemView) {
        super(itemView);
        imagemPersonagem = itemView.findViewById(R.id.imagem_personagens);
        nomePersonagem = itemView.findViewById(R.id.texto_personagens);
    }

    public void onBind(PersonagemResult personagemResult) {
        nomePersonagem.setText(personagemResult.getName());
        String imageURL = personagemResult.getThumbnail().getPath().replace("http://", "https://");
        Picasso.get().load(imageURL+ "." + personagemResult.getThumbnail().getExtension()).into(imagemPersonagem);
    }
}