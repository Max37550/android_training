package com.example.maximeperalez.memorygame;

import com.example.maximeperalez.memorygame.helpers.JSONParser;
import com.example.maximeperalez.memorygame.model.FlickImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 09/04/2018.
 */

public class JSONParserTests {

    @Test
    public void testParseFlickResponse() throws JSONException {
        // Given
        JSONObject apiJsonObject = mock(JSONObject.class);
        JSONObject photosJsonObject = mock(JSONObject.class);
        JSONArray photosJsonArray = mock(JSONArray.class);
        JSONObject singlePhotoJsonObject = mock(JSONObject.class);
        when(apiJsonObject.getJSONObject("photos")).thenReturn(photosJsonObject);
        when(photosJsonObject.getJSONArray("photo")).thenReturn(photosJsonArray);
        when(photosJsonArray.length()).thenReturn(6);
        when(photosJsonArray.getJSONObject(anyInt())).thenReturn(singlePhotoJsonObject);
        // When
        ArrayList<FlickImage> flickImages = JSONParser.parseFlickResponse(apiJsonObject);
        // Then
        Assert.assertEquals(flickImages.size(), 6);
    }
}
