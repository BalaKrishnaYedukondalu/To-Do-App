package com.niit.bej.todoservice.domain;

import org.springframework.data.annotation.Id;

public class ToDo {
    @Id
    private String title;
    private String description;
    private String imageUrl;
    private String dueDate;
    private String priority;
    private String createdDateTime;
    private boolean isCompleted;
    private boolean isArchive;
    private boolean isImportant;
    private String updatedTask;
    private boolean isNotificationRead;
    private boolean isPersonal;
    private boolean isTravel;


    public ToDo() {
    }

    public ToDo(String title, String description, String imageUrl, String dueDate, String priority, String createdDateTime, boolean isCompleted, boolean isArchive, boolean isImportant, String updatedTask, boolean isNotificationRead, boolean isPersonal, boolean isTravel) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.dueDate = dueDate;
        this.priority = priority;
        this.createdDateTime = createdDateTime;
        this.isCompleted = isCompleted;
        this.isArchive = isArchive;
        this.isImportant = isImportant;
        this.updatedTask = updatedTask;
        this.isNotificationRead = isNotificationRead;
        this.isPersonal = isPersonal;
        this.isTravel = isTravel;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getUpdatedTask() {
        return updatedTask;
    }

    public void setUpdatedTask(String updatedTask) {
        this.updatedTask = updatedTask;
    }

    public boolean isNotificationRead() {
        return isNotificationRead;
    }

    public void setNotificationRead(boolean notificationRead) {
        isNotificationRead = notificationRead;
    }

    public boolean isPersonal() {
        return isPersonal;
    }

    public void setPersonal(boolean personal) {
        isPersonal = personal;
    }

    public boolean isTravel() {
        return isTravel;
    }

    public void setTravel(boolean travel) {
        isTravel = travel;
    }


    @Override
    public String toString() {
        return "ToDo{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", imageUrl='" + imageUrl + '\'' + ", dueDate='" + dueDate + '\'' + ", priority='" + priority + '\'' + ", createdDateTime='" + createdDateTime + '\'' + ", isCompleted=" + isCompleted + ", isArchive=" + isArchive + ", isImportant=" + isImportant + ", updatedTask='" + updatedTask + '\'' + ", isNotificationRead=" + isNotificationRead + ", isPersonal=" + isPersonal + ", isTravel=" + isTravel +

                '}';
    }
}
