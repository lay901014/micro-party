package edu.sjtu.party.dao;

import edu.sjtu.party.model.UserCourse;
import edu.sjtu.party.model.query.UserCourseQuery;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:25
 **/
public interface UserCourseDAO {

    List<UserCourse> getList(Map<UserCourseQuery.UserCourseQueryEm, Object> conditions);

    int count(Map<UserCourseQuery.UserCourseQueryEm, Object> conditions);

    void saveUserCourse(UserCourse userCourse);

    void updateUserCourse(UserCourse userCourse);
}
