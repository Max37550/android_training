package com.example.maximeperalez.memorygame;

import android.graphics.Bitmap;

import com.example.maximeperalez.memorygame.model.FlickImage;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class FlickImageTests {

    @Mock
    Bitmap bitmap;

    @Test
    public void testGetAndSetBitmap() throws JSONException {
        // Given
        JSONObject jsonObject = mock(JSONObject.class);
        // When
        FlickImage flickImage = new FlickImage(jsonObject);
        flickImage.setBitmap(bitmap);
        // Then
        Assert.assertEquals(flickImage.getBitmap(), bitmap);
    }

    @Test
    public void testSourceUrl() throws JSONException {
        // Given
        JSONObject jsonObject = mock(JSONObject.class);
        when(jsonObject.getString("id")).thenReturn("aZ3dee0t");
        when(jsonObject.getString("owner")).thenReturn("ownerName");
        when(jsonObject.getString("secret")).thenReturn("qwerty");
        when(jsonObject.getString("server")).thenReturn("america");
        when(jsonObject.getInt("farm")).thenReturn(22);
        when(jsonObject.getString("title")).thenReturn("My awesome picture");
        when(jsonObject.getInt("ispublic")).thenReturn(1);
        when(jsonObject.getInt("isfriend")).thenReturn(1);
        when(jsonObject.getInt("isfamily")).thenReturn(0);
        // When
        FlickImage flickImage = new FlickImage(jsonObject);
        URL sourceURL = flickImage.sourceURL();
        // Then
        Assert.assertEquals(sourceURL.toString(), "https://farm22.staticflickr.com/america/aZ3dee0t_qwerty.jpg");
    }
}
