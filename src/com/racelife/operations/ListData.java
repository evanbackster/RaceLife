package com.racelife.operations;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.racelife.utilities.ConvertEntityToJson;

/**
 * List the data of a particular kind in the datastore.
 * 
 * @Feature
 */
public class ListData {

	private static JSONArray jsonQuery(final Query q, final String returnField) {

		JSONArray jsonArray = new JSONArray();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {

			jsonArray.put(ConvertEntityToJson.getJson(result, returnField));
		}

		return jsonArray;
	}

	public static JSONArray jsonList(final String kind, final String sortField,
			final SortDirection sortDirection, final String returnField) {

		Query q = new Query(kind);

		jsonListSort(q, sortField, sortDirection);

		return jsonQuery(q, returnField);
	}

	public static JSONArray jsonListFilter(String kind, String filterField,
			String filterValue, String sortField,
			final SortDirection sortDirection, final String returnField,
			final FilterOperator operation) {

		Query q = new Query(kind);

		if (filterField != null) {

			q.setFilter(new FilterPredicate(filterField, operation, filterValue));
		}

		jsonListSort(q, sortField, sortDirection);

		return jsonQuery(q, returnField);
	}

	public static JSONArray jsonMultipleListFilter(String kind,
			String filterField1, String filterField2, String filterField3,
			String filterField4, String filterValue1, String filterValue2,
			String filterValue3, String filterValue4, String sortField,
			SortDirection sortDirection, final String returnField) {

		Query q = new Query(kind);

		if (filterField1 != null) {

			q.setFilter(new FilterPredicate(
					filterField1,
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					filterValue1));
		}

		if (filterField2 != null) {

			q.setFilter(new FilterPredicate(
					filterField2,
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					filterValue2));
		}

		if (filterField3 != null) {

			q.setFilter(new FilterPredicate(
					filterField3,
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					filterValue3));
		}

		if (filterField4 != null) {

			q.setFilter(new FilterPredicate(
					filterField4,
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					filterValue4));
		}

		jsonListSort(q, sortField, sortDirection);

		return jsonQuery(q, returnField);
	}

	public static JSONArray jsonListFilterKey(String kind,
			String filterKeyField, String filterKeyKind, String filterKeyValue,
			String sortField, SortDirection sortDirection, final String returnField) {

		Query q = new Query(kind);

		if (filterKeyField != null) {

			q.setFilter(new FilterPredicate(
					filterKeyField,
					com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
					KeyFactory.createKey(filterKeyKind, filterKeyValue)));
		}

		jsonListSort(q, sortField, sortDirection);

		return jsonQuery(q, returnField);
	}

	public static void jsonListSort(Query q, String sortField,
			SortDirection sortDirection) {

		if (sortField != null) {

			if (sortDirection != null) {

				q.addSort(sortField, SortDirection.DESCENDING);

			} else {

				q.addSort(sortField);
			}
		}
	}

}