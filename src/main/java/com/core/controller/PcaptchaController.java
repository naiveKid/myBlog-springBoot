package com.core.controller;

import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * 使用pcaptcha生成验证码
 *
 */
@Controller
@RequestMapping("/captcha")
public class PcaptchaController {
	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	private static Random random = new Random();

	static {
		// 随机颜色
		cs.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int arg0) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		// 随机字符
		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("0123456789abcdefhmnpqrstuvwxyABCDEFGHGLMNPQRSTUVWXY");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);
		// 字体大小
		RandomFontFactory ff = new RandomFontFactory();
		ff.setMinSize(30);
		ff.setMaxSize(30);
		cs.setFontFactory(ff);
		// 图片大小
		cs.setWidth(100);
		cs.setHeight(38);
	}

	@RequestMapping("/generatImg")
	public void generatImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		switch (random.nextInt(5)) {
		case 0:
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
			break;
		case 1:
			cs.setFilterFactory(new MarbleRippleFilterFactory());
			break;
		case 2:
			cs.setFilterFactory(new DoubleRippleFilterFactory());
			break;
		case 3:
			cs.setFilterFactory(new WobbleRippleFilterFactory());
			break;
		case 4:
			cs.setFilterFactory(new DiffuseRippleFilterFactory());
			break;
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		setResponseHeaders(response);
		String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		session.setAttribute("piccode", token);
	}

	/**
	 * 设置response header
	 * 
	 * @param response
	 */
	private void setResponseHeaders(HttpServletResponse response) {
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", time);
	}
}
