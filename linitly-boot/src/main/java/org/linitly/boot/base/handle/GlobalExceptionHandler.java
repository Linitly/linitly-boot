package org.linitly.boot.base.handle;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.ExceptionResultEnum;
import org.linitly.boot.base.exception.*;
import org.linitly.boot.base.helper.entity.ResponseResult;
import org.linitly.boot.base.utils.valid.BindingResultUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public ResponseResult paramEmptyErrorHandler(Exception e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.PARAM_EMPTY_ERROR);
    }

    @ExceptionHandler({CommonException.class})
    public ResponseResult commonHandle(CommonException e) {
        e.printStackTrace();
        return new ResponseResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({EncryptException.class})
    public ResponseResult encryptErrorHandle(EncryptException e) {
        e.printStackTrace();
        return new ResponseResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({DecryptException.class})
    public ResponseResult decryptErrorHandle(DecryptException e) {
        e.printStackTrace();
        return new ResponseResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseResult fileTooBigErrorHandle(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.FILE_TOO_BIG_ERROR);
    }

    @ExceptionHandler({MissingServletRequestPartException.class, MultipartException.class})
    public ResponseResult missFileErrorHandle(Exception e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.FILE_NOT_UPLOAD_ERROR);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseResult unSupportErrorHandle(Exception e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.UN_SUPPORT_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseResult validErrorHandle(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return new ResponseResult(GlobalConstant.GENERAL_ERROR, BindingResultUtil.getBindingResultErrMsg(e.getBindingResult()));
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseResult duplicateErrorKeyHandle(DuplicateKeyException e) {
        // 数据库新增时，唯一限制字段已存在抛错
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.DUPLICATE_KEY_ERROR);
    }

    @ExceptionHandler({AESDecryptKeyException.class})
    public ResponseResult aesDecryptErrorKeyHandle(AESDecryptKeyException e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.AES_DECRYPT_KEY_ERROR);
    }

    @ExceptionHandler({QuartzException.class})
    public ResponseResult quartzExceptionHandle(QuartzException e) {
        e.printStackTrace();
        return new ResponseResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Object defaultExceptionHandler(Exception e) {
        e.printStackTrace();
        return new ResponseResult(ExceptionResultEnum.SYSTEM_ERROR);
    }

}
