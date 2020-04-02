package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.PersonagemResult;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderPersonagens extends RecyclerView.ViewHolder {
    public CircleImageView image;
    public TextView texto;

    public ViewHolderPersonagens(@NonNull View itemView) {
        super(itemView);
        image= itemView.findViewById(R.id.imagem_personagens);
        texto= itemView.findViewById(R.id.texto_personagens);
    }

    public void onBind(PersonagemResult personagemResult) {
        texto.setText(personagemResult.getName());
        Picasso.get().load(personagemResult.getThumbnail().getPath() + "." + personagemResult.getThumbnail().getExtension()).into(image);
    }
}
