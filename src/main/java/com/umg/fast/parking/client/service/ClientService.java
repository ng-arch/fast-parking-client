/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umg.fast.parking.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author bowpi
 */

public class ClientService {
    
    
     public JSONObject postRequest(String uri, JSONObject request) {
        try {
            URL url = new URL(uri);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            String data = request.toString();

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            http.disconnect();
            return getMap(content.toString());
        } catch (Exception ex) {
            System.out.println("exeption " + ex.toString());
        }
        return null;
    }

    private JSONObject getMap(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {

            JSONObject map = mapper.readValue(json, JSONObject.class);

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
