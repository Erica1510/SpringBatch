package com.example.springbatch.batch.processor;

        import com.example.springbatch.batch.validator.Validator;
        import com.example.springbatch.batch.entity.SalesInfo;
        import org.springframework.batch.item.ItemProcessor;
        import org.springframework.batch.item.validator.ValidationException;
        import org.springframework.stereotype.Component;

@Component
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfo,SalesInfo> {

    @Override
    public SalesInfo process(SalesInfo item) throws ValidationException {
        if (Validator.validateSalesInfo(item).isEmpty()) {
            return item; // If validation passes, return the item
        } else {
            throw new ValidationException("Provided SalesInfo is not valid");
        }
    }
}
