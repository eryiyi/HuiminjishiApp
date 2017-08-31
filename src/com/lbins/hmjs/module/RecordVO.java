package com.lbins.hmjs.module;


/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordVO {
    private String record_id;
    private String empid;
    private String record_type;
    private String record_cont;
    private String record_pic;
    private String record_video;
    private String record_use;
    private String record_dateline;

    private String mobile;
    private String nickname;
    private String cover;

    private int commentNum;
    private int favourNum;

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(int favourNum) {
        this.favourNum = favourNum;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getRecord_type() {
        return record_type;
    }

    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }

    public String getRecord_cont() {
        return record_cont;
    }

    public void setRecord_cont(String record_cont) {
        this.record_cont = record_cont;
    }

    public String getRecord_pic() {
        return record_pic;
    }

    public void setRecord_pic(String record_pic) {
        this.record_pic = record_pic;
    }

    public String getRecord_video() {
        return record_video;
    }

    public void setRecord_video(String record_video) {
        this.record_video = record_video;
    }

    public String getRecord_use() {
        return record_use;
    }

    public void setRecord_use(String record_use) {
        this.record_use = record_use;
    }

    public String getRecord_dateline() {
        return record_dateline;
    }

    public void setRecord_dateline(String record_dateline) {
        this.record_dateline = record_dateline;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
