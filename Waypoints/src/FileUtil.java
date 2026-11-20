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
}
