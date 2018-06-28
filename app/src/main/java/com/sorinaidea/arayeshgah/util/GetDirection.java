package com.sorinaidea.arayeshgah.util;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr-code on 6/28/2018.
 */

public class GetDirection extends AsyncTask<String, String, String> {



    String origin = "35.311308,46.995735";
    String destination = "35.322308,46.555735";
    private FragmentActivity fragmentActivity;
    private ProgressDialog dialog;
    private List<LatLng> points;
    private GoogleMap map;

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public GetDirection(FragmentActivity fragmentActivity, GoogleMap map) {
        this.setFragmentActivity(fragmentActivity);
        this.setMap(map);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(fragmentActivity);
        dialog.setMessage("Drawing the route, please wait!");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    protected String doInBackground(String... args) {
        String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&sensor=false";
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection httpconn = (HttpURLConnection) url
                    .openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()),
                        8192);
                String strLine = null;

                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }

            String jsonOutput = response.toString();

            JSONObject jsonObject = new JSONObject(jsonOutput);

            // routesArray contains ALL routes
            JSONArray routesArray = jsonObject.getJSONArray("routes");
            // Grab the first route
            JSONObject route = routesArray.getJSONObject(0);

            JSONObject poly = route.getJSONObject("overview_polyline");
            String polyline = poly.getString("points");
            points = decodePoly(polyline);

        } catch (Exception e) {

        }

        return null;

    }

    protected void onPostExecute(String file_url) {
        for (int i = 0; i < points.size() - 1; i++) {
            LatLng src = points.get(i);
            LatLng dest = points.get(i + 1);
            try {
                //here is where it will draw the polyline in your map
                Polyline line = map.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(2).color(Color.RED).geodesic(true));
            } catch (NullPointerException e) {
                Log.e("Error", "NullPointerException onPostExecute: " + e.toString());
            } catch (Exception e2) {
                Log.e("Error", "Exception onPostExecute: " + e2.toString());
            }

        }
        dialog.dismiss();

    }


    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}

