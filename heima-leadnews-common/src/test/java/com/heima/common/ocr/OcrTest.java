package com.heima.common.ocr;

import com.heima.common.ocr.cut.CutCharFinder;
import com.heima.common.ocr.distinguish.Distinguish;
import com.heima.common.ocr.distinguish.DistinguishByFuture;
import com.heima.common.ocr.distinguish.DistinguishByImage;
import com.heima.common.ocr.pretreatment.ImpurityLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * 图像画标记OCR图片
 */
@RunWith(JUnit4.class)
public class OcrTest {

    /**
     * 基于特征变量加权识别
     * @throws Exception
     */
    @Test
    public void testDistinguishByImage() throws  Exception {
        String path = OcrTest.class.getResource("/test-ocr").getPath();
        BufferedImage bi = ImageIO.read(new File(path+"/11.jpg"));
        Distinguish image = new DistinguishByImage();
        System.out.println(image.ocrText(bi));
    }

    /**
     * 基于投影变量加权识别
     * @throws Exception
     */
    @Test
    public void testDistinguishByFuture() throws  Exception {
        String path = OcrTest.class.getResource("/test-ocr").getPath();
        BufferedImage bi = ImageIO.read(new File(path+"/11.jpg"));
        Distinguish image = new DistinguishByFuture(true);
        System.out.println(image.ocrText(bi));
    }


    /**
     * 测试输出图片的过程标记
     * @throws Exception
     */
    @Test
    public void testBlag() throws Exception{
        String path = OcrTest.class.getResource("/test-ocr").getPath();
        BufferedImage bi = ImageIO.read(new File(path+"/00.jpg"));
        // 第一步二值化
        ImageBean bean = new ImageBean(bi);
        Integer[][] pixel = bean.toBinarization();
        ImageIO.write(bean.toBinarizationImage(pixel),"jpg",new File(path+"/01_二值化.jpg"));
        // 第二步去掉横线杂质
        ImpurityLine impurity = new ImpurityLine();
        impurity.clearBorder(pixel);
        BufferedImage image = bean.toBinarizationImage(pixel);
        ImageIO.write(image,"jpg",new File(path+"/02_去横竖线.jpg"));
        // 第三步定位文字位置
        CutCharFinder tool = new CutCharFinder();
        tool.drawCharBorder(image,pixel);
        ImageIO.write(image,"jpg",new File(path+"/03_标记切割字符边框.jpg"));
        // 第四步字符切割
        List<BufferedImage> list = tool.drawCharImage(pixel,true);
        int count = 1;
        for (BufferedImage img : list) {
            ImageIO.write(img, "jpg", new File(path+"/04_" + count + "_标记切割字符.jpg"));
            count++;
        }
    }

    /**
     * 建立基准库并切割字符
     * @throws Exception
     */
    @Test
    public void testCutImage() throws  Exception{
        String path = OcrTest.class.getResource("/test-ocr").getPath();
        File file = new File(path+"/原始图片");
        File[] fs = file.listFiles();
        int count = 1;
        for (File f : fs) {
            BufferedImage bi = ImageIO.read(f);
            // 第一步二值化
            ImageBean bean = new ImageBean(bi);
            Integer[][] pixel = bean.toBinarization();
            // 第二步去掉横线杂质
            ImpurityLine impurity = new ImpurityLine();
            impurity.clearBorder(pixel);
            // 第三步定位文字位置
            CutCharFinder tool = new CutCharFinder();
            List<BufferedImage> list = tool.drawCharImage(pixel,false);
            for (BufferedImage img : list) {
                ImageIO.write(img, "jpg", new File(path+"/切割图片/" + count + ".jpg"));
                count++;
            }
        }
    }

}
