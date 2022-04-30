package com.streep.EEMODLOADER.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class JsonFile {

	private File myFile;
	public JSONObject object = new JSONObject();
	
	public JsonFile(String name) {
		myFile = new File(EEMODLOADER.plugin.getDataFolder() + "/" + name + (name.endsWith(".json") ? "" : ".json"));
		if(!myFile.exists())
			try {myFile.createNewFile();} catch(IOException e) {e.printStackTrace();}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(myFile));
			String fileContents = "";
			String line = "";
			while((line = reader.readLine()) != null) {
				fileContents += line + "\n";
			}
			object = new JSONObject(fileContents);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(reader != null)
				try {reader.close();} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void save() {
		try {
			Writer writer = new FileWriter(myFile);
			object.write(writer, 2, 0);
			writer.close();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
