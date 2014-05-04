package com.racelife.utilities;


import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GetUserLocation {

	private static final String googlePlacesURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
	private static final String googlePlacesKey = "AIzaSyCVYGpOWlqcZrcebSLYSHZe1bTJjjekWYM";

	private static String parameters = "radius=10" // &types=food|cafe|museum|establishment|restaurant"
			+ "&rankby=prominence&sensor=false&key=" + googlePlacesKey;

	public static String verify(String latitude, String longitude) {
		
		String location = null;

		if (latitude != null && longitude != null) {

			parameters += "&location=" + latitude + "," + longitude;

			location = URLUtilities.fetchURLGet(googlePlacesURL, parameters);

			location = getLocationName(location);
		}

		return location;
	}

	private static String getLocationName(String location) {

		String locationName = location;

		try {
			JSONObject locationJson = new JSONObject(location);

			String results = JSONUtilities.getString(locationJson, "results");

			JSONObject firstLocation = JSONUtilities.getUserJson(results);

			locationName = JSONUtilities.getString(firstLocation, "name");

			String vicinity = JSONUtilities
					.getString(firstLocation, "vicinity");

			locationName += ", " + vicinity;

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return locationName;
	}

}
