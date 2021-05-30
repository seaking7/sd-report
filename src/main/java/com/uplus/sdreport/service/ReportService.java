package com.uplus.sdreport.service;


import com.uplus.sdreport.dto.ReportDto;

public interface ReportService {

    ReportDto getReportByContentId(String contentId);

    Iterable<ReportDto> getReportAll();

    void deleteByContentId(String contentId);

}
