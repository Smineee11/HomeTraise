package com.example.hometraise;

import java.util.HashMap;
import java.util.Map;

public class ClosetData {
    String[] clothes = {"c_basic","c_halloween", "c_christmas"};
    int[] YorN = {1, 0, 0};


    public ClosetData() {
       clothes[0] = "c_basic";
       clothes[1] = "c_halloween";
       clothes[2] = "c_christmas";
       YorN[0]=1;
       YorN[1]=0;
       YorN[2]=0;

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
