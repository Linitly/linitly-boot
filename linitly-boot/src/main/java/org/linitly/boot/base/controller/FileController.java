package org.linitly.boot.base.controller;

import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.helper.entity.ImageEntity;
import org.linitly.boot.base.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.linitly.boot.base.utils.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/10 13:48
 * @descrption:
 */
@Result
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传管理")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/image")
    @ApiOperation(value = "上传单个图片")
    public ImageEntity updateImage(@RequestParam MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new CommonException(ResultEnum.FILE_NOT_UPLOAD_ERROR);
        if (!FileUtil.checkFileType(file, FileUtil.IMG_TYPES))
            throw new CommonException(ResultEnum.FILE_TYPE_ERROR);
        return fileService.uploadOneImage(file);
    }

    @PostMapping("/upload/images")
    @ApiOperation(value = "上传多个图片")
    public List<ImageEntity> updateImages(@RequestParam MultipartFile[] files) {
        if (files == null || files.length < 1)
            throw new CommonException(ResultEnum.FILE_NOT_UPLOAD_ERROR);
        if (!FileUtil.checkFileType(files, FileUtil.IMG_TYPES))
            throw new CommonException(ResultEnum.FILE_TYPE_ERROR);
        return fileService.uploadImages(files);
    }

    @PostMapping("/upload/excel")
    @ApiOperation(value = "上传单个excel")
    public ImageEntity updateExcel(@RequestParam MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new CommonException(ResultEnum.FILE_NOT_UPLOAD_ERROR);
        if (!FileUtil.checkFileType(file, FileUtil.EXCEL_TYPES))
            throw new CommonException(ResultEnum.FILE_TYPE_ERROR);
        return fileService.uploadExcelFile(file);
    }
}
