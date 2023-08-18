package com.example.springbatch.batch.validator;


import com.example.springbatch.batch.entity.SalesInfo;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static List<String> validateSalesInfo(SalesInfo salesInfo) {
        List<String> validationErrors = new ArrayList<>();
        if (salesInfo.getId() == null ) {
            validationErrors.add("Product Id is required.");
        }
        if (salesInfo.getProduct() == null || salesInfo.getProduct().isEmpty()) {
            validationErrors.add("Product name is required.");
        }

        if (salesInfo.getSeller() == null || salesInfo.getSeller().isEmpty()) {
            validationErrors.add("Seller name is required.");
        }

        if (salesInfo.getSellerId() == null) {
            validationErrors.add("Seller ID is required.");
        } else if (salesInfo.getSellerId() <= 0) {
            validationErrors.add("Seller ID must be a positive value.");
        }

        if (salesInfo.getPrice() <= 0) {
            validationErrors.add("Price must be a positive value.");
        }

        if (salesInfo.getCategory() == null || salesInfo.getCategory().isEmpty()) {
            validationErrors.add("Category is required.");
        }

        return validationErrors;
    }


}
