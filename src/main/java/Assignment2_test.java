import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class Assignment2_test {
	public static class newImage {
		@Test
		public void test() {
			var pixels = new Integer[]{0, 1, 2, 3};
			var result = Assignment2.newImage(new Size(2, 2), pixels);
			Assert.assertEquals(2, result.getWidth());
			Assert.assertEquals(2, result.getHeight());
			Assert.assertEquals(0xff000000, result.getRGB(0, 0));
			Assert.assertEquals(0xff000001, result.getRGB(1, 0));
			Assert.assertEquals(0xff000002, result.getRGB(0, 1));
			Assert.assertEquals(0xff000003, result.getRGB(1, 1));
		}
	}

	@RunWith(Parameterized.class)
	public static class readFile {
		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][]{
				{ // 0
					"",
					new Integer[]{},
					false,
				},
				{ // 1
					"0",
					null,
					true,
				},
				{ // 2
					"0,",
					null,
					true,
				},
				{ // 3
					"0,0,0",
					new Integer[]{0x0},
					false,
				},
				{ // 4
					"0,0,0 ",
					new Integer[]{0x0},
					false,
				},
				{ // 5
					"0,0,0 \n",
					new Integer[]{0x0},
					false,
				},
				{ // 6
					"0,0,0 \n",
					new Integer[]{0x0},
					false,
				},
				{ // 7
					"0,0,0 1,1,1",
					new Integer[]{0x0, 0x010101},
					false,
				},
				{ // 8
					"0,0,0 \r\n1,1,1",
					new Integer[]{0x0, 0x010101},
					false,
				},
				{ // 9
					"0,0,0 1,1,1 \n2,2,2",
					new Integer[]{0x0, 0x010101, 0x020202},
					false,
				},
				{ // 10
					"0,0,0 1,1,1 255,255,255",
					new Integer[]{0x0, 0x010101, 0xffffff},
					false,
				},
			});
		}

		@Parameterized.Parameter
		public String fileText;

		@Parameterized.Parameter(1)
		public Integer[] expected;

		@Parameterized.Parameter(2)
		public boolean expectException;

		@Test
		public void test() {
			if (expectException) {
				Assert.assertThrows(
					Exception.class,
					() -> Assignment2.readFile(new StringReader(fileText))
				);
			} else {
				var result = Assignment2.readFile(new StringReader(fileText));
				Assert.assertArrayEquals(expected, result);
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class parseColors {
		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][]{
				{
					"",
					null,
					true,
				},
				{
					"0",
					null,
					true,
				},
				{
					"0,1,2",
					0x000102,
					false,
				},
			});
		}

		@Parameterized.Parameter
		public String s;

		@Parameterized.Parameter(1)
		public Integer expected;

		@Parameterized.Parameter(2)
		public Boolean expectException;

		@Test
		public void test() {
			if (expectException) {
				Assert.assertThrows(Exception.class, () -> Assignment2.parseColors(s));
			} else {
				Assert.assertEquals(expected, Assignment2.parseColors(s));
			}
		}
	}
}
