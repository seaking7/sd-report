package com.uplus.sdreport.service;

import com.uplus.sdreport.dto.ReportDto;
import com.uplus.sdreport.jpa.JdbcReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{


    JdbcReportRepository jdbcReportRepository;

    @Autowired
    public ReportServiceImpl(JdbcReportRepository jdbcReportRepository) {
        this.jdbcReportRepository = jdbcReportRepository;
    }

    @Override
    public ReportDto getReportByContentId(String contentId) {
        ReportDto reportDto = jdbcReportRepository.findByContentId(contentId).get();
        return reportDto;
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
