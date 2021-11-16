package edu.sjtu.party.web.controller;

import com.google.gson.Gson;
import edu.sjtu.json.Response;
import edu.sjtu.party.utils.APIException;
import edu.sjtu.party.utils.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    static final String MODEL_KEY = "__x_Canvas_Exception_Model_Key";

    static final Gson gson = new Gson();
    /**
     * Json first object in model.
     */
    View view = new AbstractView() {
        @Override
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            if(model.containsKey(MODEL_KEY)) {
                Object m = model.get(MODEL_KEY);
                response.getWriter().write(m instanceof String ? (String) m : gson.toJson(m));
            }
        }
    };

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(APIException.translate(ex));
        Response r = ResponseFactory.create(ex);
        mv.getModelMap().put(MODEL_KEY, r);
        mv.setView(this.view);
        if (ex instanceof APIException) {
            log.warn("Request URI: {}, APIException: {}", request.getRequestURI(), r.getError());
        } else {
            log.error("Request URI: {}, Exception:", request.getRequestURI(), ex);
        }
        return mv;
    }

}
