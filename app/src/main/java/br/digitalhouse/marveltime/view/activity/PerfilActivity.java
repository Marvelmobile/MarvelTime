package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import br.digitalhouse.marveltime.viewmodel.FirebaseViewModel;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_IMG_PROFILE;
import static br.digitalhouse.marveltime.util.Constantes.PERMISSION_CODE;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout tivEmail;
    private CircleImageView imgFotoPerfil;
    private Button bntSalvar;
    private InputStream stream = null;
    private TextInputLayout tivNome;
    private ProgressBar progressBar;
    private FirebaseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getWindow().setBackgroundDrawableResource(R.drawable.background_menu_principal);
        initView();
        loadEmail();
        loadImage();

        imgFotoPerfil.setOnClickListener(v -> captureImage());
        bntSalvar.setOnClickListener(v -> salvarPerfil());

        viewModel.liveDataErro.observe(this, error -> {
            Snackbar snackbar = Snackbar.make(tivEmail, error, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        });

        viewModel.getLoading.observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void salvarPerfil() {
        if(Helper.verificaConexaoComInternet(this)) {
            if (!Helper.isEmptyString(Helper.getString(tivNome))) {
                viewModel.salvarNomeFirebase(Helper.getString(tivNome));
            }
            else {
                Helper.notificacao(this, getString(R.string.preencher_campos));
                return;
            }

            if (stream != null){
                viewModel.salvarImagemFirebase(stream);
                this.stream = null;
            } else {
                Helper.notificacao(this, getString(R.string.update_sucss));
            }
        } else Helper.notificacao(this, getString(R.string.erro_conexao));
    }

    private void loadEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tivEmail.getEditText().setText(user.getEmail());
            tivEmail.getEditText().setEnabled(false);
            tivNome.getEditText().setText(user.getDisplayName());
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textEmail);
        imgFotoPerfil = findViewById(R.id.imageviewUsuario);
        bntSalvar = findViewById(R.id.btnSalvar);
        tivNome = findViewById(R.id.textNomeCompleto);
        progressBar = findViewById(R.id.progressbarPerfil);
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
    }

    private void loadImage() {
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(Helper.getIdUsuario(this) + CHAVE_IMG_PROFILE);

        storage.getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get().load(uri).into(imgFotoPerfil));
    }

    private void captureImage() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
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
                        carregaImagemTemp(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void carregaImagemTemp(File file) {
        InputStream streamTemp = null;
        Bitmap bitmap;

        try {
            streamTemp = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(streamTemp);
            imgFotoPerfil.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (streamTemp != null) {
                try {
                    streamTemp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}