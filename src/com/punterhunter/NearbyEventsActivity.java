package com.punterhunter;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class NearbyEventsActivity extends MapActivity {
	
	private static final String CLASS_NAME = "NearbyEventsActivity";
	private EventsItemizedOverlay overlay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        MapController mapController = mapView.getController();
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        overlay = new EventsItemizedOverlay(drawable,this);
        GeoPoint point = new GeoPoint(new Double(51.51442*1e6).intValue(),new Double(0.13298*1e6).intValue());
        mapController.animateTo(point);
        mapOverlays.add(overlay);
    }
    
    @Override
	public boolean dispatchTouchEvent(MotionEvent event) {
	    boolean result = super.dispatchTouchEvent(event);
	    if (event.getAction() == MotionEvent.ACTION_UP)
	        reloadMapData();
	    return result;
	}
    
    private void reloadMapData(){
    	try{
    		new Updater().execute(overlay);
    	} catch(IllegalStateException ex){
    		Log.d(CLASS_NAME, "tried to execute while still doing previous task");
    	}
	}
    
    class Updater extends AsyncTask<EventsItemizedOverlay, Integer, List<OverlayItem>> {


		@Override
		protected List<OverlayItem> doInBackground(EventsItemizedOverlay... overlay) {
			List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
			try{
				MapView mapView = (MapView) findViewById(R.id.mapview);
				GeoPoint newFocus = mapView.getMapCenter();
				String latitude = Double.toString(newFocus.getLatitudeE6()/1e6);
				String longitude = Double.toString(newFocus.getLongitudeE6()/1e6);
				String distance = "2";
				String url = "http://192.168.1.107:3000/events/nearby?latitude="+latitude+"&longitude="+longitude+"&distance="+distance;
				EventsFeedParser parser = new EventsFeedParser(url);
				List<Event> events = parser.parse();
				for(Event e:events){
					GeoPoint point = new GeoPoint(new Double(e.getLatitude()*1e6).intValue(), (new Double(e.getLongitude()*1e6).intValue()));
					/*Log.d(CLASS_NAME, "latitude "+point.getLatitudeE6());
					Log.d(CLASS_NAME, "longitude "+point.getLongitudeE6());**/
					overlayItems.add(new OverlayItem(point, e.getName(), "some other stuff"));
				}
			}catch(Exception e){
				Log.e(CLASS_NAME, "Problem? "+e.getMessage());
			    e.printStackTrace();
			}
			return overlayItems;
		}
		
		@Override
		protected void onPostExecute(List<OverlayItem> overlays) {
			overlay.setOverlays(overlays);
		}
    }
    
    

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}