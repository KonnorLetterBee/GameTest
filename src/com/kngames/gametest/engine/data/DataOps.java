package com.kngames.gametest.engine.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import android.os.Environment;
import android.util.Log;

public class DataOps {
	
	private static final String TAG = DataOps.class.getSimpleName();
	public static final File APP_DATA_ROOT = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/ResidentEvilDBG");
	public static final char KEY_DATA_DELIM = ';';
	
	public static final int TYPE_FILE = 0;
	public static final int TYPE_FOLDER = 1;
	
	//	loads data from a file and returns a hashtable with the data allocated into String/String pairs
	public static Hashtable<String, String> importFromFile(String relPath) throws IOException {
		Hashtable<String,String> table = new Hashtable<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(getFile(TYPE_FILE, relPath, true)));
		for(String line = reader.readLine(); line != null; line = reader.readLine()) {
			int delimIndex = line.indexOf(KEY_DATA_DELIM);
			String key = line.substring(0, delimIndex);
			String data = line.substring(delimIndex + 1, line.length());
			table.put(key, data);
		}
		reader.close();
		return table;
	}
	
	public static void exportToFile(String dir, String filename, String data) throws IOException {
		File outDir = getFile(TYPE_FILE, dir + filename, true);
		BufferedWriter out = new BufferedWriter(new FileWriter(outDir));
		out.write(data);
		out.close();
	}
	
	public static void exportToFile(String fullPath, String data) throws IOException {
		File outDir = getFile(TYPE_FILE, fullPath, true);
		BufferedWriter out = new BufferedWriter(new FileWriter(outDir));
		out.write(data);
		out.close();
	}
	
	public static void eraseAppExtData() {
		if (deleteDir(APP_DATA_ROOT)) Log.d(TAG, "Successfully deleted external app data");
		else Log.e(TAG, "Error: external data not deleted");
	}
	
	//	deletes all files and subdirectories under dir
	//	returns true if all deletions were successful
	//	if a deletion fails, the method stops attempting to delete and returns false
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) return false;
			}
		}

	    // The directory is now empty so delete it
	    return dir.delete();
	}
	
	//	gets the file at the specified relative path
	//	if appRelative is true, uses APP_DATA_ROOT as the starting relative path
	//	otherwise, takes the path relative to the external storage device provided by Android (usually /mnt/sdcard)
	//	when doing operations within the application's dedicated external storage dir, appRelative should always be true
	public static File getFile (int fileType, String relativePath, boolean appRelative) {
		File dir;
		if (appRelative) dir = new File (APP_DATA_ROOT.getAbsolutePath() + relativePath);
		else {
			File sdCard = Environment.getExternalStorageDirectory();
			dir = new File (sdCard.getAbsolutePath() + relativePath);
		}
		
		boolean success = false;
		switch (fileType) {
		case TYPE_FILE:
			//	actually try to create the file in case it doesn't exist
			try {
				success = dir.createNewFile();
				if (success) Log.d(TAG, String.format("File created: \"%s\"", dir.getAbsolutePath()));
				else Log.e(TAG, String.format("Error: file not created: \"%s\"", dir.getAbsolutePath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case TYPE_FOLDER:
			//	actually try to create the path
			success = dir.mkdirs();
			if (success) Log.d(TAG, String.format("Directory created: \"%s\"", dir.getAbsolutePath()));
			else Log.e(TAG, String.format("Error: directory not created: \"%s\"", dir.getAbsolutePath()));
			break;
		default: 
			Log.e(TAG, String.format("Error: incorrect file type: %d", fileType));
			return null;
		}
		
		return dir;
	}
}
