package com.nightshine.vestigate.service;

import com.nightshine.vestigate.exception.ResourceNotFound;
import com.nightshine.vestigate.model.Company;
import com.nightshine.vestigate.payload.request.CompanyRegistration_UploadRequest;
import com.nightshine.vestigate.repository.company.CompanyRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public boolean checkCompanyExists(CompanyRegistration_UploadRequest companyRegistrationUploadRequest) {
        return companyRepository.existsByCompanyName(companyRegistrationUploadRequest.getCompanyName());
    }

    public Company createCompany(CompanyRegistration_UploadRequest companyRegistrationUploadRequest) {
        Company company = new Company(
                companyRegistrationUploadRequest.getCompanyName(),
                companyRegistrationUploadRequest.getDescription(),
                companyRegistrationUploadRequest.getProjects()
        );
        companyRepository.save(company);
        return company;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getSingleCompany(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    public Company updateCompany(CompanyRegistration_UploadRequest companyRegistrationUploadRequest, String companyName) throws ResourceNotFound {
        Optional<Company> optionalCompany = companyRepository.findByCompanyName(companyName);
        if(!optionalCompany.isPresent() || !companyRepository.existsByCompanyName(companyName)) {
            throw new ResourceNotFound( "Company doesn't exists!");
        }
        Company company = optionalCompany.get();
        Helper.copyCompanyDetails(company, companyRegistrationUploadRequest);
        return companyRepository.save(company);
    }

    public void removeCompany(String companyId) {
        companyRepository.deleteById(companyId);
    }

    public void removeMultipleCompanies(List<String> ids) {
        companyRepository.deleteAll(ids);
    }
}
