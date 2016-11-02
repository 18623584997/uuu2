package test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.common.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/2.
 */
public class ImageUtils {


    /**
     * 重新设置图像大小
     * @param sfile 图片源文件
     * @param tfile 目标文件
     * @param newWidth 新宽度
     * @param newHeight 新高度
     * @param quality 质量系数
     * @throws IOException
     */
    public static void resizeImg(String sfile, String tfile, int newWidth,
                                 int newHeight, float quality) throws IOException {
        if(quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }

        if(newWidth <=0 || newHeight<=0) {
            throw new IllegalArgumentException(
                    "Width or Height must greater than zero");
        }

        //This code ensures that all the pixels in the image are loaded.
        Image srcImg = new ImageIcon(sfile).getImage();
        Image resizedImage = srcImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(new File(tfile));

            // Encodes image as a JPEG data stream
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            JPEGEncodeParam param = encoder
                    .getDefaultJPEGEncodeParam(bufferedImage);

            param.setQuality(quality, true);

            encoder.setJPEGEncodeParam(param);
            encoder.encode(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public static void makeRoundedCornerImg(String sfile, String tfile,
                                            int cornerRadius) throws IOException {
        BufferedImage image = ImageIO.read(new File(sfile));

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
                cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        String filetype = tfile.substring(tfile.lastIndexOf(".")+1);
        try {
            ImageIO.write(output, filetype, new File(tfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String filepath = "c:/";
//        ImageUtils.resizeImg(filepath+"1.jpg", filepath+"1-256-256-1.png", 256, 256, 1.0f);
//        ImageUtils.resizeImg(filepath+"1.jpg", filepath+"1-256-256-2.png", 256, 256, 0.7f);
        ImageUtils.makeRoundedCornerImg(filepath+"image_region_center.jpg", filepath+"2-256-256-90.png", 360);


//        ImageUtils.makeRoundedCornerImg(filepath+"2-256-256.png", filepath+"2-256-256-90.png", 90);
//        ImageUtils.makeRoundedCornerImg(filepath+"2-256-256.png", filepath+"2-256-256-180.png", 180);
//        ImageUtils.makeRoundedCornerImg(filepath+"2-256-256.png", filepath+"2-256-256-360.png", 360);
//        ImageUtils.makeRoundedCornerImg(filepath+"3-256-256.jpg", filepath+"3-256-256-360.png", 360);

    }
}
