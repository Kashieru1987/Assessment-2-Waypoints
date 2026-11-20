import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {

	public static int countLines(String file) {
		return countLines(new File(file));
	}

	public static int countLines(File file) {
		final Scanner scanner;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		int lines = 0;
		while(scanner.hasNextLine()){
			final String line = scanner.nextLine();
			if(line.isBlank())
				continue;
			lines++;
		}
		scanner.close();

		return lines;
	}

	public static String[] getFileAsArray(String file){
		return getFileAsArray(new File(file));
	}

	public static String[] getFileAsArray(File file){
		final String[] output = new String[countLines(file)];
		final Scanner scanner;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		int counter = 0;
		while(scanner.hasNextLine()) {
			final String text = scanner.nextLine();
			if(text.isBlank())
				continue;
			output[counter] = text;
			counter++;
		}

		return output;
	}

}
