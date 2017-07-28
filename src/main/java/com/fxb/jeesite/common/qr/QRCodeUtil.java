package com.fxb.jeesite.common.qr;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * 
 */
public class QRCodeUtil {

	private static Map<String, Image> picCache = new HashMap<String, Image>();
	// 容错率
	private static final double rate = 0.30;
	private static final ErrorCorrectionLevel E_RATE = ErrorCorrectionLevel.H;
	// 二维码宽
	private static int QRCODE_WIDTH = 400;
	public static int getQRCODE_WIDTH() {
		return QRCODE_WIDTH;
	}
	// 二维码高
	private static int QRCODE_HEIGHT = 400;// 默认宽高相同
	// logo地址
	public static  String LogoPath = System.getProperty("user.dir") + File.separator + "logo.jpeg";
	// 二维码url地址
	public static  String URL = "http://10.6.49.219:8080/jlnuxyk/Fshow_show?";
	// 生成路径
//	private static  String rootPath = System.getProperty("user.dir") + File.separator + "file"+File.separator+"qr";
	private static File rootPath = new File(System.getProperty("user.dir") + File.separator + "file"+File.separator+"qr");
	// 是否debug
	public static final boolean DEBUG = false;

	public static List<String> createQR(int startId, int endId) throws IOException, InterruptedException {
		//出错的二维码集合
		List<String> errorIds = new ArrayList<String>();
		double d_w = Math.sqrt(QRCODE_WIDTH * QRCODE_HEIGHT * rate) / 2; // 计算内部logo宽
		int dw = (int) d_w;
		BlockingQueue queue = new ArrayBlockingQueue(40);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.DAYS, queue);
		int max =endId;
		String str_j = "";
		for (int j = startId-1; j < max; j++) {
			try {
				if (executor.getQueue().size() > 30) {
					Thread.sleep(200);
				}
				str_j = String.valueOf(j + 1);
				System.out.println(str_j);
//				File f1 = new File(rootPath + File.separator + str_j.substring(0, 3) + File.separator + str_j.substring(3, 6));

				//二次验证是否存在
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				File f2 = new File(rootPath, str_j + ".png");
				LogoThread lt = new LogoThread();
				lt.setFile(f2);
				lt.setName(str_j);
				lt.setDw(dw);
				lt.setWidth(QRCODE_WIDTH);
				lt.setHeight(QRCODE_HEIGHT);
				executor.execute(lt);
				Thread.sleep(20);
				if (DEBUG) {
					System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目："
							+ executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
					System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
					errorIds.add("二维码编号为"+j+"描绘时,出现错误。未能完成生成");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage() + "," + str_j);
				errorIds.add("二维码编号为"+j+"出现错误，错误信息为"+e.getMessage());
				Thread.sleep(500);
				continue;
			}
		}

		executor.shutdown();
		return errorIds;
	}

	/**
	 * 创建二维码
	 * 
	 * @param content
	 *            文字内容
	 * @param imgPath
	 *            读取logo图片路径
	 * @param QRCODE_WIDTH
	 *            二维码宽
	 * @param QRCODE_HEIGHT
	 *            二维码高
	 * @param dw
	 *            内部logo宽
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage createImage(String content, String imgPath, int QRCODE_WIDTH, int QRCODE_HEIGHT, int dw)
			throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, E_RATE);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH,
				QRCODE_HEIGHT, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		// 插入logo
		QRCodeUtil.insertImage(image, imgPath, QRCODE_WIDTH, QRCODE_HEIGHT, dw);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * 
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath, int QRCODE_WIDTH, int QRCODE_HEIGHT, int dw)
			throws Exception {
		Image src;
		if (picCache.get("cache") != null) {
			src = picCache.get("cache");
		} else {
			File file = new File(imgPath);
			if (!file.exists()) {
				System.err.println("" + imgPath + "   该文件不存在！");
				return;
			}
			src = ImageIO.read(new File(imgPath));
			picCache.put("cache", src);// 缓存
			System.out.println("未读缓存");
		}
		/*
		 * double d_w=Math.sqrt(QRCODE_WIDTH*QRCODE_HEIGHT*rate)/2; int
		 * int_w=(int)d_w;
		 */
		// double d_h=Math.sqrt(QRCODE_WIDTH*QRCODE_HEIGHT*rate)/2.5;
		Image image = src.getScaledInstance(dw, dw, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(dw, dw, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		src = image;
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_WIDTH - dw) / 2;
		// int y = (QRCODE_HEIGHT - int_w) / 2;
		graph.drawImage(src, x, x, dw, dw, null); // y
		Shape shape = new RoundRectangle2D.Float(x, x, dw, dw, 6, 6);// y
		graph.setStroke(new BasicStroke(12f));
		graph.draw(shape);
		graph.dispose();
	}
	

	public static void setQRCODE_WIDTH(int qRCODE_WIDTH) {
		QRCODE_WIDTH = qRCODE_WIDTH;
	}

	public static int getQRCODE_HEIGHT() {
		return QRCODE_HEIGHT;
	}

	public static void setQRCODE_HEIGHT(int qRCODE_HEIGHT) {
		QRCODE_HEIGHT = qRCODE_HEIGHT;
	}

	public static String getLogoPath() {
		return LogoPath;
	}

	public static void setLogoPath(String logoPath) {
		LogoPath = logoPath;
	}

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static File getRootPath() {
		return rootPath;
	}

	public static void setRootPath(File rootPath) {
		QRCodeUtil.rootPath = rootPath;
	}
	
	private static String testDir = System.getProperty("user.dir");
	@Test
	public void test1(){
		System.out.println(testDir);
		System.out.println(System.getProperty("user.dir"));
		System.out.println(QRCodeUtil.class.getClassLoader().getResource("/"));
	}


}