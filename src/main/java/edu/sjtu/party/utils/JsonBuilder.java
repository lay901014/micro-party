package edu.sjtu.party.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Set;

public class JsonBuilder {

    public static JSONObject build(Object... args) {
        JSONObject json = new JSONObject();

        for(int i = 0; i < args.length; ++i) {
            json.put(args[i], args[i + 1]);
            ++i;
        }

        return json;
    }

    public JSONObject b(Object... args) {
        JSONObject json = new JSONObject();
        for(int i = 0; i < args.length; ++i) {
            json.put(args[i], args[i + 1]);
            ++i;
        }
        return json;
    }

    public static JSONArray buildArray(Object... args) {
        JSONArray array = new JSONArray();
        for(int i = 0; i < args.length; ++i) {
            array.add(args[i]);
        }
        return array;
    }

    public static void mergeTo(JSONObject arg, JSONObject target) {
        Set<String> keySet = arg.keySet();

        for(String key : keySet) {
            if (!target.containsKey(key)) {
                target.put(key, arg.get(key));
            } else {
                Object oldValue = target.get(key);
                Object newValue = arg.get(key);
                if (oldValue instanceof JSONArray && newValue instanceof JSONArray) {
                    JSONArray a = (JSONArray)newValue;
                    JSONArray b = (JSONArray)oldValue;
                    b.addAll(a);
                } else if (oldValue instanceof JSONObject && newValue instanceof JSONObject) {
                    mergeTo((JSONObject)newValue, (JSONObject)oldValue);
                    target.put(key, oldValue);
                } else {
                    target.put(key, newValue);
                }
            }
        }
    }

    public static JSONArray mergeTo(JSONArray a, JSONArray b) {
        b.addAll(a);
        return b;
    }

}
