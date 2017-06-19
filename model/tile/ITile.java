/*
	Author : Kangmin Kim
*/

package code.model.tile;

import java.awt.Color;

import code.model.ColorUtility;

public abstract class ITile {
	public abstract void setCharacter(char c);
	public abstract Character getCharacter();
	public abstract Color getColor();
	public abstract boolean matches(ITile that);
	public abstract boolean isRealTile();
	@Override public String toString() {
		return ""+getCharacter()+ColorUtility.color2char(getColor());
	}
}