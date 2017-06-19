/*
	Author : Kangmin Kim
*/

package code.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import code.fileIO.FileIO;
import code.model.tile.ITile;
import code.model.tile.PlaceHolderTile;
import code.model.tile.RealTile;

public class KeyBricksModel {

	
	FileIO _fileIO;

	public static final int COLS = 5;
	public static final int ROWS = COLS;

	private Observer _observer; // who to notify when the model changes

	private ArrayList<ArrayList<ITile>> _board; // a representation of the board
												// (non-graphical)

	private ArrayList<Character> _letters;

	public KeyBricksModel() {

		_observer = null;

		initializeLetters();
		// initializeBoard();

		System.out.println("CheckIFE : " + FileIO.CHECK_IF_FILE_EXSISTS);
		if (FileIO.CHECK_IF_FILE_EXSISTS) {
			String savedGameData = FileIO.readFileToString(FileIO.FILE_NAME_YOU_WANT);

			ArrayList<Character> splitToCharArr = new ArrayList<Character>();

			for (int i = 0; i < savedGameData.length(); i++) {
				splitToCharArr.add(savedGameData.charAt(i));
			}
			setAsSavedData(splitToCharArr);
		} else {
			initializeBoard();
		}
		// String savedGameData =
		// FileIO.readFileToString(_fileNameWithSavedData);
		//
		// ArrayList<Character> splitToCharArr = new ArrayList<Character>();
		//
		// for (int i = 0; i < savedGameData.length(); i++) {
		// splitToCharArr.add(savedGameData.charAt(i));
		// }
		// setAsSavedData(splitToCharArr);

		// System.out.println("saved file call? : " +
		// (FileIO.readFileToString(_fileNameWithSavedData)));
		// if (FileIO.readFileToString(_fileNameWithSavedData) == "") {
		// initializeBoard();
		// } else {
		// String savedGameData =
		// FileIO.readFileToString(_fileNameWithSavedData);
		//
		// ArrayList<Character> splitToCharArr = new ArrayList<Character>();
		//
		// for (int i = 0; i < savedGameData.length(); i++) {
		// splitToCharArr.add(savedGameData.charAt(i));
		// }
		// setAsSavedData(splitToCharArr);
		// }

	}

	public String getDataForSaving() {
		String toReturn = "";
		for (int i = 0; i < _board.size(); i++) {
			for (int j = 0; j < _board.get(i).size(); j++) {
				System.out.print(_board.get(i).get(j) + " ");
				toReturn += _board.get(i).get(j);
			}
		}
		return toReturn;
	}

	public void setAsSavedData(ArrayList<Character> splitToCharArr) {
		_board = new ArrayList<ArrayList<ITile>>();

		// place tiles as saved data
		for (int x = 0; x < _letters.size(); x++) {
			System.out.print(_letters.get(x));
		}
		for (int i = 0; i < COLS; i++) {
			ArrayList<ITile> col = new ArrayList<ITile>();
			_board.add(col);
			for (int j = 0; j < ROWS; j++) {
				if (splitToCharArr.get(1).equals('K')) {
					PlaceHolderTile t = new PlaceHolderTile();
					splitToCharArr.remove(1);
					splitToCharArr.remove(0);
					col.add(t);

				} else if (!(splitToCharArr.get(1).equals('K'))) {
					RealTile t = new RealTile(ColorUtility.char2color(splitToCharArr.remove(1)));
					_letters.remove(splitToCharArr.get(0));
					t.setCharacter(splitToCharArr.remove(0));
					if (t.getCharacter() != ' ') {
						_letters.remove(t.getCharacter());
					}
					col.add(t);
				}
			}
		}
		for (int x = 0; x < _letters.size(); x++) {
			System.out.print(_letters.get(x));
		}
		ensureEnoughTilesWithCharacters();
	}

	public void initializeBoard() {
		_board = new ArrayList<ArrayList<ITile>>();

		for (int c = 0; c < COLS; c++) {
			ArrayList<ITile> col = new ArrayList<ITile>();
			_board.add(col);
			for (int r = 0; r < ROWS; r++) {

				RealTile t = new RealTile(ColorUtility.getRandomColor());
				col.add(t);
			}
		}
		ensureEnoughTilesWithCharacters();
	}

	public void initializeLetters() {
		_letters = new ArrayList<Character>();
		for (char c = 'a'; c <= 'z'; c = (char) (c + 1)) {
			_letters.add(c);
		}
	}

	public void setObserver(Observer obs) {
		_observer = obs;
	}

	public void gameStateChanged() {
		if (_observer != null) {
			_observer.update();
		}
	}

	public ITile get(int col, int row) {
		if (col < 0 || col >= COLS) {
			return null;
		}
		if (row < 0 || row >= ROWS) {
			return null;
		}
		return _board.get(col).get(row);
	}

