package edu.sjtu.party.dao.hibernate;

import edu.sjtu.party.dao.CourseDAO;
import edu.sjtu.party.model.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:03
 **/
@Repository
public class CourseDAOHibernate extends AutowiredDAOHibernate<Course> implements CourseDAO {

    private final static String SQL = "from Course a";

    @Override
    public List<Course> getEnabledCourseList() {
        String hql = SQL + " where a.enabled = :enabled";
        return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("enabled", true).list();
    }

    @Override
    public Course findById(String id) {
        return super.getObject(Course.class, id);
    }
}
