package edu.sjtu.party.model.query;

import edu.sjtu.dao.QueryCondition;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:22
 **/
public class UserCourseQuery {

    public UserCourseQuery(String openId, String courseId) {
        this.openId = openId;
        this.courseId = courseId;
    }

    private String openId;

    private String courseId;

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Map<UserCourseQueryEm, Object> getConditionMap() {
        Map<UserCourseQueryEm, Object> conditionMap = new HashMap<>();

        if(StringUtils.isNotEmpty(this.openId)) {
            conditionMap.put(UserCourseQueryEm.OPEN_ID, this.openId);
        }
        if(StringUtils.isNotEmpty(this.courseId)) {
            conditionMap.put(UserCourseQueryEm.COURSE_ID, this.courseId);
        }

        return conditionMap;
    }

    public enum UserCourseQueryEm implements QueryCondition {
        OPEN_ID,
        COURSE_ID
    }
}
