package main.java.edu.gatech.cs2340.risk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class RiskMockUtil {

	private static Logger log = Logger.getLogger(RiskMockUtil.class);

	private static Gson gson = new Gson();
	private static final String RELATIVE_LOCATION = System
			.getProperty("catalina.base");

	public static String convertObjectToJsonFile(Object obj) {
		return gson.toJson(obj);
	}

	@SuppressWarnings("unchecked")
	public static Object convertJsonFileToObject(String file, Class model) {

		Object obj = null;

		try {
			String fileLoc = "./resources/json/" + file;
			//String fileLoc = RELATIVE_LOCATION + "/webapps/risk/json/" + file;
			BufferedReader br = new BufferedReader(new FileReader(fileLoc));

			// convert the json string back to object
			obj = gson.fromJson(br, model);

			// close the reader
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static void createFileFromJson(String file, Object obj) {

		String json = gson.toJson(obj);

		String fileLoc = RELATIVE_LOCATION + "/webapps/risk/json/" + file;
		log.debug("File location for file: " + fileLoc);
		File newFile = new File(fileLoc);
		if (newFile.exists()) {
			newFile.delete();
		}

		try {
			FileWriter fileWriter = new FileWriter(newFile);
			fileWriter.write(json);
			fileWriter.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			return;

		}
	}

	public static int getFileCountInPackage(String location) {

		String fileLoc = RELATIVE_LOCATION + "/webapps/risk/json/" + location;
		log.debug("FileLoc: " + fileLoc);
		File packageLoc = new File(fileLoc);

		int count = 0;
		if (packageLoc.listFiles() != null) {
			for (File file : packageLoc.listFiles()) {
				if (file.isFile()) {
					count++;
				}
			}
		}
		log.debug("Number of files in " + fileLoc + ": " + count);
		return count;
	}
	
	public static void deleteJsonFromPackage(int playerId) {
		String fileLoc = RELATIVE_LOCATION + "/webapps/risk/json/player";
		File folder = new File(fileLoc);
		for (File file : folder.listFiles()) {
			if (file.getName().equals("player" + playerId)) {
				log.debug("Deleting file " + file.toString());
				file.delete();
			}
		}
	}

	public static void restoreDefaults() {
		String fileLoc = RELATIVE_LOCATION + "/webapps/risk/json/player";
		File folder = new File(fileLoc);
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				log.debug("Deleting file " + file.toString());
				file.delete();
			}
		} else {
			folder.mkdirs();
		}
	}
}
