package com.aol.mobile.enumberreader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aol.mobile.enumberreader.model.ENumber;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class ENumbersParser {

	private static final String TAG_ENUMBERS = "enumbers";
	private static final String TAG_NAME = "name";
	private static final String TAG_NUMBER = "number";
	private static final String TAG_URL = "url";
	
	private Context context;
	private JSONArray enumbers = null;
	
	public ENumbersParser(Context context){
		this.context = context;
	}
	
	public List<ENumber> parseFromAssets(String filename){
		
		AssetManager assets = context.getAssets();
		InputStream input = null;
		
		try {
			input = assets.open(filename);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if (input != null){
			return parse(input);
		}
	
		return null;
	}
	
	public List<ENumber> parseFromUrl(String url){
		
		InputStream input = null;
		
		// Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            input = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        if (input != null){
        	return parse(input);
        }
        
		return null;
	}
	
	
	private List<ENumber> parse(InputStream input){
		
		String jsonStr = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
	                input, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        input.close();
	        jsonStr = sb.toString();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if (jsonStr != null){
			
			ArrayList<ENumber> enumbersArray = new ArrayList<ENumber>();
			
			// try parse the string to a JSON object
	        try {
	        	JSONObject jsonObj = new JSONObject(jsonStr);
	        	
	        	enumbers =  jsonObj.getJSONArray(TAG_ENUMBERS);
	        	 
	        	for (int i = 0; i < enumbers.length(); i ++){
	        		JSONObject enumberJSON = enumbers.optJSONObject(i);
	        		if (enumberJSON != null){
	        			
	        			String name = enumberJSON.optString(TAG_NAME);
	        			String number = enumberJSON.optString(TAG_NUMBER);
	        			String url = enumberJSON.optString(TAG_URL);
	        			
	        			ENumber enumberObj = new ENumber(name, number, url);
	        			enumbersArray.add(enumberObj);
	        			
	        		}
	        	}
	        	return enumbersArray;
	        	
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	        
		}
		
		return null;
	}
	
}
