package com.niit.bej.todoservice.config;


import org.json.simple.JSONObject;

public class ToDoDTO {
    private JSONObject jsonObject;

    public ToDoDTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ToDoDTO() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString() {
        return "ToDoDTO{" + "jsonObject=" + jsonObject + '}';
    }
}
