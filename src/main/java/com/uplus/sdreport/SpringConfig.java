package com.uplus.sdreport;

import com.uplus.sdreport.jpa.JdbcReportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcReportRepository getJdbcReportRepository(){
        return new JdbcReportRepository(dataSource);
    }

}
