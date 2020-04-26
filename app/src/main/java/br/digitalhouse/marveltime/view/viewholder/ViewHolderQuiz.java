package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.digitalhouse.marveltime.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderQuiz extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView texto;

    public ViewHolderQuiz(@NonNull View itemView) {
        super(itemView);
        image= itemView.findViewById(R.id.imagem_favoritos);
        texto= itemView.findViewById(R.id.textViewfavoritos);
    }
}