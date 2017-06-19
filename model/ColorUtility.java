/*
	Author : Kangmin Kim
*/

package code.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class ColorUtility {

	private static ArrayList<Color> COLOR_INVENTORY = new ArrayList<Color>();
	private static Random r = new Random();

	public static char color2char(Color color) {
		if (color.equals(Color.RED)) return 'R';
		if (color.equals(Color.GREEN)) return 'G';
		if (color.equals(Color.BLUE)) return 'B';
		if (color.equals(Color.YELLOW)) return 'Y';
		return 'K'; // assume Color.BLACK otherwise
	}

	public static Color char2color(char ch) {
		if (ch == 'R') return Color.RED;
		if (ch == 'G') return Color.GREEN;
		if (ch == 'B') return Color.BLUE;
		if (ch == 'Y') return Color.YELLOW;
		return Color.BLACK; // assume 'K', mapping to Color.BLACK, otherwise
	}

	public static void initializeColorInventory() {
		COLOR_INVENTORY = new ArrayList<Color>();
		COLOR_INVENTORY.add(Color.RED);
		COLOR_INVENTORY.add(Color.BLUE);
		COLOR_INVENTORY.add(Color.GREEN);
		COLOR_INVENTORY.add(Color.YELLOW);
	}

	public static Color getRandomColor() {
		if (COLOR_INVENTORY.size() == 0) {
			initializeColorInventory();
		}
		return COLOR_INVENTORY.get(r.nextInt(COLOR_INVENTORY.size()));
	}

}
