package br.digitalhouse.marveltime.model;
import com.google.gson.annotations.Expose;

public class PersonagemImagem {

    @Expose
    private String extension;
    @Expose
    private String path;

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

}
