/*
	Author : Kangmin Kim
*/

package code.model.tile;

import java.awt.Color;

public class RealTile extends ITile {
	
	private Color _color;
	private Character _char;
	
	public RealTile(Color c) {
		_color = c;
		_char = ' ';
	
	}
	@Override public void setCharacter(char c) {
		_char = c;
	}
	
	@Override public Character getCharacter() {
		return _char;
	}
	
	@Override public Color getColor() {
		return _color;
	}
	
	@Override public boolean matches(ITile that) {
		return that != null && this._color.equals(that.getColor());
	}
	
	@Override public boolean isRealTile() {
		return true;
	}

}
