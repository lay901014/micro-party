package edu.sjtu.party.utils;


import edu.sjtu.json.Response;
import edu.sjtu.json.ResponseEntity;
import org.springframework.util.StringUtils;

public class ResponseFactory {

    public static <T> Response<T> create(APIException ex) {
        return create(ex.getError(), ex.getParams());
    }

    public static <T> Response<T> create(APIErrors err, Object ... params) {
        String error = APIException.composeMessage(err, params);
        return new Response<>(err.errno, error);
    }

    public static <T> Response<T> create(Throwable ex) {
        if (ex instanceof APIException) {
            return create((APIException) ex);
        }
        String message = ex.getLocalizedMessage();
        if(StringUtils.isEmpty(message)) {
            message = ex.toString();
        }
        return create(APIErrors.EXCEPTION, message);
    }

    public static <T> ResponseEntity<T> create(ResponseEntity entity) {
        ResponseEntity<T> response = new ResponseEntity<T>(entity.getErrno(), entity.getError());
        response.setTotal(entity.getTotal());
        return response;
    } 

}
