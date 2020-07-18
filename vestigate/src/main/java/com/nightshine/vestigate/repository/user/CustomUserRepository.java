package com.nightshine.vestigate.repository.user;

import java.util.List;

public interface CustomUserRepository<T,ID> {
    void deleteById(ID id);

    void deleteAll(List<String> ids);
}