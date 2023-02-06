package com.assignment.springcrud.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

//    @Bean
//    public DataSource dataSource(){
//        DataSourceBuilder builder = DataSourceBuilder.create();
//        System.out.println("My custom datasource");
//        return builder.url("jdbc:mysql://localhost:3306/LMS")
//                .username("root")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }
}
