package com.weichuang.ecommerce.barcode.util;

import com.sun.imageio.plugins.common.ImageUtil;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageTools {
    /**
     * 将文本写入图片
     * @param text  文字内容
     * @param font 样式
     * @param color 文字颜色
     * @param width 图片宽
     * @param height 图片高
     * @param os 输出流
     * @throws Exception
     */
    public static void createImage(String text, Font font, Color color,
                                   Integer width, Integer height,OutputStream os) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(color);
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(text, i * 680, y);// 画出字符串
        }
        g.dispose();
        ImageIO.write(image, "png", os);// 输出png图片
    }

    /**
     *
     * @param text 文本
     * @param font 字体
     * @param color 颜色
     * @param width 宽度
     * @param height 高度
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage createImage(String text, Font font, Color color,
                                            Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(color);
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        //int y = (clip.height - (ascent + descent)) / 2 + ascent;
        int textWidth = fm.stringWidth(text);
        //int y=clip.height-ascent;
        int y=width+30;//clip.height-ascent*2;
        // 计算文字长度，计算居中的x点坐标

        int widthX = (width - textWidth) / 2;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(text, widthX, y);// 画出字符串
        }

        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString("冠宇阳光健身(西二区店)", widthX, y+45);// 画出字符串
        }
        g.dispose();
        return image;
    }
    /**
     *
     * @param companyName 公司名称
     * @param shopName 门店名称
     * @param codeSize 大小
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage createImage(String companyName,String shopName,
                                            Integer codeSize) throws Exception {

        int height=codeSize;
        if(StringUtils.isNotEmpty(shopName)){
            height=height+100;
        }
        else{
            height=height+70;
        }
        // 创建图片
        BufferedImage image = new BufferedImage(codeSize, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();



        g.setClip(0, 0, codeSize, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, codeSize, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.red);
        Font font=new Font("楷体",0,30);
        g.setFont(font);// 设置画笔字体

        g.drawRect(5, 5, 100, 100);//画矩形
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        //int y = (clip.height - (ascent + descent)) / 2 + ascent;
        int textWidth = fm.stringWidth(companyName);
        //int y=clip.height-ascent;
        int y=codeSize+30;//clip.height-ascent*2;
        // 计算文字长度，计算居中的x点坐标

        int widthX = (codeSize - textWidth) / 2;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(companyName, widthX, y);// 画出字符串
        }
        if(StringUtils.isNotEmpty(shopName)){
            int textWidth1 = fm.stringWidth(shopName);

            int widthX1 = (codeSize - textWidth1) / 2;
            if(StringUtils.isNotEmpty(shopName)){
                g.setColor(Color.blue);
                for (int i = 0; i < 6; i++) {// 256 340 0 680
                    g.drawString(shopName, widthX1, y+45);// 画出字符串
                }
            }
        }

        g.dispose();
        return image;
    }
    /**
     *
     * @param barcode 二维码图片
     * @param bgimage 背景图片
     * @param os 输出流
     * @return 合成后的图片
     * @throws Exception
     */
    public static void compositeImage(BufferedImage barcode,BufferedImage bgimage,OutputStream os) throws  Exception {
        BufferedImage image= barcode;//二维码
        BufferedImage bg=bgimage;//获取背景图片
        Graphics2D g=bg.createGraphics();
        g.drawImage(image,0,0,image.getWidth() ,image.getHeight(),null);
        g.dispose();
        bg.flush();
        image.flush();
        ImageIO.write(bg,"png", os);

    }
}
