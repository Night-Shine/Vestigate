package com.nightshine.vestigate.controller.company;

import com.nightshine.vestigate.model.company.Company;
import com.nightshine.vestigate.payload.request.company.CompanyRegistration_UpdateRequest;
import com.nightshine.vestigate.payload.response.ApiResponse;
import com.nightshine.vestigate.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<Company> registerCompany(@Valid @RequestBody CompanyRegistration_UpdateRequest companyRegistrationUploadRequest) {
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

    @GetMapping("/allCompanies/{CompanyId}")
    public ResponseEntity<Optional<Company>> getSingleCompany(@Valid @PathVariable UUID CompanyId) {
        return new ResponseEntity<>(companyService.getSingleCompany(CompanyId), HttpStatus.OK);
    }

    @PutMapping("/allCompanies/{CompanyId}")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody CompanyRegistration_UpdateRequest companyRegistrationUploadRequest,
                                                 @PathVariable UUID CompanyId) {
        ResponseEntity status = companyService.updateCompany(companyRegistrationUploadRequest, CompanyId);
        if(status.getStatusCodeValue() == 400)
            return new ResponseEntity(status.getBody(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(status.getBody(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompanyById(@Valid @PathVariable UUID companyId) {
        ResponseEntity status = companyService.removeCompany(companyId);
        return new ResponseEntity(status.getBody(), status.getStatusCode());
    }

    @DeleteMapping("/deleteMultipleCompanies")
    public ResponseEntity<?> deleteMultipleCompanies(@Valid @RequestBody List<UUID> ids) {
        ResponseEntity status = companyService.removeMultipleCompanies(ids);
        return new ResponseEntity(status.getBody(), status.getStatusCode());
    }
}
