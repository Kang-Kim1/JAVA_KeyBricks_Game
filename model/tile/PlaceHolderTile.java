/*
	Author : Kangmin Kim
*/

package code.model.tile;

import java.awt.Color;

public class PlaceHolderTile extends ITile {
	
	@Override public void setCharacter(char c) {}

	@Override public Character getCharacter() {
		return ' ';
	}

	@Override public Color getColor() {
		return Color.BLACK;
	}

	@Override public boolean matches(ITile that) {
		return false;
	}	

	@Override public boolean isRealTile() {
		return false;
	}
}
