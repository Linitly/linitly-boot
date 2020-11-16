package org.linitly.boot.base.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @program: life-insurance->ImageUtils
 * @description: 图片压缩
 * @author: caofeng
 * @create: 2019-08-07 14:43
 **/
public class ImageUtils {

    /**
     *  按指定大小把图片进行缩和放（会遵循原图高宽比例）
     *  例如把图片压成400×500的缩略图
     * @param file  需要压缩的图片
     * @param tofile 压缩后图片
     * @param width  要压缩的宽度
     * @param high 要压缩的高度
     * @return
     * @throws IOException
     */
    public static void specifySizeByProportion(File file, File tofile,int width, int high) throws IOException {
//        InputStream inputStream = new FileInputStream(file);
        Thumbnails.of(file).size(width, high).toFile(tofile);
    }

    /**
     *  按照比例进行缩小，放大
     * @param file  需要压缩的图片
     * @param tofile 压缩后图片
     * @param scale  压缩比例  0 - 1 为压缩  >1 为放大  如 0.2
     * @return
     * @throws IOException
     */
    public static void proportion(File file, File tofile, double scale) throws IOException {
        Thumbnails.of(file).scale(scale).toFile(tofile);
    }
    /**
     *  不按比例，就按指定的大小进行缩放
     * @param file  需要压缩的图片
     * @param tofile 压缩后图片
     * @param width  要压缩的宽度
     * @param high 要压缩的高度
     * @return
     * @throws IOException
     */
    public static void specifySize(File file, File tofile, int width, int high) throws IOException {
        Thumbnails.of(file).forceSize(width,high).toFile(tofile);
        //或者
//        Thumbnails.of(file).size(width, high).keepAspectRatio(false).toFile(tofile);
    }
    /**
     *  图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
     * @param file  需要压缩的图片
     * @param tofile 压缩后图片
     * @param scale 压缩比例
     * @return
     * @throws IOException
     */
    public static void outputQuality(File file, File tofile, float scale) throws IOException {
        Thumbnails.of(file).scale(1f).outputQuality(scale).toFile(tofile);
    }

    /**
     * 创建输出目录
     * @param file
     * @param asFile
     * @throws IOException
     */
    private static void createPath(File file, boolean asFile) throws IOException {
        String path = file.getAbsolutePath();
        String dirPath;
        if (asFile)
            dirPath = path.substring(0, path.lastIndexOf(File.separator));
        else {
            dirPath = path;
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (asFile)
            file.createNewFile();
    }

}
