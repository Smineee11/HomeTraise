package com.example.hometraise;

import java.util.HashMap;
import java.util.Map;

public class ClosetData {
    String[] clothes = {"c_basic","basic_2"};
    int[] YorN = {1, 0};

    public ClosetData() {
    }

    public void addClothes(int index) {
        YorN[index] = 1;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        for(int i=0; i<clothes.length; i++)
            result.put(clothes[i], YorN[i]);

        return result;
    }

}
