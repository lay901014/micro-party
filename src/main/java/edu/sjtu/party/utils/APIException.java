package edu.sjtu.party.utils;

import edu.sjtu.json.Response;

import javax.servlet.http.HttpServletResponse;

public class APIException extends RuntimeException {

    private APIErrors error;
    private Object[] params;

    public APIErrors getError() {
        return error;
    }

    public Object[] getParams() {
        return params;
    }

    protected APIException(APIErrors error, Object ... params) {
        super(error.toString());
        this.error = error;
        this.params = params;
    }

    public static APIException create(APIErrors error) {
        return create(error, (Object[]) null);
    }

    public static APIException create(Response response) {
        return create(APIErrors.EXCEPTION, response.getError());
    }

    public static APIException create(APIErrors error, Object ... params) {
        return new APIException(error, params);
    }

    protected static String composeMessage(APIErrors error, Object ... params) {
        String err = LanguagePack.get(error);
        if (null == params) return err;
        return String.format(err, params);
    }

    public static int translate(Exception ex) {
        if(ex instanceof APIException) {
            return ((APIException)ex).error.getHTTPStatusCode();
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}
