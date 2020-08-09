package com.nightshine.vestigate.service.company;

import com.nightshine.vestigate.model.company.Company;
import com.nightshine.vestigate.payload.request.company.CompanyRegistration_UpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.repository.company.CompanyRepository;
import com.nightshine.vestigate.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public boolean checkCompanyExists(CompanyRegistration_UpdateRequest companyRegistrationUploadRequest) {
        return companyRepository.existsByCompanyName(companyRegistrationUploadRequest.getCompanyName());
    }

    public Company createCompany(CompanyRegistration_UpdateRequest companyRegistrationUploadRequest) {
        Company company = new Company();
        company.setCompanyName(companyRegistrationUploadRequest.getCompanyName());
        company.setDescription(companyRegistrationUploadRequest.getDescription());
        company.setProjects(companyRegistrationUploadRequest.getProjects());
        companyRepository.save(company);
        return company;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getSingleCompany(UUID companyId) {
        return companyRepository.findById(companyId);
    }

    public ResponseEntity<?> updateCompany(CompanyRegistration_UpdateRequest companyRegistrationUploadRequest, UUID companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if(!optionalCompany.isPresent() || !companyRepository.existsByCompanyName(optionalCompany.get().getCompanyName())) {
            return new ResponseEntity(new ApiResponse(false, "Company doesn't exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        Company company = optionalCompany.get();
        Helper.copyCompanyDetails(company, companyRegistrationUploadRequest);
        return new ResponseEntity(company, HttpStatus.ACCEPTED);
    }

    public void removeCompany(UUID companyId) {
        companyRepository.deleteById(companyId);
    }

    public void removeMultipleCompanies(List<UUID> ids) {
        companyRepository.deleteAll(ids);
    }
}
