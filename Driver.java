/*
	Author : Kangmin Kim
*/
	
package code;

import java.io.File;

import javax.swing.SwingUtilities;

import code.fileIO.FileIO;
import code.gui.KeyBricksGUI;
import code.model.KeyBricksModel;

public class Driver {
	public static void main(String[] args) {
		System.out.println(args[0]);
		File file = new File(args[0]);
		if (file.exists()) {
			FileIO.CHECK_IF_FILE_EXSISTS = true;
		}
		SwingUtilities.invokeLater(new KeyBricksGUI(new KeyBricksModel()));
	}

}
