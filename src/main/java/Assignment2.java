import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment2 {
	public static void main(String[] args) throws IOException {
		try (var file = Files.newBufferedReader(Path.of("image.dat"))) {
			file.readLine();
			var pixels = readFile(file);
		}
	}

    // file must from the second line, where the RGB values begin.
	static Integer[] readFile(Readable file) {
		ArrayList<Integer> pixels = new ArrayList<>();

		var scanner = new Scanner(file);
		scanner.useDelimiter("( |\\R)");
		while (scanner.hasNext()) {
			pixels.add(parseColors(scanner.next()));
		}

		return pixels.toArray(new Integer[]{});
    }

    static Integer parseColors(String s) {
		var values = s.split(",");
		int r = Integer.parseInt(values[0]);
		int g = Integer.parseInt(values[1]);
		int b = Integer.parseInt(values[2]);
		return ((r & 0xff) << 0x10) | ((g & 0xff) << 0x8) | (b & 0xff);
	}
}
