package com.demovehiclepro.service.report;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class Report {
    public abstract JSONObject showReport() throws JSONException;
}
