/*
	Author : Kangmin Kim
*/

package code.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import code.fileIO.FileIO;
import code.model.KeyBricksModel;
import code.model.Observer;

public class KeyBricksGUI implements Runnable, Observer {

	private KeyBricksModel _model;
	private JPanel _boardPanel;
	private JFrame _window;

	public KeyBricksGUI(KeyBricksModel m) {
		_model = m;
		_model.setObserver(this);
	}

	@Override
	public void run() {
		_window = new JFrame("KeyBricks - Lab 9");
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_window.getContentPane().setLayout(new FlowLayout());
		_window.setFocusable(true);
		_window.setFocusTraversalKeysEnabled(false);

		KeyPressHandler keyHandler = new KeyPressHandler(_model);
		_window.addKeyListener(keyHandler);

		createAndPopulateBoard();

		_window.add(_boardPanel);
		placeSaveButton();
		_window.pack();
		_window.setVisible(true);

	}

	public void placeSaveButton() {
		JButton saveButton = new JButton("Save!");
		saveButton.addActionListener(new DataSaveHandler(_model));
		_window.add(saveButton);
	}

	public void createAndPopulateBoard() {
		_boardPanel = new JPanel();
		_boardPanel.setFocusable(false);
		_boardPanel.setLayout(new GridLayout(KeyBricksModel.ROWS, KeyBricksModel.COLS));

		for (int c = 0; c < KeyBricksModel.COLS; c++) {
			for (int r = 0; r < KeyBricksModel.ROWS; r++) {
				JButton b = new JButton();
				b.setOpaque(true);
				b.setFocusable(false);
				b.setPreferredSize(new Dimension(100, 100)); // so board stays
																// same size
																// regardless of
																// letters
				b.setFont(b.getFont().deriveFont(Font.BOLD, b.getFont().getSize() * 4));
				_boardPanel.add(b);
			}
		}
		update();
	}

	@Override
	public void update() {
		for (int c = 0; c < KeyBricksModel.COLS; c++) {
			for (int r = 0; r < KeyBricksModel.ROWS; r++) {
				JButton b = (JButton) _boardPanel.getComponent(r * KeyBricksModel.COLS + c);
				Character ch = _model.get(c, r).getCharacter();
				b.setBackground(_model.get(c, r).getColor());
				b.setText(ch.toString());
			}
		}
		_window.repaint(); // make sure board is updated visually
	}

}
