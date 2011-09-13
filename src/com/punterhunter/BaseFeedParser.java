package com.punterhunter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public abstract class BaseFeedParser implements FeedParser {

    // names of the XML tags
    static final String EVENT = "event";
    static final String NAME = "name";
    static final String END_TIME = "end-time";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    
    final URL feedUrl;

    protected BaseFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
            Log.d("BASE_FEED_PARSER", feedUrl.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
