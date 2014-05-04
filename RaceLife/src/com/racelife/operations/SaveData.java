package com.racelife.operations;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.racelife.utilities.URLUtilities;

/**
 * Send a particular registry to the datastore.
 * 
 * @Feature
 */
public class SaveData {

	public static final String FIELD_TYPE_STRING = "String";
	public static final String FIELD_TYPE_DATE = "Date";
	public static final String FIELD_TYPE_TEXT = "Text";

	public static String doPut(String kind, String ID, String[] fieldsKind,
			String[] fieldsName, String[] fieldsValue, String override) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity entity = null;

		if (ID != null) {

			entity = GetData.getEntity(kind, ID);

			if (entity == null) {

				try {

					entity = GetData.getEntity(kind, new Long(ID));

				} catch (Exception e) {

				}
			}

			if (entity == null) {

				entity = new Entity(kind, ID);
			}

		} else {

			entity = new Entity(kind);
		}

		if (fieldsName != null) {

			for (int i = 0; i < fieldsName.length; i++) {

				if (entity.getProperty(fieldsName[i]) == null
						|| !("false".equals(override))) {

					String fieldValue = URLUtilities.decode(fieldsValue[i]);

					if (FIELD_TYPE_STRING.equals(fieldsKind[i])) {

						entity.setProperty(fieldsName[i], fieldValue);

					} else if (FIELD_TYPE_TEXT.equals(fieldsKind[i])) {

						entity.setProperty(fieldsName[i], new Text(fieldValue));

					} else if (FIELD_TYPE_DATE.equals(fieldsKind[i])) {

						entity.setProperty(fieldsName[i], new Date(new Long(
								fieldValue)));

					} else {

						entity.setProperty(fieldsName[i],
								KeyFactory.createKey(fieldsKind[i], fieldValue));
					}
				}
			}
		}

		datastore.put(entity);

		String id = entity.getKey().getName();

		if (id == null) {
			id = Long.toString(entity.getKey().getId());
		}
		return id;
	}
}