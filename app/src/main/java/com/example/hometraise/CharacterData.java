package com.example.hometraise;

import java.util.HashMap;
import java.util.Map;

public class CharacterData {
    public String name;
    public int point;
    public int clothes;

    public CharacterData() {

    }

    public CharacterData(String name, int point, int clothes) {
        this.name = name;
        this.point = point;
        this.clothes = clothes;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("point", point);
        result.put("clothes", clothes);

        return result;
    }
}