	public void remove(char ch) {
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				ITile t = get(c, r);
				if (t.isRealTile()) {
					if (ch == t.getCharacter()) {
						removeAdjacentSameColorTiles(t);

						ensureEnoughTilesWithCharacters();
						gameStateChanged();
						return;
					}
				}
			}
		}
	}

	public void shift() {
		ArrayList<ArrayList<ITile>> isPlaceHolderTileLst = new ArrayList<ArrayList<ITile>>();

		for (int colAt = 0; colAt < COLS - 1; colAt++) {
			ArrayList<ITile> placeHolderArr = new ArrayList<ITile>();
			for (int rowAt = 0; rowAt < ROWS; rowAt++) {
				ITile t = _board.get(colAt).get(rowAt);

				if (t.isRealTile() == false) {
					placeHolderArr.add(t);
				}
			}
			if (placeHolderArr.size() == ROWS) {
				isPlaceHolderTileLst.add(placeHolderArr);
			}
		}
		System.out.println("# of PlaceHolderTilesCol : " + isPlaceHolderTileLst.size());
		for (int i = 0; i < isPlaceHolderTileLst.size(); i++) {
			_board.remove(isPlaceHolderTileLst.get(i));
			_board.add((COLS - 1), isPlaceHolderTileLst.get(i));
		}
		// for (int i = 0; i < isPHTLst.size(); i++) {
		// _board.add((_board.size() - i), isPHTLst.get(i));
		// }

	}

	public void removeAdjacentSameColorTiles(ITile tile) {
		ArrayList<ITile> toBeChecked = new ArrayList<ITile>();
		toBeChecked.add(tile);
		HashSet<ITile> toBeRemoved = new HashSet<ITile>();
		// STAGE 1: find tiles that need to be removed
		while (!toBeChecked.isEmpty()) {
			ITile t = toBeChecked.remove(0);
			toBeRemoved.add(t);
			Point p = locationOfTile(t);
			int c = p.x;
			int r = p.y;
			checkTile(c, r - 1, toBeChecked, toBeRemoved, t);
			checkTile(c, r + 1, toBeChecked, toBeRemoved, t);
			checkTile(c - 1, r, toBeChecked, toBeRemoved, t);
			checkTile(c + 1, r, toBeChecked, toBeRemoved, t);
		}
		// STAGE 2: remove them
		for (ITile t : toBeRemoved) {
			removeFromBoard(t);
		}
		shift();
	}

	public void ensureEnoughTilesWithCharacters() {
		ArrayList<RealTile> allAndOnlyRealTiles; // all 'real' tiles on the
													// board
		allAndOnlyRealTiles = allAndOnlyRealTiles();

		// check if game is over
		if (allAndOnlyRealTiles().size() == 0) {
			System.out.println("Game over!");
			System.exit(0);
		}

		// count how many tiles have a letter
		int howManyTilesWithLetters = 0;
		for (RealTile t : allAndOnlyRealTiles) {
			if (t.getCharacter() != ' ') {
				howManyTilesWithLetters = howManyTilesWithLetters + 1;
			}
		}

		// make sure new letters are placed randomly
		Collections.shuffle(allAndOnlyRealTiles);

		// replenish the supply of letter tiles on board
		int numberOfLettersWeWantToAddToBoard = COLS - howManyTilesWithLetters;
		int numberOfPlaceHolderTilesOnBoard = allAndOnlyRealTiles.size() - howManyTilesWithLetters;
		int maximumNumberOfLettersWeCanAddToBoard = Math.min(numberOfLettersWeWantToAddToBoard,
				numberOfPlaceHolderTilesOnBoard);
		for (int i = 0; i < maximumNumberOfLettersWeCanAddToBoard; i = i + 1) {
			RealTile t = getRealTileWithoutLetter(allAndOnlyRealTiles);
			t.setCharacter(_letters.remove(0));
		}
	}

	public RealTile getRealTileWithoutLetter(ArrayList<RealTile> tiles) {
		for (RealTile t : tiles) {
			if (t.getCharacter() == ' ') {
				return t;
			}
		}
		return null; // this should never happen
	}

	// find all the RealTiles on the board, return as an ArrayList
	public ArrayList<RealTile> allAndOnlyRealTiles() {
		ArrayList<RealTile> allAndOnlyRealTiles; // all 'real' tiles on the
													// board
		allAndOnlyRealTiles = new ArrayList<RealTile>();
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				ITile t = get(c, r);
				if (t.isRealTile()) {
					allAndOnlyRealTiles.add((RealTile) t); // add all and only
															// RealTiles to
															// collection
				}
			}
		}
		return allAndOnlyRealTiles;
	}

	public Point locationOfTile(ITile t) {
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				if (t == get(c, r)) { // '==' is correct here - we want to find
										// exactly the tile t
					return new Point(c, r);
				}
			}
		}
		return null;
	}

	public void checkTile(int c, int r, ArrayList<ITile> toBeChecked, HashSet<ITile> toBeRemoved, ITile target) {
		ITile tileToBeChecked = get(c, r);
		if (target.matches(tileToBeChecked)) {
			if (!toBeRemoved.contains(tileToBeChecked)) {
				toBeChecked.add(tileToBeChecked);
			}
		}
	}

	public void removeFromBoard(ITile target) {
		Point p = locationOfTile(target);
		int c = p.x;
		int r = p.y;
		_board.get(c).remove(r); // remove a RealTile
		_board.get(c).add(0, new PlaceHolderTile()); // add a PlaceHolder tile
														// at top of column
		System.out.println("**********************************");
		for (int i = 0; i < _board.size(); i++) {
			System.out.print(i + "COL : ");
			for (int j = 0; j < _board.get(i).size(); j++) {
				System.out.print(_board.get(i).get(j) + " ");
			}
			System.out.print(" / size : " + _board.get(i).size());
			System.out.println();
		}
		System.out.println("**************** " + _board.size() + " ***************");

	}
}