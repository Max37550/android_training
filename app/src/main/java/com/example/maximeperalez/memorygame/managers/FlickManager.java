package com.example.maximeperalez.memorygame.managers;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.maximeperalez.memorygame.helpers.JSONParser;
import com.example.maximeperalez.memorygame.model.FlickImage;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by maxime.peralez on 04/04/2018.
 */

public class FlickManager implements ImageDownloader {

    // Data binding
    private final MutableLiveData<ArrayList<FlickImage>> mFlickImagesLiveData = new MutableLiveData<>();

    // Constants
    private final String API_KEY = "d06b248109f68608db417e22fc4fbb72";

    // Endpoints
    private final String BASE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search";

    // Parameters
    private final String PARAM_API_KEY = "api_key";
    private final String PARAM_PER_PAGE = "per_page";
    private final String PARAM_TEXT = "text";
    private final String PARAM_FORMAT = "format";
    private final String PARAM_SORT = "sort";

    // MARK: - ImageDownloader

    @Override
    public MutableLiveData<ArrayList<FlickImage>> getFlickImagesLiveData() {
        return mFlickImagesLiveData;
    }

    @Override
    public void downloadImages(int numberOfImages) {
        URL url = buildUrl(numberOfImages);
        new QueryPhotosAttributesTask(this).execute(url);
    }

    // MARK: - Private

    /**
     * Builds the URL used to fetch a given amount of pictures.
     * @param numberOfImages : the number of images to fetch.
     * @return an URL representing the endpoint to use.
     */
    private URL buildUrl(int numberOfImages) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_TEXT, "kitten")
                .appendQueryParameter(PARAM_SORT, "relevance")
                .appendQueryParameter(PARAM_PER_PAGE, String.valueOf(numberOfImages))
                .appendQueryParameter(PARAM_FORMAT, "json").build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static class QueryPhotosAttributesTask extends AsyncTask<URL, Void, ArrayList<FlickImage>> {
        private final WeakReference<FlickManager> flickManager;

        QueryPhotosAttributesTask(FlickManager flickManager) {
            this.flickManager = new WeakReference<>(flickManager);
        }

        @Override
        protected ArrayList<FlickImage> doInBackground(URL... urls) {
            ArrayList<FlickImage> flickImages = null;
            // Fetch data
            try {
                // Get response
                String jsonString = getResponseFromHttpUrl(urls[0]);
                // Parse JSON
                JSONObject jsonObject = new JSONObject(jsonString);
                flickImages = JSONParser.parseFlickResponse(jsonObject);
                // Download photos
                downloadBitmaps(flickImages);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flickImages;
        }

        @Override
        protected void onPostExecute(ArrayList<FlickImage> flickImages) {
            flickManager.get().mFlickImagesLiveData.setValue(flickImages);
        }

        /**
         * Downloads all images from their respective source urls
         * and updates each FlickImage object with the new created bitmap.
         * @param flickImages : An ArrayList of FlickImages
         */
        private void downloadBitmaps(ArrayList<FlickImage> flickImages) {
            for (FlickImage flickImage : flickImages) {
                try {
                    Bitmap bitmap = getBitmap(flickImage.sourceURL());
                    flickImage.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Gets and creates the bitmap contained in the given url.
         * @param url : an url representing an image
         * @return a Bitmap object
         * @throws IOException
         */
        private Bitmap getBitmap(URL url) throws IOException {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
    }

    /**
     * Retrieves the JSON data from the given url.
     * @param url : an url containing json data
     * @return a String representing the retrieved json.
     * @throws IOException
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpsURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("jsonFlickrApi\\(");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpsURLConnection.disconnect();
        }
    }
}
