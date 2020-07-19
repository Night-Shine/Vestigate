package com.nightshine.vestigate.repository.company;

import java.util.List;

public interface CustomCompanyRepository <T,ID> {
    void deleteById(ID id);

    void deleteAll(List<String> ids);
}