package com.uplus.sdreport.client;


import com.uplus.sdreport.vo.ResponseContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="content-service")
public interface ContentServiceClient {

    @GetMapping("/{contentId}/contents/client")
    public ResponseEntity<ResponseContent> selectFromReport(@PathVariable String contentId);

}
