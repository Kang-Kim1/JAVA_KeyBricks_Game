/*
	Author : Kangmin Kim
*/


package code.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import code.model.KeyBricksModel;

public class KeyPressHandler implements KeyListener {

	private KeyBricksModel _model;

	public KeyPressHandler(KeyBricksModel m) {
		_model = m;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("[[[[[[[[[[[[[[[ " + e.getKeyChar() + " ]]]]]]]]]]]]]]]");
		_model.remove(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
