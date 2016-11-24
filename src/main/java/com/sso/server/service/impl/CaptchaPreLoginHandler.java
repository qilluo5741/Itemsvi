package com.sso.server.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.util.Base64Utils;

import com.sso.server.service.IPreLoginHandler;

public class CaptchaPreLoginHandler implements IPreLoginHandler{
	//验证码组成
	private final static String COODES="0123456789";
	//长度
	private final static int LEN=4;
	
	
	public Map<String,Object> handle(HttpSession session) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		//生成随机码
		String code=randomCode();
		//code 存入session中
		session.setAttribute(SESSION_ATTR_NAME, code);
		//图片
		map.put("imageData", "data:image/png;base64,"+Base64Utils.encodeToString(generateImg(code)));
		return map;
	}
	/**
	 * 随机生成4为数字字符串
	 * @return
	 */
	private String randomCode(){
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < LEN; i++) {
			sb.append(COODES.charAt(random.nextInt(COODES.length())));
		}
		return sb.toString();
	}

	/*
	 * 绘制PNG图片
	 * 
	 * @return
	 */
	private byte[] generateImg(String code) throws IOException {

		final int width = 80;
		final int height = 30;
		
		BufferedImage bimg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		//绘图
		Graphics2D g = bimg.createGraphics();

		// 背景
		g.setColor(Color.WHITE);
		//填充矩形
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.GRAY);
		g.setFont(new Font("黑体", Font.BOLD, 25));

		// 干扰线
		Random random = new Random();
		for (int i = 0; i < 10; ++i) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);

			g.drawLine(x1, y1, x2, y2);
		}

		for (int i = 0; i < LEN; ++i) {
			g.drawString(String.valueOf(code.charAt(i)), 5 + 16 * i, 25);
		}
		//释放资源
		g.dispose();

		// 输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bimg, "png", baos);
		baos.close();

		return baos.toByteArray();
	}

}
