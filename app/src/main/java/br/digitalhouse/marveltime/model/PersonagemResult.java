package br.digitalhouse.marveltime.model;
import com.google.gson.annotations.Expose;
public class PersonagemResult {
    @Expose
    private String description;
    @Expose
    private Long id;
    @Expose
    private String modified;
    @Expose
    private String name;
    @Expose
    private String resourceURI;
    @Expose
    private PersonagemImagem thumbnail;

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
}
