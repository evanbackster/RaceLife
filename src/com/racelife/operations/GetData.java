package com.racelife.operations;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.racelife.utilities.ConvertEntityToJson;

/**
 * Get the data with a particular ID and a particular kind in the datastore.
 * 
 * @Feature
 */
public class GetData {

	public static JSONObject getJson(String kind, String keyValue) {

		JSONObject jsonObject = ConvertEntityToJson.getJson(
				getEntity(kind, keyValue), null);

		return jsonObject;
	}

	public static JSONObject getJson(String kind, Long keyValue) {

		JSONObject jsonObject = ConvertEntityToJson.getJson(
				getEntity(kind, keyValue), null);

		return jsonObject;
	}

	public static Entity getEntity(String kind, Long keyValue) {

		return getEntity(KeyFactory.createKey(kind, keyValue));
	}

	public static Entity getEntity(String kind, String keyName) {

		return getEntity(KeyFactory.createKey(kind, keyName));
	}

	public static Entity getEntity(Key key) {

		Entity result = null;
		try {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			result = datastore.get(key);

		} catch (EntityNotFoundException e) {

		}

		return result;
	}

}