package com.example.maximeperalez.memorygame.helpers;

import com.example.maximeperalez.memorygame.model.FlickImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 04/04/2018.
 */

public class JSONParser {

    /**
     * Parses an array of JSONObject
     * and returns an ArrayList of FlickImages.
     * @param jsonObject : a JSONObject representing several FlickImages.
     * @return an ArrayList of FlickImages.
     */
    public static ArrayList<FlickImage> parseFlickResponse(JSONObject jsonObject) {
        ArrayList<FlickImage> flickImages = new ArrayList<>();
        try {
            JSONObject jsonPhotos = jsonObject.getJSONObject("photos");
            JSONArray jsonArray = jsonPhotos.getJSONArray("photo");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                FlickImage flickImage = new FlickImage(jsonPhoto);
                flickImages.add(flickImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flickImages;
    }
}
