package com.rabobank.statement.process;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * <h1>Statement process Test!</h1>
 *
 * @author Kingshuk Mukherjee - 233303
 * @version 1.0
 * @since 02/10/2019
 */

@ContextConfiguration(classes = {CustomerStatementProcessConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class StatementProcessTest {

    private final int EXPECTED_COUNT = 3;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private FlatFileItemReader<CustomerStatementModel> reader;

    private ItemProcessor<CustomerStatementModel, CustomerStatementModel> statementProcessor;
    @Value("classpath:/input/test_positive_records.csv")
    private Resource testInputResourcePositive;

    @Before
    public void setUp() throws Exception {
        reader.setResource(testInputResourcePositive);
        reader.setLinesToSkip(1);

        // open, provide "mock" ExecutionContext
        reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
    }

    @Test
    public void testRecordsCount() throws Exception {

        // read
        try {
            int count = 0;
            while (reader.read() != null) {
                count++;
            }
            assertEquals(EXPECTED_COUNT, count);
        } catch (Exception e) {
            throw e;
        } finally {
            reader.close();
        }
    }

    @Test
    public void testCsvFileProcessingStepSuccess() throws Exception {
        resetModelProcessor();
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("csvFileProcessingStep");
        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCsvFileProcessingJobSuccess() throws Exception {
        resetModelProcessor();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobLauncherTestUtils.getUniqueJobParameters());
        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    void resetModelProcessor() {
        statementProcessor = new CustomerStatementModelProcessor();
    }
}
