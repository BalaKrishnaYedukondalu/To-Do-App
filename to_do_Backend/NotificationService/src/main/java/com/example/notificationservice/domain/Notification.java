package com.example.notificationservice.domain;

import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Notification {
    @MongoId
    private String emailId;
    private String message;
    private JSONObject jsonObject;

    public Notification(String emailId, String message, JSONObject jsonObject) {
        this.emailId = emailId;
        this.message = message;
        this.jsonObject = jsonObject;
    }

    public Notification() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString() {
        return "Notification{" + "emailId='" + emailId + '\'' + ", message='" + message + '\'' + ", jsonObject=" + jsonObject + '}';
    }
}
