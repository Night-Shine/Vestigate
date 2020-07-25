package com.nightshine.vestigate.repository.projects;

import java.util.List;

public interface CustomProjectRepository <T,ID>{

    void deleteAll(List<String> ids);
}
