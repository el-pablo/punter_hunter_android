package com.punterhunter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class EventsItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private static final String CLASS_NAME = "EventsItemizedOverlay";
	
	public EventsItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		Log.d(CLASS_NAME, "setting the change listener(2)");
	}
	
	public EventsItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		Log.d(CLASS_NAME, "setting the change listener");
		populate();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	public void setOverlays(List<OverlayItem> overlays){
		this.mOverlays = overlays;
		populate();
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
}
