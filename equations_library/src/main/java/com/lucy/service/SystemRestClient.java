/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.lucy.service;

import com.fasterxml.jackson.core.util.JacksonFeature;
import com.lucy.jsf.util.JsfUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import jakarta.inject.Named;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author PC
 */
@Named(value = "systemRestClient")
@ApplicationScoped
public class SystemRestClient {

    private final Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    // Create a main JSON object to hold the IDs array and updated entity atribute/field
    private JSONObject mainJSON = new JSONObject();
    // Create a JSON object for the updated entity atribute/field
    private JSONObject updatedField = new JSONObject();
    // Create a JSON array for the list of IDs
    private JSONArray idsArray = new JSONArray();
    private String jsonString;

    public JSONObject getMainJSON() {
        return mainJSON;
    }

    public void setMainJSON(JSONObject mainJSON) {
        this.mainJSON = mainJSON;
    }

    public JSONObject getUpdatedField() {
        return updatedField;
    }

    public void setUpdatedField(JSONObject updatedField) {
        this.updatedField = updatedField;
    }

    public JSONArray getIdsArray() {
        return idsArray;
    }

    public void setIdsArray(JSONArray idsArray) {
        this.idsArray = idsArray;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    /**
     * Creates a new instance of SystemRestClient
     *
     * @param method
     */
    public void createOrUpdate(HttpMethod method) {
        try {
            Response response;
            if (method == null) {
                throw new IllegalArgumentException("Invalid HTTP method");
            } else {
                switch (method) {
                    case POST:
                        // Convert the mainJSON to its string representation
                        String baseURL = "http://lucy.et:46383/lucy/api/company/create"; // Replace with System URL
                        response = client.target(baseURL)
                                .request(MediaType.APPLICATION_JSON)
                                .post(Entity.json(jsonString));
                        break;
                    case PUT:
                        baseURL = "http://lucy.et:46383/lucy/api/company/edit"; // Replace with System URL
                        response = client.target(baseURL)
                                .request(MediaType.APPLICATION_JSON)
                                .put(Entity.json(mainJSON.toString()));
                        break;
                    case DELETE:
                        baseURL = "http://lucy.et:46383/lucy/api/company/delete"; // Replace with System URL
                        response = client.target(baseURL)
                                .request(MediaType.APPLICATION_JSON)
                                .delete();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid HTTP method");
                }
            }

            // Handle the response
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                JsfUtil.addErrorMessage("Saved");
            } else {
                JsfUtil.addErrorMessage("Failed: " + response.getStatus());
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        if (e.getMessage() != null && e.getMessage().toLowerCase().contains("timed out")) {
            JsfUtil.addErrorMessage("Connection timed out");
        } else {
            e.printStackTrace();
        }
    }

    public enum HttpMethod {
        POST, PUT, DELETE
    }

    public Map<Integer, String> findAll() {
        try {
            Map<Integer, String> mList = new HashMap<>();
            String baseURL = "http://lucy.et:46383/lucy/api/company/all"; // Replace with System URL
            WebTarget target = client.target(baseURL);
            Response response = target.request(MediaType.APPLICATION_JSON).get();
            int statusCode = response.getStatus();

            if (statusCode == 200) {
                String jsonResponse = response.readEntity(String.class);
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject mapData = jsonObject.getJSONObject("mapData");
                // Iterate through the keys in the JSON object and populate the map
                for (String key : mapData.keySet()) {
                    int company = Integer.parseInt(key);
                    String description = mapData.getString(key);
                    mList.put(company, description);
                }
                return mList;
            } else if (statusCode == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                JsfUtil.addErrorMessage("An error occurred, please contact the product vendor");
            } else {
                // Handle other response statuses if needed
                JsfUtil.addErrorMessage("Unexpected response status: " + statusCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("An error occurred while parsing the response.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("An unexpected error occurred.");
        }
        return null;
    }

    public Map<Integer, String> mapData() {
        try {
            String urlStr = "http://lucy.et:46383/lucy/api/company/all";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuilder responseData = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    responseData.append(line);
                }
            }

            String response = responseData.toString();
            JSONObject jsonObject = new JSONObject(response);
            JSONObject companyMap = jsonObject.getJSONObject("mapData");

            // Map to store company data
            Map<Integer, String> mList = new HashMap<>();

            // Iterate through the keys in the JSON object and populate the map
            for (String key : companyMap.keySet()) {
                int companyId = Integer.parseInt(key);
                String companyName = companyMap.getString(key);
                mList.put(companyId, companyName);
            }
            return mList;

        } catch (IOException e) {
            e.printStackTrace();
            // Handle connection or IO exception
        }

        return null;
    }

}
