package br.digitalhouse.marveltime.view.viewholder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.digitalhouse.marveltime.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderFavoritos extends RecyclerView.ViewHolder {
    public CircleImageView image;
    public TextView texto;
    public ImageView favoritado;

    public ViewHolderFavoritos(@NonNull View itemView) {
        super(itemView);
        favoritado= itemView.findViewById(R.id.favoritado);
        image= itemView.findViewById(R.id.imagem_cardview_quiz);
        texto= itemView.findViewById(R.id.textView1);
    }
}
