package com.asinx.utils;


import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class Base64ImgUtil {

	/**
	 * 图片转化成base64字符串
	 * @param imgPath
	 * @return
	 */
	public static String GetImageStr(String imgPath,String photoType) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = imgPath;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		String encode = null; // 返回Base64编码过的字节数组字符串
		// 对字节数组Base64编码
		Base64.Encoder encoder = Base64.getEncoder();
		try {
			// 读取图片字节数组
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			return "data:image/" + photoType + ";base64," + encoder.encodeToString(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return encode;
	}

	/**
	 * base64字符串转化成图片
	 *
	 * @param imgData
	 *            图片编码
	 * @param imgFilePath
	 *            存放到本地路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("finally")
	public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
		if (imgData == null) // 图像数据为空
			return false;
		imgData = imgData.substring(imgData.indexOf(",", 1) + 1);
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			out = new FileOutputStream(imgFilePath);
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgData);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			out.write(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
			return true;
		}
	}

	public static void main(String[] args) throws IOException {
		String imageStr = GetImageStr("/Users/donnie/Desktop/passort2.jpg","jpg");
		System.out.println(imageStr);
//		System.out.println(imageStr);
		GenerateImage(imageStr, "/Users/donnie/Downloads/a.jpeg");
	}
}