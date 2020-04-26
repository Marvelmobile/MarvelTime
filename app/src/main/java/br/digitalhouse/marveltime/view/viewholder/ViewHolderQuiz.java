package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.CardModel;

public class ViewHolderQuiz extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView texto;
    public ImageView imgViewFavorito;

    public ViewHolderQuiz(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imagem_quiz);
        texto = itemView.findViewById(R.id.textViewQuiz);
        imgViewFavorito = itemView.findViewById(R.id.favoritarQuiz);
    }

    public void bind(CardModel cardModel) {
        image.setImageResource(cardModel.getImagem());
        texto.setText(cardModel.getNome());
    }
}