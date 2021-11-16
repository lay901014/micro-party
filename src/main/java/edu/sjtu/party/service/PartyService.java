package edu.sjtu.party.service;

import edu.sjtu.party.model.UserProfile;
import edu.sjtu.party.model.resp.CourseVo;

import java.util.List;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 11:11
 **/
public interface PartyService {

    List<CourseVo> getCourseListByOpenId(String openId);

    void saveUserCourse(UserProfile userProfile, String courseId);
}
