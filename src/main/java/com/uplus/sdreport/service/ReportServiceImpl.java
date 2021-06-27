package com.uplus.sdreport.service;

import com.uplus.sdreport.client.ContentServiceClient;
import com.uplus.sdreport.dto.ReportDto;
import com.uplus.sdreport.jpa.JdbcReportRepository;
import com.uplus.sdreport.vo.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService{


    JdbcReportRepository jdbcReportRepository;
    ContentServiceClient contentServiceClient;
    CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public ReportServiceImpl(JdbcReportRepository jdbcReportRepository, ContentServiceClient contentServiceClient, CircuitBreakerFactory circuitBreakerFactory) {
        this.jdbcReportRepository = jdbcReportRepository;
        this.contentServiceClient = contentServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }


    @Override
    public ResponseContent getReportByContentId(String contentId) {

        log.info("{getReportByContentId} contentId: "+ contentId);

        //ReportDto reportDto = jdbcReportRepository.findByContentId(contentId).get();

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("GetContent");
        ResponseContent responseContent = circuitBreaker.run(() -> contentServiceClient.selectFromReport(contentId).getBody(),
                throwable -> new ResponseContent());


        return responseContent;
    }

    @Override
    public Iterable<ReportDto> getReportAll() {
        return jdbcReportRepository.findReportAll();
    }

    @Override
    public void deleteByContentId(String contentId) {
        jdbcReportRepository.deleteByContentId(contentId);
    }
}
