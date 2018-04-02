package com.home.ferrari.plugin.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.home.ferrari.enums.UploadPictureEnum;

public class QRCodeUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private QRCodeUtil() {
	}

	public static String createQrPic(String requestUrl) {
		try {
			int width = 300;
			int height = 300;
			String format = "png";
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(requestUrl, BarcodeFormat.QR_CODE, width, height, hints);
			String savePicPath = "/home/ferrari/share" + UploadPictureEnum.PICTURE_QR.getPath();
			File outputFile = new File(savePicPath + System.currentTimeMillis() + ".png");
			if(outputFile.exists()) {
				outputFile = new File(savePicPath + System.currentTimeMillis() + ".png");
			}else{
				outputFile.createNewFile();
			}
			QRCodeUtil.writeToFile(bitMatrix, format, outputFile);
			return outputFile.getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

}
