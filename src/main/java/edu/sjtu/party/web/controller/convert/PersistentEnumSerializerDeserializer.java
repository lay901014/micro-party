package edu.sjtu.party.web.controller.convert;

import com.google.gson.*;
import edu.sjtu.party.dao.hibernate.PersistentEnum;
import edu.sjtu.party.dao.hibernate.PersistentIntegerEnum;
import edu.sjtu.party.dao.hibernate.PersistentStringEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.lang.reflect.Type;

public class PersistentEnumSerializerDeserializer implements JsonSerializer<PersistentEnum>, JsonDeserializer<PersistentEnum> {

    public final static PersistentEnumSerializerDeserializer INSTANCE = new PersistentEnumSerializerDeserializer();

    private static Log log = LogFactory.getLog(PersistentEnumSerializerDeserializer.class);

    private PersistentEnumSerializerDeserializer() {
    }

    public JsonElement serialize(PersistentEnum value, Type type, JsonSerializationContext context) {
        if (value == null) {
            return JsonNull.INSTANCE;
        }
        JsonObject result = new JsonObject();
        Serializable code = value.getEnumCode();
        if (code instanceof String) {
            result.add("code", new JsonPrimitive((String) code));
        } else if (code instanceof Number) {
            result.add("code", new JsonPrimitive((Number) code));
        } else {
            log.warn(String.format("Unsupported type %s", value.getClass().getName()));
        }
        result.addProperty("name", value.getLabel());
        return result;
    }

    @SuppressWarnings("unchecked")
    public PersistentEnum deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element == null) {
            return null;
        }
        JsonElement code = element;
        if (code.isJsonObject()) {
            code = ((JsonObject)code).get("code");
        }
        if (code == null || code.isJsonNull()) {
            return null;
        }
        Class targetClass = (Class) type;
        if (PersistentStringEnum.class.isAssignableFrom(targetClass)) {
            return PersistentEnum.fromEnumCode((Class<PersistentStringEnum>)targetClass, code.getAsString());
        } else if (PersistentIntegerEnum.class.isAssignableFrom(targetClass)) {
            return PersistentEnum.fromEnumCode((Class<PersistentIntegerEnum>)targetClass, code.getAsInt());
        }
        log.warn(String.format("Unsupported type %s", targetClass.getName()));
        return null;
    }


}
