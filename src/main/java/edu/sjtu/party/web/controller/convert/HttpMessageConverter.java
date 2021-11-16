package edu.sjtu.party.web.controller.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.sjtu.party.dao.hibernate.PersistentEnum;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Date;

public class HttpMessageConverter extends GsonHttpMessageConverter {

    private static final TypeAdapter<Date> dateTypeAdapter = new TypeAdapter<Date>() {

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            if (value == null)
                out.nullValue();
            else
                out.value(value.getTime() / 1000);
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            if (JsonToken.NULL == token) {
                in.nextNull();
                return null;
            }
            return new Date(in.nextLong() * 1000);
        }

    };

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date.class, dateTypeAdapter)
            .registerTypeHierarchyAdapter(PersistentEnum.class, PersistentEnumSerializerDeserializer.INSTANCE)
            .create();

    public HttpMessageConverter() {
        super();
        this.setGson(gson);
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        /*
        if (o instanceof ResponseEntity) {
            Charset charset = this.getContentTypeCharset(outputMessage.getHeaders().getContentType());
            Type type = ((ResponseEntity) o).getType();
            StreamUtils.copy(type == null ? gson.toJson(o) : gson.toJson(o, type), charset, outputMessage.getBody());

        }
        */
        super.writeInternal(o, type, outputMessage);
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        return contentType != null && contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
    }

}
