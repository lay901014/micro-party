package edu.sjtu.party.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 15:12
 **/
@Table(name = "p_user_course")
@Entity
public class UserCourse implements Serializable {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "open_id")
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "is_view")
    private Boolean isView;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getIsView() {
        return this.isView;
    }

    public void setIsView(Boolean view) {
        this.isView = view;
    }

    public static UserCourse createInstance(UserProfile userProfile, Course course) {
        UserCourse userCourse = new UserCourse();
        userCourse.setId(UUID.randomUUID().toString());
        userCourse.setUserProfile(userProfile);
        userCourse.setCourse(course);
        userCourse.setIsView(true);

        return userCourse;
    }
}
