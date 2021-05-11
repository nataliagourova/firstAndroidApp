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

    public final String name;
    public final Uri dynamicUrl;
    public final String url;
    public final String age;
    public final String description;

    public MatchEntry(
            String name, String dynamicUrl, String url, String age, String description) {
        this.name = name;
        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.age = age;
        this.description = description;
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
        Type matchListType = new TypeToken<ArrayList<MatchEntry>>() {}.getType();
        return gson.fromJson(jsonMatchesString, matchListType);
    }
}
