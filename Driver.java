/*
	Author : Kangmin Kim
*/
	
package code.driver;

import java.io.File;

import javax.swing.SwingUtilities;

import code.fileIO.FileIO;
import code.gui.KeyBricksGUI;
import code.model.KeyBricksModel;

public class Driver {
	public static void main(String[] args) {
		File file;
		if (args.length > 0) {
			file = new File(args[0]);

			if (file.exists()) {
				FileIO.CHECK_IF_FILE_EXSISTS = true;
			}
		}
		SwingUtilities.invokeLater(new KeyBricksGUI(new KeyBricksModel()));
	}
}

