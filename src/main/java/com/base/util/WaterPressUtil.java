package com.base.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param destFileName 目标文件名
     */
    public static boolean copyFile(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        File destFile = new File(destFileName);
        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
