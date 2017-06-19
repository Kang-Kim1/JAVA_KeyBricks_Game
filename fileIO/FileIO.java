/*
	Author : Kangmin Kim
*/
package code.fileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FileIO {

	public static boolean CHECK_IF_FILE_EXSISTS = false;
	public static String FILE_NAME_YOU_WANT = "kangmink.kbr";

	FileIO() {

	}

	/**
	 * This method reads the contents of the indicated file and returns the
	 * contents as a String.
	 * 
	 * @param filename
	 *            - the name of the file whose contents is to be read
	 * @return the contents of the file, as String
	 */
	public static String readFileToString(String filename) {
		System.out.println("read file to file.....");
		Scanner scan = null;
		String contents = "";
		try {
			System.out.println("this is try");
			scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				contents = contents + scan.nextLine();
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filename);
		} finally {
			scan.close();
		}
		return contents;
	}

	/**
	 * This method writes the String toOutput to the indicated file. If the file
	 * does not exist, it is created. If the file exists, it is overwritten.
	 * 
	 * @param filename
	 *            - the name of the file to write to
	 * @param toOutput
	 *            - the String to be written to the file
	 * @return true if writing the file was successful, false otherwise
	 */
	public static boolean writeStringToFile(String filename, String toOutput) {
		System.out.println("writing Str to file.....");
		PrintStream stream = null;
		boolean result = false;
		try {
			stream = new PrintStream(filename);
			stream.format("%s%n", toOutput);
			result = true;
		} catch (FileNotFoundException e) {
			System.err.println("File not found and cannot be created: "
					+ filename);
		} finally {
			stream.close();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("this is main method");
		System.out.println(FileIO.readFileToString(FILE_NAME_YOU_WANT));
		if (args.length == 0) {
		} else {
			CHECK_IF_FILE_EXSISTS = true;
		}
		System.out.println("does file exsists ? : " + CHECK_IF_FILE_EXSISTS);
	}
}
