package com.example.springbatch.batch;

import com.example.springbatch.batch.dto.SalesInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class SalesInfoJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<SalesInfoDTO> salesInfoFileReader(){
        return new FlatFileItemReaderBuilder<SalesInfoDTO>()
                .resource(new ClassPathResource("/data/Pascoal-Store.csv"))
                .name("salesInfoFileReader")
                .delimited()
                .delimiter(",")
                .names(new String[]{"Product,Seller,Seller ID,Price,City,Category"})
                .linesToSkip(1)
                .targetType(SalesInfoDTO.class)
                .build();

    }
}
