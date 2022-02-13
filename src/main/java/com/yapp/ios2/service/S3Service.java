package com.yapp.ios2.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.util.IOUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;

@Service
@NoArgsConstructor
public class S3Service{

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.sns.topic.arn")
    private String arn;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public boolean upload(MultipartFile file, String filePath) throws IOException {
        try {
            s3Client.putObject(new PutObjectRequest(bucket, filePath, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return true;
        } catch (Exception e){

            return false;
        }
    }

    public byte[] download(Long filmUid, String fileName) throws IOException {
        S3Object file = s3Client.getObject(new GetObjectRequest(bucket +
        "/" + filmUid.toString(), fileName + ".jpeg"));

        S3ObjectInputStream s3OIS = file.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(s3OIS);

        return bytes;
    }

    public byte[] download(String filePath, String fileName) throws IOException {
        S3Object file = s3Client.getObject(new GetObjectRequest(bucket +
                "/" + filePath, fileName));

        S3ObjectInputStream s3OIS = file.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(s3OIS);

        return bytes;
    }

    // Delete Only 1 file.
    public void delete(Long filmUid, Long photoUid){

        s3Client.deleteObject(
            bucket, s3Client.getObject(bucket, filmUid.toString() + "/" + photoUid.toString() + ".jpeg").getKey()
        );

//        for (S3ObjectSummary file : s3Client.listObjects(bucket, filmUid.toString() + "/").getObjectSummaries()){
//            s3Client.deleteObject(bucket, file.getKey());
//        }

    }


}
