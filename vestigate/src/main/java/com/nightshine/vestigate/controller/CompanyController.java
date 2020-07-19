package com.nightshine.vestigate.controller;

import com.nightshine.vestigate.exception.ResourceNotFound;
import com.nightshine.vestigate.model.Company;
import com.nightshine.vestigate.payload.request.CompanyRegistration_UploadRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<Company> registerCompany(@Valid @RequestBody CompanyRegistration_UploadRequest companyRegistrationUploadRequest) {
        if(companyService.checkCompanyExists(companyRegistrationUploadRequest)) {
            return new ResponseEntity(new ApiResponse(false, "Company name is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(companyService.createCompany(companyRegistrationUploadRequest), HttpStatus.CREATED);
    }

    @GetMapping("/allCompanies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/allCompanies/{companyName}")
    public ResponseEntity<Optional<Company>> getSingleCompany(@Valid @PathVariable String companyName) {
        return new ResponseEntity<>(companyService.getSingleCompany(companyName), HttpStatus.OK);
    }

    @PutMapping("/allCompanies/{companyName}")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody CompanyRegistration_UploadRequest companyRegistrationUploadRequest,
                                                 @PathVariable String companyName) throws ResourceNotFound {
        Company company = companyService.updateCompany(companyRegistrationUploadRequest, companyName);
        return new ResponseEntity<>(company, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompanyById(@Valid @PathVariable String companyId) {
        companyService.removeCompany(companyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteMultipleCompanies")
    public ResponseEntity<?> deleteMultipleCompanies(@Valid @RequestBody List<String> ids) {
        companyService.removeMultipleCompanies(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
