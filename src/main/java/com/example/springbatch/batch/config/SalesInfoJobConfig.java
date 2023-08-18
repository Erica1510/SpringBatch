package com.example.springbatch.batch.config;

import com.example.springbatch.SpringBatchApplication;
import com.example.springbatch.batch.listener.JobCompletionNotificationListener;
import com.example.springbatch.batch.processor.SalesInfoItemProcessor;
import com.example.springbatch.batch.repository.SalesInfoRepository;
import com.example.springbatch.batch.tasklet.SalesInfoTasklet;
import com.example.springbatch.batch.entity.SalesInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class SalesInfoJobConfig {
    private  SalesInfoRepository salesInfoRepository;
    public SalesInfoItemProcessor salesInfoItemProcessor;
    private final Logger log = LoggerFactory.getLogger(SpringBatchApplication.class);
    @Autowired
    public SalesInfoJobConfig(SalesInfoRepository salesInfoRepository) {
        this.salesInfoRepository = salesInfoRepository;
    }

    @Bean
    public Job importSalesInfo(JobRepository jobRepository, JobCompletionNotificationListener listener,Step fromFileIntoDataBase, Step moveFile){
        return new JobBuilder("importSalesInfoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(fromFileIntoDataBase)
                .next(moveFile)
                .end().build();
    }
    @Bean
    public Step fromFileIntoDataBase(JobRepository jobRepository,DataSource dataSource,PlatformTransactionManager transactionManager){
        return new StepBuilder("fromFileIntoDatabase",jobRepository)
                .<SalesInfo,SalesInfo>chunk(10, transactionManager)
                .reader(salesInfoFileReader())
                .processor(salesInfoItemProcessor)
                .writer(salesInfoJpaItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<SalesInfo> salesInfoFileReader(){
        return new FlatFileItemReaderBuilder<SalesInfo>()
                .resource(new ClassPathResource("data/Pascoal-Store.csv"))
                .name("salesInfoFileReader")
                .delimited()
                .delimiter(",")
                .names(new String[]{"id","product","seller","sellerId","price","city","category"})
                .linesToSkip(1)
                .targetType(SalesInfo.class)
                .build();
    }
    @Bean
    public RepositoryItemWriter<SalesInfo> salesInfoJpaItemWriter(){
        return new RepositoryItemWriterBuilder<SalesInfo>()
                .repository(salesInfoRepository)
                .methodName("save")
                .build();
    }
    @Bean
    public Step moveFile(JobRepository jobRepository, PlatformTransactionManager transactionManager, SalesInfoTasklet tasklet) {
        return new StepBuilder("moveFile", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

}
