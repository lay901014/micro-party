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

    public static CourseVo convert(Course course, Boolean isView) {
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);
        courseVo.setIsView(isView);

        return courseVo;
    }
}
