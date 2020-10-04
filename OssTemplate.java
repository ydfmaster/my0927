package com.offcn.dycommon.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class OssTemplate {


    private String endpoint;

    private String bucketDomain;

    private String accessKeyId;
    private String accessKeySecret;

    private String bucketName;


    /**
     * 上传文件返回url
     * https://offcn925.oss-cn-beijing.aliyuncs.com/pic/123.jpg
     * @param inputStream
     * @param fileName
     * @return
     */
    public String upload(InputStream inputStream,String fileName) {

        //日期归档
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //文件夹名称
        String folderName = simpleDateFormat.format(new Date());

        //oss客户端对象
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //oss中的文件名（uuid+fileName）
        String ossFileName = UUID.randomUUID().toString().replace("-","")+fileName;
        //上传
        oss.putObject(bucketName, "pic/" + folderName + "/" + ossFileName, inputStream);

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        oss.shutdown();

        String url = bucketDomain + "pic/"+folderName+"/"+ossFileName;

        return url;
    }



}
