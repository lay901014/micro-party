package edu.sjtu.party.dao;

import edu.sjtu.party.model.Course;

import java.util.List;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:03
 **/
public interface CourseDAO {

    List<Course> getEnabledCourseList();

    Course findById(String id);
}
