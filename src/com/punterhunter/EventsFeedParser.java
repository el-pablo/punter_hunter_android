package com.punterhunter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class EventsFeedParser extends BaseFeedParser {

    public EventsFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public List<Event> parse() {
        final Event currentMessage = new Event();
        RootElement root = new RootElement("events");
        final List<Event> messages = new ArrayList<Event>();
        Element item = root.getChild(EVENT);
        item.setEndElementListener(new EndElementListener(){
            public void end() {
                messages.add(currentMessage.copy());
            }
        });
        item.getChild(NAME).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setName(body);
            }
        });
        item.getChild(END_TIME).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
//                currentMessage.setEndTime(new Date(Date.parse(body)));
            }
        });
        item.getChild("venue").getChild(LATITUDE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setLatitude(Double.parseDouble(body));
            }
        });
        item.getChild("venue").getChild(LONGITUDE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setLongitude(Double.parseDouble(body));
            }
        });
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            Log.e("PARSER", "couldn't parse because: "+e.getMessage());
        }
        return messages;
    }
}

