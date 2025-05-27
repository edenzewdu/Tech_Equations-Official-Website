/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lucy.service;

import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author PC
 */
public class APIModule {

    private JSONObject mapData;

    public JSONObject getMapData() {
        return mapData;
    }

    public void setMapData(JSONObject mapData) {
        this.mapData = mapData;
    }

    public APIModule(JSONObject mapData) {
        this.mapData = mapData;
    }
}
