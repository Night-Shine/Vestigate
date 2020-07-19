package com.nightshine.vestigate.utils;

import com.nightshine.vestigate.model.Company;
import com.nightshine.vestigate.model.RoleType;
import com.nightshine.vestigate.model.User;
import com.nightshine.vestigate.payload.request.CompanyRegistration_UploadRequest;
import com.nightshine.vestigate.payload.request.UserUpdateRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Helper {

    public static void copyUserDetails(User user, UserUpdateRequest userRequest) {
        if (userRequest.getName() != null)
            user.setName(userRequest.getName());
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getUsername() != null)
            user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null)
            user.setPassword(userRequest.getPassword());
        if (userRequest.getPosition() != null)
            user.setPosition(userRequest.getPosition());
        if (userRequest.getRoleType() != null)
            user.setRoleType(userRequest.getRoleType());
        if (userRequest.getImage() != null)
            user.setImage(userRequest.getImage());
    }

    public static Company copyCompanyDetails(Company company, CompanyRegistration_UploadRequest companyRegistrationUploadRequest) {
        if (companyRegistrationUploadRequest.getCompanyName() != null)
            company.setCompanyName(companyRegistrationUploadRequest.getCompanyName());
        if (companyRegistrationUploadRequest.getDescription() != null)
            company.setDescription(companyRegistrationUploadRequest.getDescription());
        if (companyRegistrationUploadRequest.getProjects() != null)
            company.setProjects(companyRegistrationUploadRequest.getProjects());
        return company;
    }
}
