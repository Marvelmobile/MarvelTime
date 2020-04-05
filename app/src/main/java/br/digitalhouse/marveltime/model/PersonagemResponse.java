package br.digitalhouse.marveltime.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "response")
public class PersonagemResponse implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @Expose
    private String attributionHTML;
    @Expose
    private String attributionText;
    @Expose
    private Long code;
    @Expose
    private String copyright;
    @Expose
    private Data data;
    @Expose
    private String etag;
    @Expose
    private String status;

    public PersonagemResponse() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected PersonagemResponse(Parcel in) {
        attributionHTML = in.readString();
        attributionText = in.readString();
        if (in.readByte() == 0) {
            code = null;
        } else {
            code = in.readLong();
        }
        copyright = in.readString();
        etag = in.readString();
        status = in.readString();
    }

    public static final Creator<PersonagemResponse> CREATOR = new Creator<PersonagemResponse>() {
        @Override
        public PersonagemResponse createFromParcel(Parcel in) {
            return new PersonagemResponse(in);
        }

        @Override
        public PersonagemResponse[] newArray(int size) {
            return new PersonagemResponse[size];
        }
    };

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attributionHTML);
        dest.writeString(attributionText);
        if (code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(code);
        }
        dest.writeString(copyright);
        dest.writeString(etag);
        dest.writeString(status);
    }
}