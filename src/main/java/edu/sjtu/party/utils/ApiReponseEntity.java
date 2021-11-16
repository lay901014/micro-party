package edu.sjtu.party.utils;

import com.google.gson.annotations.Expose;
import edu.sjtu.json.Response;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @description: 添加额外信息
 * @author: lay
 * @create: 2020/02/24 17:11
 **/
public class ApiReponseEntity<T> extends Response<T> {

    public ApiReponseEntity() {
        super();
    }

    public ApiReponseEntity(Type type) {
        super(type);
    }

    @Expose
    private Map<String, Object> extData;

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }
}
