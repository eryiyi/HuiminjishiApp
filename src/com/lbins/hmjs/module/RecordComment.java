package com.lbins.hmjs.module;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordComment {
    private String comment_id;
    private String record_id;
    private String comment_cont;
    private String comment_dateline;
    private String comment_empid;

    private String commentNickname;
    private String commentCover;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getComment_cont() {
        return comment_cont;
    }

    public void setComment_cont(String comment_cont) {
        this.comment_cont = comment_cont;
    }

    public String getComment_dateline() {
        return comment_dateline;
    }

    public void setComment_dateline(String comment_dateline) {
        this.comment_dateline = comment_dateline;
    }

    public String getComment_empid() {
        return comment_empid;
    }

    public void setComment_empid(String comment_empid) {
        this.comment_empid = comment_empid;
    }

    public String getCommentNickname() {
        return commentNickname;
    }

    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }

    public String getCommentCover() {
        return commentCover;
    }

    public void setCommentCover(String commentCover) {
        this.commentCover = commentCover;
    }
}
