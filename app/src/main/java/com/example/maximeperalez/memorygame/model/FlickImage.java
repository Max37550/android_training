package com.example.maximeperalez.memorygame.model;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maxime.peralez on 04/04/2018.
 */

public class FlickImage {

    // Attributes
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private boolean isPublic;
    private boolean isFriend;
    private boolean isFamily;
    private Bitmap bitmap;

    // MARK: - Initialization

    public FlickImage(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            owner = jsonObject.getString("owner");
            secret = jsonObject.getString("secret");
            server = jsonObject.getString("server");
            farm = jsonObject.getInt("farm");
            title = jsonObject.getString("title");
            isPublic = jsonObject.getInt("ispublic") == 1;
            isFriend = jsonObject.getInt("isfriend") == 1;
            isFamily = jsonObject.getInt("isfamily") == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // MARK: - Getters & Setters

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    // MARK: - Public

    public URL sourceURL() {
        String urlString = "https://farm" + String.valueOf(farm)
                + ".staticflickr.com/" + server + "/"
                + id + "_" + secret + ".jpg";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
