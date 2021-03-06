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

package at.ac.univie.nappingplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.util.Log;

/**
 * Abstract class that handles the playlist
 * @author werner
 *
 */
public abstract class VideoPlaylist {

	private static final String TAG = VideoPlaylist.class.getSimpleName();
	
	private static ArrayList<File> 	sFiles;
	static ArrayList<Integer>   sPlayedIds;
	private static int 		sCurrentVideo;
	private static int 		sState;
	
	public static final int STATE_INITIALIZED 	= 1;
	public static final int STATE_PLAYING 		= 2;
	public static final int STATE_FINISHED 		= 3;
	
	// TODO: order implementation for playlist 
	public static final int ORDER_SEQUENTIAL 	= 1;
	public static final int ORDER_RANDOM 		= 2;
	
	public static void initialize(ArrayList<File> files) {
		sFiles 			= files;
		sPlayedIds 		= new ArrayList<Integer>();
		sCurrentVideo 	= 0;
		sState 			= STATE_INITIALIZED;

		// TODO: Sort by name, but add other options later
		Collections.sort(sFiles);
	}
	
	/**
	 * Returns a file reference for the given ID, in the order the files are read (whatever that is)
	 */
	public static File getVideo(int id) {
		return sFiles.get(id);
	}
	
	/**
	 * Returns the video ID for a given file
	 */
	public static int getId(File f) {
		return sFiles.indexOf(f);
	}
	
	/**
	 * Confirms if the playlist has a next video to play
	 * @return
	 */
	public static boolean hasNext() {
		Log.d(TAG, "Has next? Current video id: " + sCurrentVideo + ", size: " + sFiles.size());
		if (sState == STATE_FINISHED) {
			return false;
		} else {
			return (sCurrentVideo < (sFiles.size() - 1));
		}
	}
	
	/**
	 * Sets the pointer to the next video in the playlist
	 * @return True or false depending on whether a next video exists and could be selected
	 */
	public static boolean incrementToNext() {
		sState = STATE_PLAYING;
		sPlayedIds.add(sCurrentVideo);
		if (hasNext()) {
			sCurrentVideo++;
			return true;
		} else {
			sState = STATE_FINISHED;
			return false;
		}
	}
	
	/**
	 * Return a file reference to the video currently being selected
	 */
	public static File getCurrentVideo() {
		if (sState != STATE_FINISHED) {
			sState = STATE_PLAYING;
			return sFiles.get(sCurrentVideo);			
		} else {
			return null;
		}
	}
	
	/**
	 * Return the ID of the video currently being selected.
	 * This ID is used in the napping context and to load the files from the ViewActivity.
	 */
	public static int getCurrentVideoId() {
		if (sState != STATE_FINISHED) {
			sState = STATE_PLAYING;
			return sCurrentVideo;			
		} else {
			return -1;
		}
	}
	
	/**
	 * Resets the playlist to its initial state, keeping all the videos
	 */
	public static void reset() {
		sPlayedIds.clear();
		sCurrentVideo = 0;
		sState = STATE_INITIALIZED;
	}
	
	/**
	 * Returns the internal state of the playlist
	 * @return
	 */
	public static int getState() {
		return sState;
	}
	
}
