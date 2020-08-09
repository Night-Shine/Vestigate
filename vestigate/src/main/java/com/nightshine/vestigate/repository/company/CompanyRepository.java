package com.nightshine.vestigate.repository.company;

import com.nightshine.vestigate.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    @Query("SELECT P FROM Company P WHERE P.isDeleted=false")
    List<Company> findAll();

    @Query("SELECT P FROM Company P WHERE P.isDeleted=false and P.id=:companyId")
    Optional<Company> findById(UUID companyId);

    boolean existsByCompanyName(String companyName);

    @Modifying
    @Query("UPDATE Company c SET c.isDeleted=true WHERE c.id IN :ids")
    void deleteAll(List<UUID> ids);

    @Modifying
    @Query("UPDATE Company c SET c.isDeleted=true WHERE c.id=:id")
    void deleteById(UUID id);
}
