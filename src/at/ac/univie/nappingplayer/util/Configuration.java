// Copyright (C) 2013 Werner Robitza
//
// This file is part of NappingPlayer.
//
// NappingPlayer is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version. 
//
// NappingPlayer is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with NappingPlayer.  If not, see <http://www.gnu.org/licenses/>.
//
// NappingPlayer was written at the University of Vienna by Werner Robitza.

package at.ac.univie.nappingplayer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public abstract class Configuration {

	private static final String TAG = Configuration.class.getSimpleName();

	private static File sSDcard = null;
	private static File sFolderVideos = null;
	private static File sFolderLogs = null;
	private static int mScreenWidth;
	private static int mScreenHeight;
	
	/**
	 * Path of the folder in which sVideos are stored, relative to the SD card
	 * root with a trailing slash. If it does not exist, it will be created
	 * automatically on the SD card.
	 */
	public static final String PATH_VIDEOS 	= new String("NappingMovies/");
	public static final String PATH_LOGS 	= new String("NappingLogs/");
	
	/**List of Files in Video Directory */
	private static ArrayList<File> sVideos;

	/**
	 * Tries to initialize the SD card, obtain the file handles and then create
	 * folders if they don't exist already.
	 */
	public static void initialize(Context ctx) throws Exception {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			sSDcard = Environment.getExternalStorageDirectory();
			
			try {
				// create the data and video directory if they don't exist already
				sFolderVideos = new File(sSDcard, PATH_VIDEOS);
				sFolderVideos.mkdirs();
				sFolderLogs = new File(sSDcard, PATH_LOGS);
				sFolderLogs.mkdirs();
				
				// get the list of video files
				// TODO: Refine based on file extensions, invisibles, et cetera.
				sVideos = new ArrayList<File>(Arrays.asList(sFolderVideos.listFiles()));
			} catch (Exception e) {
				Log.e(TAG, "Error while creating directories or fetching files: " + e.toString());
				throw new Exception(e);
			}
		} else {
			Log.e(TAG, "Could not initialize SD card");
			throw new Exception("Could not open SD card!");
		}
		
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
//		FIXME: Fix me for upper API levels
//		http://stackoverflow.com/q/1016896/
//		try { 
//			Point size = new Point();
//			display.getSize(size); 
//			mScreenWidth = size.x; 
//			mScreenHeight = size.y; 
//		} catch (NoSuchMethodError e) { 
			mScreenWidth = display.getWidth(); 
			mScreenHeight = display.getHeight(); 
//		}
		
		mScreenWidth = display.getWidth();
		mScreenHeight = display.getHeight();
		
	}
	
	public static File getLogFolder() {
		return sFolderLogs;
	}
	
	public static int getWidth() {
		return mScreenWidth;
	}
	
	public static int getHeight() {
		return mScreenHeight;
	}
	
	public static ArrayList<File> getVideos() {
		return sVideos;
	}
	
	
	
}
