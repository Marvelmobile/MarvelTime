package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import static br.digitalhouse.marveltime.util.Constantes.PERMISSION_CODE;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout tivEmail;
    private FirebaseUser user;
    private CircleImageView imgFotoPerfil;
    private Button bntSalvar;
    private InputStream stream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initView();
        loadEmail();

        imgFotoPerfil.setOnClickListener(v -> captureImage());
        bntSalvar.setOnClickListener(v -> salvarImagemFirebase(stream, "imagem-perfil"));
    }

    private void loadEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            tivEmail.getEditText().setText(email);
            tivEmail.getEditText().setEnabled(false);
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textEmail);
        imgFotoPerfil = findViewById(R.id.imageviewUsuario);
        bntSalvar = findViewById(R.id.btnSalvar);
    }

    private void captureImage() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    private void salvarImagemFirebase(InputStream stream, String name) {
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(Helper.getIdUsuario(getApplicationContext()) + "/image/profile/" + name);

        if (stream != null) {
            UploadTask uploadTask = storage.putStream(stream);
            loadImageFromFirebase();

            uploadTask.addOnSuccessListener(taskSnapshot -> {
            }).addOnFailureListener(e -> {
                Snackbar snackbar = Snackbar.make(tivEmail, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.RED);
                snackbar.show();
            });
        }
    }
    private void loadImageFromFirebase() {
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(Helper.getIdUsuario(getApplicationContext()) + "/image/profile/imagem-perfil");

        storage.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Picasso.get().load(uri).rotate(90).into(imgFotoPerfil);
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        captureImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                for (File file : imageFiles) {
                    try {
                        stream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}