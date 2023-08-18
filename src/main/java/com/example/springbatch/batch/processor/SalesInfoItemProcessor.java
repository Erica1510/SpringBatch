package com.example.springbatch.batch.processor;

        import com.example.springbatch.batch.validator.Validator;
        import com.example.springbatch.batch.entity.SalesInfo;
        import org.springframework.batch.item.ItemProcessor;
        import org.springframework.batch.item.validator.ValidationException;
        import org.springframework.stereotype.Component;

@Component
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfo,SalesInfo> {
/**Процессор в Spring Batch отвечает за преобразование данных, обычно в пределах одной порции (chunk) записей.
 Процессор принимает входные данные, выполняет над ними какие-либо операции и возвращает трансформированные данные.
  Примеры операций, которые может выполнять процессор, включают валидацию данных, преобразование форматов, фильтрацию записей*/

/**проверить валидность каждой записи перед сохранением в базу данных.
 * В этом случае, процессор может выполнять следующие шаги:

    Принять входные данные (например, строки CSV).
    Разбить каждую строку на поля (например, разделить по запятым).
    Проверить каждое поле на соответствие ожидаемому формату (например, проверить, что цена является числом).
    Если данные прошли валидацию, создать объект SalesInfo (например, Java-объект) и передать его дальше.*/
    @Override
    public SalesInfo process(SalesInfo item) throws ValidationException {
        if (Validator.validateSalesInfo(item).isEmpty()) {
            return item; // If validation passes, return the item
        } else {
            throw new ValidationException("Provided SalesInfo is not valid");
        }
    }
}
