package com.example.springbatch.batch.repository;

import com.example.springbatch.batch.entity.SalesInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInfoRepository extends CrudRepository<SalesInfo,Integer> {
}
