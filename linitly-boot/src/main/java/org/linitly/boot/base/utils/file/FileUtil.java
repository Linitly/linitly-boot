package org.linitly.boot.base.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.enums.FileTypeEnum;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.FileResolverException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/10 11:45
 * @descrption:
 */
@Slf4j
public class FileUtil {

    // excel文件类型
    public static List<FileTypeEnum> EXCEL_TYPES = new ArrayList<>();

    // 图片文件类型
    public static List<FileTypeEnum> IMG_TYPES = new ArrayList<>();

    static {
        EXCEL_TYPES.add(FileTypeEnum.XLSX);
        EXCEL_TYPES.add(FileTypeEnum.XLS);
    }

    static {
        IMG_TYPES.add(FileTypeEnum.BMP16);
        IMG_TYPES.add(FileTypeEnum.BMP24);
        IMG_TYPES.add(FileTypeEnum.BMP256);
        IMG_TYPES.add(FileTypeEnum.JPEG);
        IMG_TYPES.add(FileTypeEnum.GIF);
        IMG_TYPES.add(FileTypeEnum.PNG);
    }

    /**
     * @author linxiunan
     * @date 11:54 2020/12/10
     * @description 获取文件类型(后缀名)
     */
    public static String getFileType(MultipartFile file) {
        String fileHeader = getFileHeader(file);
        String fileType = null;
        for (FileTypeEnum type: FileTypeEnum.values() ) {
            if (fileHeader.startsWith(type.getValue())) {
                fileType = type.getCode();
                break;
            }
        }
        return fileType;
    }

    /**
     * @author linxiunan
     * @date 11:54 2020/12/10
     * @description MultipartFile转换为File
     */
    public static File changeToFile(MultipartFile file) {
        if (file.isEmpty()) return null;
        try {
            File fileResult = File.createTempFile("tmp", null);
            file.transferTo(fileResult);
            fileResult.deleteOnExit();
            return fileResult;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileResolverException(ResultEnum.FILE_RESOLVER_ERROR);
        }
    }

    /**
     * 检测上传的文件类型
     * @param file 上传的文件
     * @param fileTypes 文件类型后缀List
     * @return
     */
    public static boolean checkFileType(MultipartFile file, List<FileTypeEnum> fileTypes) {
        String fileHeader = getFileHeader(file);
        boolean trueType = false;
        for (FileTypeEnum type: FileTypeEnum.values() ) {
            if (fileHeader.startsWith(type.getValue()) && fileTypes.contains(type)) {
                trueType = true;
            }
        }
        return trueType;
    }

    public static boolean checkFileType(MultipartFile[] files, List<FileTypeEnum> fileTypes) {
        String fileHeader = null;
        boolean trueType = false;
        for (int i = 0; i < files.length; i++) {
            fileHeader = getFileHeader(files[i]);
            for (FileTypeEnum type: FileTypeEnum.values() ) {
                if (fileHeader.startsWith(type.getValue()) && fileTypes.contains(type)) {
                    trueType = true;
                }
            }
        }
        return trueType;
    }

    /**
     * 获取上传文件二进制头
     */
    public static String getFileHeader(MultipartFile file) {
        byte[] is = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            is = file.getBytes();
            byte[] bytes = new byte[28];
            System.arraycopy(is,0,bytes,0,28);
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("file error");
        }
        return stringBuilder.toString().toLowerCase();
    }
}