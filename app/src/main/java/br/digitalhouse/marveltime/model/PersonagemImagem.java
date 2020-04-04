package br.digitalhouse.marveltime.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;


@Entity(tableName = "imagem")
public class PersonagemImagem implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @Expose
    private String extension;
    @Expose
    private String path;

    public PersonagemImagem() { }

    protected PersonagemImagem(Parcel in) {
        extension = in.readString();
        path = in.readString();
    }


    public static final Creator<PersonagemImagem> CREATOR = new Creator<PersonagemImagem>() {
        @Override
        public PersonagemImagem createFromParcel(Parcel in) {
            return new PersonagemImagem(in);
        }

        @Override
        public PersonagemImagem[] newArray(int size) {
            return new PersonagemImagem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extension);
        dest.writeString(path);
    }
}


