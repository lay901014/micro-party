package edu.sjtu.party.model.resp;

import com.google.gson.annotations.Expose;
import edu.sjtu.party.model.Course;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 14:44
 **/
public class CourseVo {

    @Expose
    private String id;

    /**
     * 党课名称
     */
    @Expose
    private String courseName;

    /**
     * 经度
     */
    @Expose
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Expose
    private BigDecimal latitude;

    /**
     * 页面文本
     */
    @Expose
    private String pageContent;

    /**
     * 是否已观看
     */
    @Expose
    private Boolean isView;

    /**
     * 视频地址
     */
    @Expose
    private String videoUrl;

    /**
     * 图片链接
     */
    @Expose
    private String imageUrls;

    /**
     * 授课人
     */
    @Expose
    private String teacher;

    /**
     * 地址
     */
    @Expose
    private String address;

    /**
     * 课程标记
     */
    @Expose
    private String courseTag;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getPageContent() {
        return this.pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public Boolean getIsView() {
        return this.isView;
    }

    public void setIsView(Boolean view) {
        this.isView = view;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrls() {
        return this.imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourseTag() {
        return this.courseTag;
    }

    public void setCourseTag(String courseTag) {
        this.courseTag = courseTag;
    }

    public static CourseVo convert(Course course, Boolean isView) {
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);
        courseVo.setIsView(isView);

        return courseVo;
    }
}
