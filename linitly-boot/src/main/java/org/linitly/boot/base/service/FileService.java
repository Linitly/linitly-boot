package org.linitly.boot.base.service;

import org.linitly.boot.base.config.OSSConfig;
import org.linitly.boot.base.helper.entity.ImageEntity;
import org.linitly.boot.base.utils.file.FileUtil;
import org.linitly.boot.base.utils.file.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/10 13:36
 * @descrption:
 */
@Service
public class FileService {

    @Autowired
    private OSSConfig config;

    public ImageEntity uploadExcelFile(MultipartFile file) {
        String filePath = OSSUtil.uploadFile(FileUtil.changeToFile(file), FileUtil.getFileType(file), config);
        return new ImageEntity().setUrl(filePath);
    }

    public ImageEntity uploadOneImage(MultipartFile file) {
        String filePath = OSSUtil.uploadFile(FileUtil.changeToFile(file), FileUtil.getFileType(file), config);
        return new ImageEntity().setUrl(filePath);
    }

    public List<ImageEntity> uploadImages(MultipartFile[] files) {
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            String filePath = OSSUtil.uploadFile(FileUtil.changeToFile(file), FileUtil.getFileType(file), config);
            imageEntities.add(new ImageEntity().setUrl(filePath));
        }
        return imageEntities;
    }
}
