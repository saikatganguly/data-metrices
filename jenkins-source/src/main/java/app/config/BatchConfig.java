package app.config;


import app.importer.JenkinsDataImporter;
import app.listener.JobCompletionListener;
import app.sender.JenkinsMessageSender;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BatchConfig extends DefaultBatchConfigurer {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    JenkinsMessageSender sender;

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(jenkinsStep()).end().build();
    }

    @Bean
    public Step jenkinsStep() {
        return stepBuilderFactory.get("jenkinsStep").<String, String>chunk(1).reader(new JenkinsDataImporter()).writer(sender).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        //This BatchConfigurer ignores any DataSource
    }
}
