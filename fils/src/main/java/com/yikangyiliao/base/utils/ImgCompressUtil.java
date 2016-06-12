package com.yikangyiliao.base.utils;

import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImgCompressUtil {
	
	private Image img;
	private int width;
	private int height;

	/**
	 * 构造函数
	 * 
	 * @param fileName
	 *            图片全路径
	 */
	public ImgCompressUtil(String fileName) throws IOException {
		File file = new File(fileName);// 读入文件
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到原图宽
		height = img.getHeight(null); // 得到源图长
	}
	
	/**
	 * 构造函数
	 * 
	 * @param fileName
	 *            图片全路径
	 */
	public ImgCompressUtil(InputStream inputStream) throws IOException {
		img = ImageIO.read(inputStream); // 构造Image对象
		width = img.getWidth(null); // 得到原图宽
		height = img.getHeight(null); // 得到源图长
	}

	public ImgCompressUtil(File file) throws IOException {
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到原图宽
		height = img.getHeight(null); // 得到源图长
	}
	/**
	 * 构造函数
	 * 
	 * @param img
	 *            image对象
	 */
	public ImgCompressUtil(Image img) throws IOException {
		this.img = img;
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 * @return
	 */
	public Image resizeFix(int w, int h) throws IOException {
		Image img = null;
		if (width / height > w / h) {
			img = resizeByWidth(w);
		} else {
			img = resizeByWidth(w);
		}
		return img;
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public Image resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		return resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public Image resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		return resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public Image resize(int w, int h) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		// File destFile = new File("D:\\456.jpg");
		// FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// // 可以正常实现bmp、png、gif转jpg
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// encoder.encode(image); // JPEG编码
		// out.close();
		return image;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		System.out.println("开始：" + new Date().toLocaleString());
//		ImgCompress imgCom = new ImgCompress("C:\\Users\\ZFang\\Desktop\\sa.jpg");
//		imgCom.resizeFix(640, 480);
//		System.out.println("结束：" + new Date().toLocaleString());
		
		
		ImageIcon ii = new ImageIcon("E:\\123.jpg");
		ImgCompressUtil imgCom = new ImgCompressUtil(ii.getImage());
		Image resizeFix = imgCom.resizeFix(220, 200);
		ImageIO.write((BufferedImage)resizeFix, "jpg", new File("E:\\ii\\0.jpg"));
		System.out.println("结束：" + new Date().toLocaleString());
	}

}
