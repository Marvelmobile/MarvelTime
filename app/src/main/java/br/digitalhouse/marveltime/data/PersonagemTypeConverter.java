package br.digitalhouse.marveltime.data;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import br.digitalhouse.marveltime.model.Data;
import br.digitalhouse.marveltime.model.PersonagemImagem;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.model.Url;

public class PersonagemTypeConverter {
    @TypeConverter
    public Object fromObject(String value) {
        Type listType = (Type) new TypeToken<Object>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromJsonObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    
    @TypeConverter
    public List<PersonagemResult> fromListPersonagem(String value) {
        Type listType = (Type) new TypeToken<List<PersonagemResult>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }
    
    @TypeConverter
    public String fromListPersonagemObject(List<PersonagemResult> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public PersonagemImagem fromImagem(String value) {
        Type listType = (Type) new TypeToken<PersonagemImagem>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromJsonImagem(PersonagemImagem personagemImagem) {
        Gson gson = new Gson();
        return gson.toJson(personagemImagem);
    }

    @TypeConverter
    public List<PersonagemResponse> fromListResponse(String value) {
        Type listType = (Type) new TypeToken<List<PersonagemResponse>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListResponseObject(List<PersonagemResponse> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Data> fromListData(String value) {
        Type listType = (Type) new TypeToken<List<Data>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListDataObject(List<Data> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Url> fromListUrl(String value) {
        Type listType = (Type) new TypeToken<List<Url>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListUrlObject(List<Url> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}