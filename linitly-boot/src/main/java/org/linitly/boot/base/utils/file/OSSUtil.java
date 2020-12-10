package org.linitly.boot.base.utils.file;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.linitly.boot.base.config.OSSConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: linxiunan
 * @date: 2020/12/10 11:36
 * @descrption:
 */
public class OSSUtil {

    /**
     * @param file
     * @param fileType 文件后缀
     * @return String 文件地址
     * @MethodName: uploadFile
     * @Description: OSS单文件上传
     */
    public static String uploadFile(File file, String fileType, OSSConfig config) {
        String fileName = config.getStorePath() + UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + fileType; // 文件名，根据UUID来
        return putObject(file, fileType, fileName, config);
    }

    /**
     * @param file
     * @param fileType
     * @param oldUrl
     * @return String
     * @MethodName: updateFile
     * @Description: 更新文件:只更新内容，不更新文件名和文件地址。
     * (因为地址没变，可能存在浏览器原数据缓存，不能及时加载新数据，例如图片更新，请注意)
     */
    public static String updateFile(File file, String fileType, String oldUrl, OSSConfig config) {
        String fileName = getFileName(oldUrl);
        if (fileName == null)
            return null;
        return putObject(file, fileType, fileName, config);
    }

    /**
     * @param file
     * @param fileType 文件后缀
     * @param oldUrl   需要删除的文件地址
     * @return String 文件地址
     * @MethodName: replaceFile
     * @Description: 替换文件:删除原文件并上传新文件，文件名和地址同时替换 解决原数据缓存问题，只要更新了地址，就能重新加载数据)
     */
    public static String replaceFile(File file, String fileType, String oldUrl, OSSConfig config) {
        boolean flag = deleteFile(oldUrl, config); // 先删除原文件
        if (!flag) {
            // 更改文件的过期时间，让他到期自动删除。
        }
        return uploadFile(file, fileType, config);
    }

    /**
     * @param fileUrl 需要删除的文件url
     * @return boolean 是否删除成功
     * @MethodName: deleteFile
     * @Description: 单文件删除
     */
    public static boolean deleteFile(String fileUrl, OSSConfig config) {
        String bucketName = OSSUtil.getBucketName(fileUrl); // 根据url获取bucketName
        String fileName = OSSUtil.getFileName(fileUrl); // 根据url获取fileName
        if (bucketName == null || fileName == null)
            return false;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
            ossClient.deleteObject(request);
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return true;
    }

    /**
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     */
    public static int deleteFile(List<String> fileUrls, OSSConfig config) {
        int deleteCount = 0; // 成功删除的个数
        String bucketName = OSSUtil.getBucketName(fileUrls.get(0)); // 根据url获取bucketName
        List<String> fileNames = OSSUtil.getFileName(fileUrls); // 根据url获取fileName
        if (bucketName == null || fileNames.size() <= 0)
            return 0;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new RuntimeException("OSS服务异常:", oe);
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new RuntimeException("OSS客户端异常:", ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return deleteCount;

    }

    /**
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较慢)：适用于不同endPoint和BucketName
     */
    public static int deleteFiles(List<String> fileUrls, OSSConfig config) {
        int count = 0;
        for (String url : fileUrls) {
            if (deleteFile(url, config)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param file
     * @param fileType
     * @param fileName
     * @return String
     * @MethodName: putObject
     * @Description: 上传文件
     */
    private static String putObject(File file, String fileType, String fileName, OSSConfig config) {
        String url = null; // 默认null
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            InputStream input = new FileInputStream(file);
            ObjectMetadata meta = new ObjectMetadata(); // 创建上传Object的Metadata
            meta.setContentType(OSSUtil.contentType(fileType)); // 设置上传内容类型
            meta.setCacheControl("no-cache"); // 被下载时网页的缓存行为
            PutObjectRequest request = new PutObjectRequest(config.getBucketName(), fileName, input, meta); // 创建上传请求
            ossClient.putObject(request);
            url = config.getEndpoint().replaceFirst("http://", "http://" + config.getBucketName() + ".") + "/" + fileName; // 上传成功再返回的文件路径
        } catch (FileNotFoundException | OSSException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    /**
     * @param fileType
     * @return String
     * @MethodName: contentType
     * @Description: 获取文件类型
     */
    private static String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "xls":
                contentType = "application/vnd.ms-excel";
                break;
            case "xlsx":
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            case "bmp":
                contentType = "image/bmp";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }

    /**
     * @param fileUrl 文件url
     * @return String bucketName
     * @MethodName: getBucketName
     * @Description: 根据url获取bucketName
     */
    private static String getBucketName(String fileUrl) {
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex = 0;
        if (httpIndex == -1) {
            if (httpsIndex == -1) {
                return null;
            } else {
                startIndex = httpsIndex + https.length();
            }
        } else {
            startIndex = httpIndex + http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    /**
     * @param fileUrl 文件url
     * @return String fileName
     * @MethodName: getFileName
     * @Description: 根据url获取fileName
     */
    private static String getFileName(String fileUrl) {
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if (beginIndex == -1)
            return null;
        return fileUrl.substring(beginIndex + str.length());
    }

    /**
     * @param fileUrls 文件url
     * @return List<String> fileName集合
     * @MethodName: getFileName
     * @Description: 根据url获取fileNames集合
     */
    private static List<String> getFileName(List<String> fileUrls) {
        List<String> names = new ArrayList<>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }
}


