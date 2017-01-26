package com.benjaminboyce.partyup;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{
	
	private LatLng mySpot;
	private ArrayList<PartyMember> party;
	private DatabaseHelper dbh;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 LocationManager locationManager;
		 String context = Context.LOCATION_SERVICE;
		 locationManager = (LocationManager)getSystemService(context);
		 
		 map = null;
		 Criteria criteria = new Criteria();
		    criteria.setAccuracy(Criteria.ACCURACY_FINE);
		    criteria.setAltitudeRequired(false);
		    criteria.setBearingRequired(false);
		    criteria.setCostAllowed(true);
		    criteria.setPowerRequirement(Criteria.POWER_LOW);
		    String provider = locationManager.getBestProvider(criteria, true);

			  Location location = locationManager.getLastKnownLocation(provider);
		    updateWithNewLocation(location);

		    locationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
		
		    mapFragment = (SupportMapFragment) getSupportFragmentManager()
			    .findFragmentById(R.id.map);
			mapFragment.getMapAsync(this);
		
			setLocation();
	}
	
	private final LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      updateWithNewLocation(location);
	    }
		 
	    public void onProviderDisabled(String provider){
	      updateWithNewLocation(null);
	    }

	    public void onProviderEnabled(String provider){
	    	
	    }
	    public void onStatusChanged(String provider, int status, Bundle extras){
	    	
	    }
	  };
	  
	  private void updateWithNewLocation(Location location) {
		    if (location != null) {
		      double lat = location.getLatitude();
		      double lng = location.getLongitude();
		      mySpot = new LatLng(lat, lng);
		    }
		    if(map != null){
		    	  map.clear();
		          CircleOptions options = new CircleOptions();
				  options.center(mySpot);
				  options.radius(7);
				  options.visible(true);
				  options.strokeWidth(15);
				  
				  map.moveCamera(CameraUpdateFactory.newLatLngZoom(mySpot, 15));
		    	  map.addCircle(options);
		    }
		  }
	
	public void pinSelf(Location location){
		mySpot = new LatLng(location.getLatitude(), location.getLongitude());
		if(map != null){
			CircleOptions options = new CircleOptions();
			options.center(mySpot);
			options.radius(7);
			options.visible(true);
			options.strokeWidth(15);
			map.addCircle(options);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMapReady(GoogleMap map) {
		this.map = map;
		CircleOptions options = new CircleOptions();
		options.center(mySpot);
		options.radius(5);
		options.visible(true);
		options.strokeWidth(10);
		  
		this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(mySpot, 15));
		  
		this.map.addCircle(options);
		addEntities(map);
	}
	
	public void setLocation(){
		LocationManager locationManager;
	    String context = Context.LOCATION_SERVICE;
	    locationManager = (LocationManager)getSystemService(context);
	    String provider = LocationManager.GPS_PROVIDER;
	    Location location = locationManager.getLastKnownLocation(provider);
	    pinSelf(location);
	}

	public void addEntities(GoogleMap map) {
		dbh = new DatabaseHelper(this);
		party = dbh.getPartyMembersByName();
		if(!party.isEmpty()){//if there are entities to add to the map
			LatLng theirSpot;
			for(int i = 0; i < party.size(); i++){
				if(party.get(i).getStatus().equals(PartyMember.PAIRED)){
					theirSpot = new LatLng(party.get(i).getLat(), party.get(i).getLon());
					map.addMarker(new MarkerOptions()
			        .position(theirSpot)
			        .title(party.get(i).getName()));
				}
			}
		} else {//if there are no entities to add to the map
		}
	}
	
	public void goToPartyPage(View view){
		Intent intent = new Intent(MainActivity.this, PartyPage.class);
		startActivity(intent);
	}

}
