package edu.sjtu.party.web.controller;

import com.google.common.reflect.TypeToken;
import edu.sjtu.json.Response;
import edu.sjtu.json.ResponseEntity;
import edu.sjtu.party.model.resp.CourseVo;
import edu.sjtu.party.service.PartyService;
import edu.sjtu.party.web.controller.annotation.CheckPriviledge;
import edu.sjtu.party.web.controller.interceptor.SecurityInterceptor;
import edu.sjtu.web.annotation.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 11:14
 **/
@RestController
@RequestMapping(value = "/party")
@API
public class PartyController {

    @Autowired
    private PartyService partyService;

    @CheckPriviledge
    @RequestMapping(value = "/my/course/list", method = RequestMethod.GET)
    public Response getMyCourseList(HttpServletRequest request) {
        ResponseEntity<CourseVo> result = new ResponseEntity<>(new TypeToken<Response<CourseVo>>() {}.getType());
        result.addAll(partyService.getCourseListByOpenId(SecurityInterceptor.getLoginUserID(request)));
        return result;
    }

    @CheckPriviledge
    @RequestMapping(value = "/my/update/state", method = RequestMethod.POST)
    public Response setUserCourseIsView(HttpServletRequest request, String courseId) {
        partyService.saveUserCourse(SecurityInterceptor.getLoginUser(request), courseId);
        return new Response();
    }
}
