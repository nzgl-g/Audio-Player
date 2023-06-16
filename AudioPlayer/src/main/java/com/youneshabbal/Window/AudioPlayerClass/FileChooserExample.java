package com.youneshabbal.Window.AudioPlayerClass;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

public class FileChooserExample {
	private static final String LAST_PATH_KEY = "lastPath";
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		fileChooser();
	}
	
	public static void fileChooser() {
		Preferences preferences = Preferences.userRoot().node(FileChooserExample.class.getName());
		String lastPath = preferences.get(LAST_PATH_KEY, null);
		
		JFileChooser chooser = new JFileChooser();
		
		// Set the initial directory to the last path opened
		if (lastPath != null) {
			chooser.setCurrentDirectory(new File(lastPath));
		} else {
			chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
		}
		
		FileNameExtensionFilter wavFilter = new FileNameExtensionFilter("Only WAV Files", "wav");
		chooser.setFileFilter(wavFilter);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setApproveButtonText("Select");
		
		chooser.setDragEnabled(true);
		int chosenFiles = chooser.showOpenDialog(null);
		if (chosenFiles == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = chooser.getSelectedFiles();
			try {
				File outputPathFile = new File("output.txt"); // Change "output.txt" to your desired file name
				if (outputPathFile.exists()) {
					outputPathFile.delete();
				}
				outputPathFile.createNewFile();
				FileWriter writer = new FileWriter(outputPathFile, true);
				for (File file : selectedFiles) {
					writer.write(file.getAbsolutePath() + System.lineSeparator());
				}
				writer.close();
				
				// Save the last path opened
				String currentPath = chooser.getCurrentDirectory().getAbsolutePath();
				preferences.put(LAST_PATH_KEY, currentPath);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
