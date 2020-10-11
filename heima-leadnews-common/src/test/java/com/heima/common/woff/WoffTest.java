package com.heima.common.woff;

import com.heima.common.ocr.OcrTest;
import com.heima.common.ocr.ImageBean;
import com.heima.common.ocr.cut.CutCharFinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class WoffTest {
    String rootPath = "";
    String woffBase64 = "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";

    @Before
    public void testBase64ToWoff() throws Exception{
        rootPath = OcrTest.class.getResource("/test-woff").getPath();
    }

    /**
     * 生产字符库
     * @throws Exception
     */
    @Test
    public void testToCreateLib() throws  Exception{
        Woff woff = new Woff(Base64.getDecoder().decode(woffBase64));
        BufferedImage bi = woff.getImage();
        ImageBean bean = new ImageBean(bi);
        Integer[][] pixel = bean.toBinarization();
        // 第四步字符切割
        CutCharFinder tool = new CutCharFinder();
        List<BufferedImage> list = tool.drawCharImage(pixel,false);
        int count = 1;
        for (BufferedImage img : list) {
            ImageIO.write(img, "jpg", new File(rootPath+"/char_" + count + ".jpg"));
            count++;
        }
    }

    /**
     * 测试字符识别
     * @throws Exception
     */
    @Test
    public void testOcrMap() throws  Exception{
        Woff woff = new Woff(Base64.getDecoder().decode(woffBase64));
        Map<Integer, String> map = woff.ocrWoff();
        System.out.println(map);
        String str = "\uE5E5\uE124\uE4FD\uE503\uE17B";
        String res = "";
        for (int i = 0; i < str.length(); i++)
        {
            int temp = str.charAt(i);
            if (map.containsKey(Integer.valueOf(temp))) {
                res = res + (String)map.get(Integer.valueOf(temp));
            } else {
                res = res + (char)temp;
            }
        }
        System.out.println(res);
    }


}
