package edu.pnu;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@SpringBootTest
public class BoardInitialize {

	@Autowired
	BoardRepository boardRepo;

	@Test
	public void doWork() {
		for (int i = 95; i <= 134; i++) {
			boardRepo.save(Board.builder().title("1호선" + i).content(randStr()).author("ㅋㅌㅊㅍ").stationcode(i)
					.likesCount(0).image(colorImg()).build());
		}
	}

	public static String randStr() {
		Random rand = new Random();
		int len = rand.nextInt(50) + 10;
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			int alpha = rand.nextInt(26);
			boolean cap = rand.nextBoolean();
			char c;
			if (cap) {
				c = (char) (alpha + 'A');
			} else {
				c = (char) (alpha + 'a');
			}
			str[i] = c;
		}
		return new String(str);
	}

	public static String randomImg() {
		Random random = new Random();
		int width = random.nextInt(10) + 10; // 이미지의 너비
		int height = random.nextInt(10) + 10; // 이미지의 높이

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 각 픽셀에 랜덤한 색상 값을 할당
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int red = random.nextInt(256);
				int green = random.nextInt(256);
				int blue = random.nextInt(256);
				Color color = new Color(red, green, blue);
				image.setRGB(x, y, color.getRGB());
			}
		}

		// 랜덤 이미지를 파일로 저장
		String filename = randStr() + "randomImage.png";
		try {
			ImageIO.write(image, "png", new File("C:\\Temp\\uploads\\" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filename;
	}

	// ---------------------------------------------------------------------//

	private static double noise(int x, int y, int seed) {
		int n = x + y * 57 + seed;
		n = (n << 13) ^ n;
		double t = (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff;
		return 1.0 - t * 0.000000000931322574615478515625;
	}

	private static double smoothNoise(int x, int y, int seed) {
		double corners = (noise(x - 1, y - 1, seed) + noise(x + 1, y - 1, seed) + noise(x - 1, y + 1, seed)
				+ noise(x + 1, y + 1, seed)) / 16;
		double sides = (noise(x - 1, y, seed) + noise(x + 1, y, seed) + noise(x, y - 1, seed) + noise(x, y + 1, seed))
				/ 8;
		double center = noise(x, y, seed) / 4;
		return corners + sides + center;
	}

	public static String randGoodImg() {
		Random random = new Random();
		int width = random.nextInt(100) + 10; // 이미지의 너비
		int height = random.nextInt(100) + 10;
		int seedR = (int) (Math.random() * Integer.MAX_VALUE);
		int seedG = (int) (Math.random() * Integer.MAX_VALUE);
		int seedB = (int) (Math.random() * Integer.MAX_VALUE);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int red = (int) ((smoothNoise(x / 8, y / 8, seedR) + 1) * 128);
				int green = (int) ((smoothNoise(x / 8, y / 8, seedG) + 1) * 128);
				int blue = (int) ((smoothNoise(x / 8, y / 8, seedB) + 1) * 128);
				Color color = new Color(red, green, blue);
				image.setRGB(x, y, color.getRGB());
			}
		}

		String filename = randStr() + "randomImage.png";
		try {
			ImageIO.write(image, "png", new File("C:\\Temp\\uploads\\" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

	// ---------------------------------------------------//

	public static String colorfulPatternImg() {
		Random random = new Random();
		int width = random.nextInt(400) + 10; // 이미지의 너비
		int height = random.nextInt(400) + 10;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int centerX = width / 2;
		int centerY = height / 2;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// 원형 패턴
				double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
				int colorValue1 = (int) (255 * Math.sin(distanceToCenter / 8.0));

				// 대각선 패턴
				int colorValue2 = (x + y) * 255 / (width + height);

				// 중심부 강조 패턴
				double ratio = distanceToCenter / (Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2)));
				int colorValue3 = (int) (255 * (1 - ratio));

				Color color = new Color(colorValue1, colorValue2, colorValue3);
				image.setRGB(x, y, color.getRGB());
			}
		}

		String filename = randStr() + "patternImage.png";
		try {
			ImageIO.write(image, "png", new File("C:\\Temp\\uploads\\" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

	// -------------현진이 랜덤 이미지 코드--------------------------------
	public static String colorImg() {
		Random random = new Random();
		int width = random.nextInt(400) + 100; // 이미지의 너비
		int height = random.nextInt(400) + 100; // 이미지의 높이

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		Color color = new Color(red, green, blue);
		image.setRGB(0, 0, color.getRGB());
		for (int i = 1; i < width + height; i++) {
			red += random.nextInt(-10, 11);
			if (red > 255)
				red = 255;
			if (red < 0)
				red = 0;
			green += random.nextInt(-10, 11);
			if (green > 255)
				green = 255;
			if (green < 0)
				green = 0;
			blue += random.nextInt(-10, 11);
			if (blue > 255)
				blue = 255;
			if (blue < 0)
				blue = 0;
			color = new Color(red, green, blue);
			for (int j = 0; j < height; j++) {
				if (i - j < width && i - j >= 0)
					image.setRGB(i - j, j, color.getRGB());
			}
		}
		String filename = randStr() + "patternImage.png";
		try {
			ImageIO.write(image, "png", new File("C:\\Temp\\uploads\\" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}
}