package com.exlservices.DistanceFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exlservices.DistanceFinder.model.DrivingDistanceAndTime;

/**
 * @author Manas Gupta
 * @since 21st Oct, 2015
 * @version 1.0
 */
public class DistanceCalculator {

	public List<DrivingDistanceAndTime> calculateDistance(
			TreeMap<Double, Double> mapOfFileOne,
			TreeMap<Double, Double> mapOfFileTwo) {

		List<DrivingDistanceAndTime> finalList = new ArrayList<DrivingDistanceAndTime>();

		for (Double latOfFileTwo : mapOfFileTwo.keySet()) {
			for (Double latOfFileOne : mapOfFileOne.keySet()) {
				try {
					String directionsURL = getGeneratedDirectionsURL(
							mapOfFileOne.get(latOfFileOne),
							mapOfFileTwo.get(latOfFileTwo), latOfFileOne,
							latOfFileTwo);
					String directionJSONString = getJSONString(directionsURL);

					JSONObject directionsObj = new JSONObject(
							directionJSONString);
					JSONArray routesArray = directionsObj
							.getJSONArray("routes");
					JSONArray legsArray = routesArray.getJSONObject(0)
							.getJSONArray("legs");
					JSONObject distanceObject = legsArray.getJSONObject(0)
							.getJSONObject("distance");
					String distanceValue = distanceObject.getString("text");
					JSONObject durationObject = legsArray.getJSONObject(0)
							.getJSONObject("duration");
					String durationValue = durationObject.getString("text");

					String sourceAddress = getFormattedAddress(getJSONString(getGeneratedLocationURL(
							latOfFileTwo, mapOfFileTwo.get(latOfFileTwo))));
					String destinationAddress = getFormattedAddress(getJSONString(getGeneratedLocationURL(
							latOfFileOne, mapOfFileOne.get(latOfFileOne))));

					DrivingDistanceAndTime listObj = new DrivingDistanceAndTime(
							sourceAddress, destinationAddress, distanceValue,
							durationValue);
					finalList.add(listObj);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return finalList;
	}

	private String getFormattedAddress(String jsonString) {
		JSONObject obj = new JSONObject(jsonString);
		JSONArray resultsArray = obj.getJSONArray("results");
		return resultsArray.getJSONObject(0).getString("formatted_address");
	}

	private String getGeneratedLocationURL(Double latitude, Double longitude) {
		StringBuilder locationURL = new StringBuilder(
				"http://maps.googleapis.com/maps/api/geocode/json?");
		List<BasicNameValuePair> locationParams = new ArrayList<BasicNameValuePair>();
		locationParams.add(new BasicNameValuePair("latlng", latitude + ","
				+ longitude));
		locationParams.add(new BasicNameValuePair("sensor", "true"));
		String locationParamsString = URLEncodedUtils.format(locationParams,
				"utf-8");
		locationURL.append(locationParamsString);
		return locationURL.toString();
	}

	private String getGeneratedDirectionsURL(Double lngOfFileOne,
			Double lngOfFileTwo, Double latOfFileOne, Double latOfFileTwo) {
		StringBuilder directionsURL = new StringBuilder(
				"http://maps.googleapis.com/maps/api/directions/json?");
		List<BasicNameValuePair> directionParams = new ArrayList<BasicNameValuePair>();
		directionParams.add(new BasicNameValuePair("origin", latOfFileTwo + ","
				+ lngOfFileTwo));
		directionParams.add(new BasicNameValuePair("destination", latOfFileOne
				+ "," + lngOfFileOne));
		directionParams.add(new BasicNameValuePair("sensor", "false"));
		String directionParamsString = URLEncodedUtils.format(directionParams,
				"utf-8");
		directionsURL.append(directionParamsString);
		return directionsURL.toString();
	}

	private String getJSONString(String url) throws IOException,
			ClientProtocolException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		String jsonString = EntityUtils.toString(response.getEntity());
		return jsonString;
	}
}
