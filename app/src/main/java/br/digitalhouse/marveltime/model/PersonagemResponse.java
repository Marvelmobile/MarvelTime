package br.digitalhouse.marveltime.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;

@Entity(tableName = "response")
public class PersonagemResponse {
 @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
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
}
