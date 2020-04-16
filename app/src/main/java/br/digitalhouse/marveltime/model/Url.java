package br.digitalhouse.marveltime.model;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;

@Entity(tableName = "url")
public class Url implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @Expose
    private String type;
    @Expose
    private String url;

    protected Url(Parcel in) {
        id = in.readLong();
        type = in.readString();
        url = in.readString();
    }

    public Url() {

    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(type);
        parcel.writeString(url);
    }
}
