package com.example.hometraise;

import java.util.HashMap;
import java.util.Map;

public class ClosetData {
    //String[] clothes = {"c_basic","c_halloween", "c_christmas"};
    //int[] YorN = {1, 0, 0};
    public int exercise; public int halloween; public int christmas;
    public ClosetData(){

    }
    public ClosetData(int exercise, int halloween, int christmas) {
        this.exercise= exercise;
        this.halloween = halloween;
        this.christmas = christmas;
    }
//    public void addClothes(int index) {
//        //YorN[index] = 1;
//    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("exercise", exercise);
        result.put("halloween", halloween);
        result.put("christmas", christmas);


        return result;
    }

}
