package com.racelife.utilities;

import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ConvertEntityToJson {

	public static JSONObject getJson(final Entity result,
			final String returnField) {

		JSONObject jsonObject = new JSONObject();

		if (result != null) {

			try {

				if (returnField == null || "ID".equals(returnField)) {

					if (result.getKey().getName() != null) {

						jsonObject.put("ID", result.getKey().getName());

					} else {

						jsonObject.put("ID", result.getKey().getId());
					}

				}

				for (Entry<String, Object> entry : result.getProperties()
						.entrySet()) {

					if (returnField == null
							|| entry.getKey().equals(returnField)) {

						if (entry.getValue() instanceof Text) {

							jsonObject.put(entry.getKey(),
									((Text) entry.getValue()).getValue());

						} else {

							jsonObject.put(entry.getKey(), entry.getValue());
						}
					}
				}

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

		return jsonObject;
	}

}