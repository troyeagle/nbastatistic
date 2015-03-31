package njuse.ffff.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class SvgConverter {

	/**
	 * 将指定目录下的svg文件转换为png文件储存在目标目录下
	 * 
	 * @param svgFolderPath
	 * @param pngFolderPath
	 * @throws IOException
	 * @throws TranscoderException
	 */
	public static void convertSvgsToPngs(String svgFolderPath, String pngFolderPath)
			throws IOException, TranscoderException {
		File svgFolder = new File(svgFolderPath);
		File pngFolder = new File(pngFolderPath);

		if (!pngFolder.exists()) {	// 是否必要？
			pngFolder.mkdirs();
		}

		// 转化
		File[] files = svgFolder.listFiles();
		for (File svgFile : files) {
			String name = svgFile.getName();
			int end = name.lastIndexOf(".svg");	// 判断后缀名
			if (end > 0) {	// 是svg文件
				String pngFileName = pngFolderPath + "/" + name.substring(0, end)
						+ ".png";

				SvgConverter.convertToPng(svgFile.getPath(), pngFileName);
			}
		}
	}

	/**
	 * 将svgCode转换成png文件，直接输出到流中
	 * 
	 * @param svgCode
	 *            svg代码
	 * @param outputStream
	 *            输出流
	 * @throws TranscoderException
	 *             异常
	 * @throws IOException
	 *             io异常
	 */
	public static void convertToPng(String svgCode, OutputStream outputStream)
			throws TranscoderException, IOException {
		try {
			byte[] bytes = svgCode.getBytes("utf-8");
			PNGTranscoder t = new PNGTranscoder();
			TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
			TranscoderOutput output = new TranscoderOutput(outputStream);
			t.transcode(input, output);
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * svg文件转为png文件
	 * 
	 * @param svg
	 * @param png
	 * @throws IOException
	 * @throws TranscoderException
	 */
	public static void convertToPng(File svg, File png)
			throws IOException, TranscoderException
	{
		InputStream in = new FileInputStream(svg);
		OutputStream out = new FileOutputStream(png);
		out = new BufferedOutputStream(out);
		convertToPng(in, out);
	}

	public static void convertToPng(InputStream in, OutputStream out)
			throws IOException, TranscoderException
	{
		Transcoder transcoder = new PNGTranscoder();
		try {
			TranscoderInput input = new TranscoderInput(in);
			try {
				TranscoderOutput output = new TranscoderOutput(out);
				transcoder.transcode(input, output);
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

	/**
	 * 将指定路径的svg文件转为png文件储存在目标位置，若目标文件已存在则跳过
	 * 
	 * @param svgPath
	 * @param pngPath
	 * @throws IOException
	 * @throws TranscoderException
	 */
	public static void convertToPng(String svgPath, String pngPath)
			throws IOException, TranscoderException {
		File pngFile = new File(pngPath);

		// 若目标文件不存在则执行转换
		if (!pngFile.exists())
			convertToPng(new File(svgPath), pngFile);
	}

}
