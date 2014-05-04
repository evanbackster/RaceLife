package com.racelife;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.racelife.operations.ListData;
import com.racelife.utilities.CallbackUtilities;
import com.racelife.utilities.JsonpfyProperties;
import com.racelife.utilities.URLUtilities;

@SuppressWarnings("serial")
public class ListRacersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (JsonpfyProperties.OPEN) {

			response.getWriter().println(getUser(request));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		response.getWriter().println(getUser(request));
	}

	private String getUser(HttpServletRequest request) {

		String answer = null;

		if (JsonpfyProperties.OPEN || JsonpfyProperties.authenticate(request)) {

			JSONArray entities = null;

			String kind = "RacingPositions";

			String filterField = "raceName";
			String filterValue = URLUtilities.decode(request
					.getParameter("RaceName"));

			entities = ListData.jsonListFilter(kind, filterField, filterValue,
					null, null, null, FilterOperator.EQUAL);

			answer = CallbackUtilities.getCallback(
					request.getParameter("callback"), entities.toString());

		}

		return answer;
	}

}
