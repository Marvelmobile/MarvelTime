package br.digitalhouse.marveltime.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "result")
public class PersonagemResult implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @Expose
    private Long id;
    @Expose
    private String description;
    @Expose
    private String modified;
    @Expose
    private String name;
    @Expose
    private String resourceURI;
    @Expose
    private PersonagemImagem thumbnail;

    public PersonagemResult() {
    }

    public PersonagemResult(Parcel in) {
        description = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        modified = in.readString();
        name = in.readString();
        resourceURI = in.readString();
        thumbnail = in.readParcelable(PersonagemImagem.class.getClassLoader());
    }

    public static final Creator<PersonagemResult> CREATOR = new Creator<PersonagemResult>() {
        @Override
        public PersonagemResult createFromParcel(Parcel in) {
            return new PersonagemResult(in);
        }

        @Override
        public PersonagemResult[] newArray(int size) {
            return new PersonagemResult[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public PersonagemImagem getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(PersonagemImagem thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(modified);
        dest.writeString(name);
        dest.writeString(resourceURI);
        dest.writeParcelable(thumbnail, flags);
    }
}