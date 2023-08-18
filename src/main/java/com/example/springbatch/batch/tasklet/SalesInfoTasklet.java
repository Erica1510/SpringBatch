package com.example.springbatch.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
@StepScope
public class SalesInfoTasklet implements Tasklet {
/**Тасклет - это ещё один способ выполнения определенной логики в рамках Spring Batch Job.
 * В отличие от процессора, который работает с порциями данных, тасклет выполняет какую-либо логику,
 * которая не требует обработки больших объемов данных. Тасклеты могут использоваться для задач,
 * таких как копирование файлов, запуск SQL-запросов или любой другой пользовательской логики.

 Например, после того как данные были сохранены в базу данных, вы хотите переместить обработанный CSV-файл
 в другую директорию. Для этого вы можете использовать тасклет:

 Прочитать путь к файлу для обработки из настроек.
 Переместить файл из этой директории в другую с помощью операции копирования или перемещения.*/

    @Value("${file.path.to.be.processed}")
    private String processingFilePath;
    @Value("${file.path.was.processed}")
    private String processedFilePath;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File before = new File(processingFilePath);
        File after = new File(processedFilePath);

        Files.move(before.toPath(), after.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return RepeatStatus.FINISHED;
    }
}
