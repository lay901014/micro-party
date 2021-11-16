package edu.sjtu.party.web.controller.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.sjtu.json.ResponseEntity;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Date;

public class ResponseEntityHttpMessageConverter extends AbstractHttpMessageConverter<ResponseEntity> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

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
            if (in != null)
                return new Date(in.nextLong() * 1000);
            else
                return null;
        }

    };

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date.class, dateTypeAdapter)
            .create();

    public ResponseEntityHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    protected boolean supports(Class<?> clazz) {
        return ResponseEntity.class.isAssignableFrom(clazz);
    }

    protected ResponseEntity readInternal(Class<? extends ResponseEntity> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            return gson.fromJson(new InputStreamReader(inputMessage.getBody(), "UTF-8"), clazz);
        } catch (JsonSyntaxException e) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    protected void writeInternal(ResponseEntity entity, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Charset charset = this.getContentTypeCharset(outputMessage.getHeaders().getContentType());
        Type type = entity.getType();
        StreamUtils.copy(type == null ? gson.toJson(entity) : gson.toJson(entity, type), charset, outputMessage.getBody());
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        return contentType != null && contentType.getCharSet() != null ? contentType.getCharSet() : DEFAULT_CHARSET;
    }
}