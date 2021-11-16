package edu.sjtu.party.dao.hibernate;

import edu.sjtu.party.dao.UserCourseDAO;
import edu.sjtu.party.model.UserCourse;
import edu.sjtu.party.model.query.UserCourseQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:25
 **/
@Repository
public class UserCourseDAOHibernate extends AutowiredDAOHibernate<UserCourse> implements UserCourseDAO {

    private final static String SQL = "from UserCourse a";

    private final static String SQL_COUNT = "select count(a.id) from UserCourse a";

    private final static Object[][] QUERY_CONDITIONS = {
            {UserCourseQuery.UserCourseQueryEm.OPEN_ID, "a.userProfile.openId = ?"},
            {UserCourseQuery.UserCourseQueryEm.COURSE_ID, "a.course.id = ?"}
    };


    @Override
    public List<UserCourse> getList(Map<UserCourseQuery.UserCourseQueryEm, Object> conditions) {
        return super.getList(conditions,  0, -1, null, false, SQL, null,  QUERY_CONDITIONS, null);
    }

    @Override
    public int count(Map<UserCourseQuery.UserCourseQueryEm, Object> conditions) {
        return super.getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    @Override
    public void saveUserCourse(UserCourse userCourse) {
        super.saveObject(userCourse);
    }

    @Override
    public void updateUserCourse(UserCourse userCourse) {
        super.updateObject(userCourse);
    }
}
