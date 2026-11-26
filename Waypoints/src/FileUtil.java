import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {

	// Removed as part of refactor: arraylist to store file
//	public static int countLines(String file) {
//		return countLines(new File(file));
//	}
//
//	public static int countLines(File file) {
//		final Scanner scanner;
//
//		try {
//			scanner = new Scanner(file);
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//
//		int lines = 0;
//		while(scanner.hasNextLine()){
//			final String line = scanner.nextLine();
//			if(line.isBlank())
//				continue;
//			lines++;
//		}
//		scanner.close();
//
//		return lines;
//	}

	public static List<String> getFileAsArray(String file){
		return getFileAsArray(new File(file));
	}

	public static List<String> getFileAsArray(File file){
		final List<String> output = new ArrayList<>();
		final Scanner scanner;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		while(scanner.hasNextLine()) {
			final String text = scanner.nextLine();
			if(text.isBlank())
				continue;
			output.add(text);
		}

		return output;
	}

}
