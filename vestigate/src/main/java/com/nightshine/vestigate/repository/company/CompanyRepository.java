package com.nightshine.vestigate.repository.company;

import com.nightshine.vestigate.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String>, CustomCompanyRepository<String, String> {
    Optional<Company> findByCompanyName(String companyName);

    Boolean existsByCompanyName(String companyName);

    @Query("{isDeleted:false}")
    List<Company> findAll();

    @Query("{isDeleted:false, id:?0}")
    Optional<Company> findById(String id);
}
