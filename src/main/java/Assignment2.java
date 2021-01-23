import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment2 {
	public static void main(String[] args) throws IOException {
		Size size;
		Integer[] pixels;
		try (var file = Files.newBufferedReader(Path.of("image.dat"))) {
			size = parseSize(file.readLine());
			pixels = readFile(file);
		}

		var image = newImage(size, pixels);
		ImageIO.write(image, "png", new File("image.png"));
	}

	static Size parseSize(String s) {
		var values = s.split(" ");
		return new Size(Integer.parseInt(values[1]), Integer.parseInt(values[0]));
	}

    // file must start from the second line, where the RGB values begin.
	static Integer[] readFile(Readable file) {
		ArrayList<Integer> pixels = new ArrayList<>();

		var scanner = new Scanner(file);
		scanner.useDelimiter(" \\R?");
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

	static BufferedImage newImage(Size size, Integer[] pixels) {
		assert(size.width * size.height == pixels.length);

		var image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pixels.length; ++i) {
			image.setRGB(i % size.width, i / size.width, pixels[i]);
		}
		return image;
	}
}

class Size {
	int width;
	int height;

	Size(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
