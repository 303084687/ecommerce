package com.weichuang.ecommerce.barcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BarCodeTools {

    /**
     *
     * @param url url地址
     * @param imageSize 二维码大小
     * @param os 输出流
     * @throws Exception
     */
    public static  void createBarcode(String url,int imageSize,OutputStream os) throws  Exception
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, imageSize, imageSize, hints);
        //生成二维码
        ByteArrayOutputStream oi=new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg",os);

    }
    /**
     *
     * @param url url地址
     * @param imageSize 二维码大小
     * @param os 输出流ByteArrayOutputStream
     * @throws Exception
     */
    public static  void createBarcode(String url,int imageSize,ByteArrayOutputStream os) throws  Exception
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, imageSize, imageSize, hints);
        //生成二维码
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg",os);

    }
    /**
     * 二维码图片的生成
     * @param content			链接
     * @param qrcode_width		二维码宽
     * @param qrcode_height		二维码高
     * @param type		1 公司码 2门店码 其他值为普通码
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(String content, int qrcode_width, int qrcode_height,int type) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, qrcode_width, qrcode_height, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if(type==1){
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0XEE0000 : 0xFFFFFFFF);
                }
                else if(type==2){
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0X0000FF : 0xFFFFFFFF);
                }
                else{
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
        }
        return image;
    }
    public static void main(String[] args) throws  Exception
    {
        BufferedImage bufImg= createImage("http://www.baidu.com",430,430,1);
        Font font=new Font("楷体",0,30);
        String color="red";
        Class<?>  clazz=Class.forName("java.awt.Color");
        Field f=clazz.getDeclaredField(color);
        f.setAccessible(true);
        Color c=(Color)f.get(clazz.getClass());
       // BufferedImage bgImg= ImageTools.createImage("北京版图体育文化发展有限公司",font, c,430,490);//text文字信息
        BufferedImage bgImg= ImageTools.createImage("北京版图体育文化发展有限公司","冠宇阳光健身(西二区店)",430);
        FileOutputStream os=new FileOutputStream(new File("d:\\code1.png"));
        ImageTools.compositeImage(bufImg,bgImg,os);

    }
}
