package com.yapp.ios2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpService {


    public static boolean sendReq( String url, String json ){

        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("Content-Type", "application/json");

            httpPost.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(httpPost);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {

                System.out.println("[RESPONSE] requestHttpJson() " + body);
                log.info("[RESPONSE] requestHttpJson() " + body);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(body);

                return true;
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
                log.error("response is error : " + response.getStatusLine().getStatusCode());
                log.error("[RESPONSE] " + body);
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return false;
    }


}
