package com.base.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * 图片水印工具类
 */
public class WaterPressUtil {
    /**
     * 把图片印刷到图片上
     *
     * @param pressImg  水印文件
     * @param targetImg 目标文件
     */
    public final static void pressImage(String pressImg, String targetImg, int top, int right) {
        try {
            // 目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            //水印位置
            g.drawImage(src_biao, (wideth - wideth_biao - right), top, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image,formatName , new File(targetImg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** */
    /**
     * 打印文字水印图片
     *
     * @param pressText --文字
     * @param targetImg --目标图片
     */
    public static void pressText(String pressText, String targetImg) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(Color.blue);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString(pressText, width - 290, height-5);
            g.rotate(45.00);
            g.dispose();
            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image,formatName , new File(targetImg));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
