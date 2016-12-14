package com.example.administrator.myapplication.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/14.
 */

public class Comment implements Serializable {


    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    User commentator;
    Date createDate;
    Date editDate;
    String content;
    String id;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
