package edu.sjtu.party.service.impl;

import edu.sjtu.party.dao.CourseDAO;
import edu.sjtu.party.dao.UserCourseDAO;
import edu.sjtu.party.model.Course;
import edu.sjtu.party.model.UserCourse;
import edu.sjtu.party.model.UserProfile;
import edu.sjtu.party.model.query.UserCourseQuery;
import edu.sjtu.party.model.resp.CourseVo;
import edu.sjtu.party.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 11:11
 **/
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserCourseDAO userCourseDAO;

    @Override
    public List<CourseVo> getCourseListByOpenId(String openId) {
        List<CourseVo> respList = new ArrayList<>();
        List<Course> courseList = courseDAO.getEnabledCourseList();
        for(Course course : courseList) {
            UserCourseQuery userCourseQuery = new UserCourseQuery(openId, course.getId());
            respList.add(CourseVo.convert(course, (userCourseDAO.count(userCourseQuery.getConditionMap()) > 0)));
        }
        return respList;
    }

    @Override
    public void saveUserCourse(UserProfile userProfile, String courseId) {

        Course course = courseDAO.findById(courseId);
        if(course == null) {
            throw new RuntimeException("该课程不存在。");
        }

        if(userProfile == null) {
            throw new RuntimeException("用户信息不存在。");
        }

        List<UserCourse> list = userCourseDAO.getList(new UserCourseQuery(userProfile.getOpenId(), courseId).getConditionMap());
        if(list.isEmpty()) {
            userCourseDAO.saveUserCourse(UserCourse.createInstance(userProfile, course));
        }else {
            list.get(0).setIsView(true);
            userCourseDAO.updateUserCourse(list.get(0));
        }
    }
}
