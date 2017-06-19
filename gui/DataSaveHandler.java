/*
	Author : Kangmin Kim
*/

package code.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.fileIO.FileIO;
import code.model.KeyBricksModel;

public class DataSaveHandler implements ActionListener {

	KeyBricksModel _keyBrickModel;

	public DataSaveHandler(KeyBricksModel keyBricksModel) {
		this._keyBrickModel = keyBricksModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("save button clicked");
		FileIO.writeStringToFile("kangmink.kbr", _keyBrickModel.getDataForSaving());
	}
}
