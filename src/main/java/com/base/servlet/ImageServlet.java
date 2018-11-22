package com.base.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码
 */
@WebServlet(urlPatterns = "/imageUtil")
public class ImageServlet extends HttpServlet {
	public ImageServlet() {
		super();
	}

	@Override
    public void destroy() {
		super.destroy();
	}

	public Color getRandColor(int x, int y, int z) {
		Random random = new Random();
		int r, g, b;
		r = random.nextInt(x);
		g = random.nextInt(y);
		b = random.nextInt(z);
		return new Color(r, g, b);
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		int width = 80, height = 36;
		BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		g.setFont(font);
		Color c = new Color(220, 220, 220);
		g.setColor(c);

		g.fillRect(0, 0, width, height);
		Random r = new Random();
		g.setColor(getRandColor(100, 100, 100));
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(width - 1);
			int y = r.nextInt(height - 1);
			int x1 = r.nextInt(6) + 1;
			int y1 = r.nextInt(12) + 1;
			BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL);
			Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
			g2d.setStroke(bs);
			g2d.draw(line);
		}

		char[] ch = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int len = ch.length, index;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);
			g.setColor(getRandColor(80, 255, 255));
			g.drawString(ch[index] + "", (i * 15) + 14, 24);
			sb.append(ch[index]);
		}
		request.getSession().setAttribute("piccode", sb.toString());
		ImageIO.write(bi, "JPG", response.getOutputStream());
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
    public void init() throws ServletException {
	}
}