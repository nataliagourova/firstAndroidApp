package com.example.helloworldnataliasapplication;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A match entry in the list of matches.
 */

public class MatchEntry {
    private static final String TAG = MatchEntry.class.getSimpleName();

    public String name;
    public String imageUrl;
    public String uid;
    public Boolean liked;
    public String lat;
    public String longtitude;

    public MatchEntry() { }

    public MatchEntry(String name,
                      String imageUrl,
                      String uid,
                      Boolean liked,
                      String lat,
                      String longtitude) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.uid = uid;
        this.liked = liked;
        this.lat = lat;
        this.longtitude = longtitude;
    }

    /**
     * Loads a raw JSON at R.raw.Matchs and converts it into a list of MatchEntry objects
     */
    public static List<MatchEntry> initMatchEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.matches);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonMatchesString = writer.toString();
        Gson gson = new Gson();
        Type matchListType = new TypeToken<ArrayList<MatchEntry>>() {
        }.getType();
        return gson.fromJson(jsonMatchesString, matchListType);
    }
}
