package com.example.hometraise;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    public String name;
    public String age;
    public String height;
    public double weight;

    public UserData() {
    }

    public UserData(String name, String age, String height, double weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        result.put("height", height);
        result.put("weight", weight);

        return result;
    }
}
