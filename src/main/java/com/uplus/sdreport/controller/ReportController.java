package com.uplus.sdreport.controller;

import com.uplus.sdreport.dto.ReportDto;
import com.uplus.sdreport.service.ReportService;
import com.uplus.sdreport.vo.RequestDeleteContent;
import com.uplus.sdreport.vo.ResponseContent;
import com.uplus.sdreport.vo.ResponseReport;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class ReportController {

    ReportService reportService;
    private Environment env;


    @Autowired
    public ReportController(ReportService reportService, Environment env) {
        this.reportService = reportService;
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "report-welcome";
    }

    @GetMapping("/")
    public String reportHome(){
        return "home";
    }



    @GetMapping("/report")
    @Timed(value="report.list", longTask = true)
    public String viewReport(Model model){
        log.info("{viewReport} list report ");

        Iterable<ReportDto> reportDto = reportService.getReportAll();

        List<ResponseReport> result = new ArrayList<>();
        reportDto.forEach( v -> {
            result.add(new ModelMapper().map(v, ResponseReport.class));
        });
        model.addAttribute("reports", result);
        setEnvModel(model);

        return "content/viewReport";
    }


    @GetMapping("/{contentId}/report")
    @Timed(value="report.detail", longTask = true)
    public ModelAndView selectDetailContent(@PathVariable String contentId){
        log.info("{selectDetailContent} contentId: "+ contentId);

        ModelAndView mav= new ModelAndView("content/reportContent");

        ResponseContent responseContent = reportService.getReportByContentId(contentId);

        mav.addObject("contentId", responseContent.getContentId());
        mav.addObject("contentName", responseContent.getContentName());
        mav.addObject("url", responseContent.getUrl());
        mav.addObject("creator", responseContent.getCreator());
        mav.addObject("cp", responseContent.getCp());
        mav.addObject("category", responseContent.getCategory());

        mav.addObject("server_address", env.getProperty("eureka.instance.hostname"));
        mav.addObject("server_port", env.getProperty("local.server.port"));
        mav.addObject("server_service", env.getProperty("spring.application.name"));

        return mav;
    }

    @PostMapping("/report/deleteContent")
    @Timed(value="report.delete", longTask = true)
    public ResponseEntity<ResponseReport> deleteReport(@RequestBody RequestDeleteContent requestDeleteContent){
        log.info("{deleteReport} contentId: "+ requestDeleteContent.getContentId());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        reportService.deleteByContentId(requestDeleteContent.getContentId());

        ResponseReport responseReport = mapper.map(requestDeleteContent, ResponseReport.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseReport);

    }

    public void setEnvModel(Model model){
        model.addAttribute("server_address", env.getProperty("eureka.instance.hostname"));
        model.addAttribute("server_port", env.getProperty("local.server.port"));
        model.addAttribute("server_service", env.getProperty("spring.application.name"));
    }

}
