package com.streep.EEMODLOADER.utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class JsonFunctionLoader {

	public static Map<String, JsonFile> functions = new HashMap<String, JsonFile>();
	
	public static void LoadAllFunctions() {
		List<String> filestoLoad = getFileNames(new ArrayList<String>(),Paths.get(EEMODLOADER.plugin.getDataFolder() + "/Functions"), "");
		for(String file : filestoLoad) {
			String key = "Functions/" + file.replaceAll(".json", "");
			String jsonFilePath = "Functions/" + file;
			EEMODLOADER.plugin.getLogger().info(key + " //// " + jsonFilePath);
			functions.put(key, new JsonFile(jsonFilePath));
		}
	}
	
	private static List<String> getFileNames(List<String> fileNames, Path dir, String folder) {
	    try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
	        for (Path path : stream) {
	            if(path.toFile().isDirectory()) {
	                getFileNames(fileNames, path, folder + path.getFileName() + "/");
	            } else {
	                fileNames.add(folder + path.getFileName());
	            }
	        }
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	    return fileNames;
	} 
}
