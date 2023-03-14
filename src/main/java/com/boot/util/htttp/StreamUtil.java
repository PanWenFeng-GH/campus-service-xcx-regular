package com.boot.util.htttp;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @author xiechanglei
 */
public abstract class StreamUtil {
	/**
	 * the default buffer size
	 */
	public static final int DEFAULT_BUFFER_SIZE = 2048;

	/**
	 * copy the buffer from input to output with the default DEFAULT_BUFFER_SIZE(2048)
	 */
	public static void copy(InputStream input, OutputStream output) throws IOException {
		copy(input, output, DEFAULT_BUFFER_SIZE);
	}

	public static void copy(InputStream input, RandomAccessFile rf) throws IOException {
		copy(input, rf, DEFAULT_BUFFER_SIZE);
	}

	private static void copy(InputStream input, RandomAccessFile rf, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int bytesRead = input.read(buf);
		while (bytesRead != -1) {
			rf.write(buf, 0, bytesRead);
			bytesRead = input.read(buf);
		}
	}

	public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int bytesRead = input.read(buf);
		while (bytesRead != -1) {
			output.write(buf, 0, bytesRead);
			bytesRead = input.read(buf);
		}
		output.flush();
	}

	/**
	 * copy 之后关闭
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void copyThenClose(InputStream input, OutputStream output) throws IOException {
		copy(input, output);
		closeStream(input, output);
	}

	/**
	 * 将流读到byte数组中
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream input) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		copy(input, result);
		result.close();
		return result.toByteArray();
	}

	/**
	 * 关闭流
	 * @param closeable
	 */
	public static void closeStream(Closeable... closeable) {
		for (Closeable st : closeable) {
			if (st != null) {
				try {
					st.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}