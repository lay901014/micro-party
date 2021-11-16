package edu.sjtu.party.web.model;

import com.google.gson.annotations.Expose;

public class App {

    @Expose
    private String id;			//无意义的唯一id，必须为GUID格式，并保持稳定
    @Expose
    private String name;		//显示名称
    @Expose
    private String uri;			//启动uri
    @Expose
    private String abbreviation;//缩略名
    @Expose
    private String description;	//服务摘要
    @Expose
    private String tags;		//应用标签，可以有多个，以逗号分隔
    @Expose
    private String icon;		//图标uri
    @Expose
    private String palette; 	//调色板颜色，形如：#ff0000
    @Expose
    private String department;	//负责院系
    @Expose
    private String contact; 	//联系人及其方式描述
    @Expose
    private Long online;		//上线时间的时间戳，unix时间戳
    @Expose
    private Long offline;		//下线时间的时间戳，unix时间戳
    @Expose
    private Integer recommend;	//推荐度，可办任务按推荐度逆序排列
    @Expose
    private boolean visible;	//是否可见
    @Expose
    private boolean ready;		//是否提供线上服务。默认为false。一门式服务网站和App会特殊显示非线上服务
    @Expose
    private String system;		//所属系统。工作流应用的此字段为空；用于区分管理企业内其他系统提供的“可办“

    public App() {
        visible = true;
        ready= true;
        system ="保卫处进校审核";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPalette() {
        return palette;
    }

    public void setPalette(String palette) {
        this.palette = palette;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getOnline() {
        return online;
    }

    public void setOnline(long online) {
        this.online = online;
    }

    public long getOffline() {
        return offline;
    }

    public void setOffline(long offline) {
        this.offline = offline;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
