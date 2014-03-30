package com.racelife;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.racelife.operations.ListData;
import com.racelife.operations.SaveData;
import com.racelife.utilities.CallbackUtilities;
import com.racelife.utilities.JsonpfyProperties;
import com.racelife.utilities.URLUtilities;

@SuppressWarnings("serial")
public class SaveRacingPositionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (JsonpfyProperties.OPEN) {

			response.getWriter().println(saveData(request));

		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		response.getWriter().println(saveData(request));

	}

	private String saveData(HttpServletRequest request) {

		String answer = null;

		if (JsonpfyProperties.OPEN || JsonpfyProperties.authenticate(request)) {

			String raceName = URLUtilities.decode(request
					.getParameter("RaceName"));
			String racerNickName = URLUtilities.decode(request
					.getParameter("RacerNickName"));
			String timestamp = URLUtilities.decode(request
					.getParameter("Timestamp"));
			String latitude = URLUtilities.decode(request
					.getParameter("Latitude"));
			String longitude = URLUtilities.decode(request
					.getParameter("Longitude"));
			String raceDistance = URLUtilities.decode(request
					.getParameter("RaceDistance"));

			String[] fieldsKind = new String[] { "String", "String", "String",
					"String", "String", "String" };

			String[] fieldsName = new String[] { "raceName", "racerNickName",
					"timestamp", "latitude", "longitude", "raceDistance" };

			String[] fieldsValue = new String[] { raceName, racerNickName,
					timestamp, latitude, longitude, raceDistance };

			/*
			 * JSONArray racer = GetRacer(raceName, racerNickName);
			 * 
			 * JSONObject racerObject = null; if (racer != null) { try {
			 * racerObject = (JSONObject) racer.get(0);
			 * 
			 * String lat1 = racerObject.getString("latitude"); String long1 =
			 * racerObject.getString("longitude"); } catch (JSONException e) {
			 * 
			 * e.printStackTrace(); } }
			 */

			String racerId = SaveData.doPut("RacingPositions", racerNickName,
					fieldsKind, fieldsName, fieldsValue, "true");

		//	String id = SaveData.doPut("RacingHistory", null, fieldsKind,
		//			fieldsName, fieldsValue, "true");

			JSONArray entities = ListRacers(raceName);

			answer = CallbackUtilities.getCallback(
					request.getParameter("callback"), entities.toString());

		}
		return answer;
	}

	private JSONArray GetRacer(String raceName, String racerNickName) {

		JSONArray entities = null;

		entities = ListData.jsonMultipleListFilter("RacingPositions",
				"raceName", "racerNickName", null, null, raceName,
				racerNickName, null, null, "timestamp",
				SortDirection.DESCENDING, null);

		return entities;
	}

	private JSONArray ListRacers(String raceName) {

		JSONArray entities = null;

		entities = ListData.jsonListFilter("RacingPositions", "raceName",
				raceName, "timestamp", SortDirection.DESCENDING, null,
				FilterOperator.EQUAL);

		return entities;
	}

}
