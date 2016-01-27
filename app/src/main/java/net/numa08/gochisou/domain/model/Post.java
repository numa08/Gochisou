package net.numa08.gochisou.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmString;
import io.realm.annotations.PrimaryKey;

public class Post extends RealmObject{
    @PrimaryKey
    private long number;
    private String name;
    private RealmList<RealmString> tags;
    private String category;
    @SerializedName("full_name")
    private String fullName;
    private boolean wip;
    @SerializedName("body_md")
    private String bodyMD;
    @SerializedName("body_html")
    private String bodyHTML;
    private Date createdAt;
    private Date updateAt;
    private String message;
    @SerializedName("revision_number")
    private long revisionNumber;
    @SerializedName("created_by")
    private User createdBy;
    @SerializedName("update_by")
    private User updateBy;
    private String kind;
    @SerializedName("comment_count")
    private long commentCount;
    @SerializedName("tasks_count")
    private long tasksCount;
    @SerializedName("stargazers_count")
    private long stargazersCount;
    @SerializedName("watchers_count")
    private long watchersCount;
    private boolean star;
    private boolean watch;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RealmString> getTags() {
        return tags;
    }

    public void setTags(RealmList<RealmString> tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isWip() {
        return wip;
    }

    public void setWip(boolean wip) {
        this.wip = wip;
    }

    public String getBodyMD() {
        return bodyMD;
    }

    public void setBodyMD(String bodyMD) {
        this.bodyMD = bodyMD;
    }

    public String getBodyHTML() {
        return bodyHTML;
    }

    public void setBodyHTML(String bodyHTML) {
        this.bodyHTML = bodyHTML;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(long tasksCount) {
        this.tasksCount = tasksCount;
    }

    public long getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(long stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public long getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(long watchersCount) {
        this.watchersCount = watchersCount;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }
}