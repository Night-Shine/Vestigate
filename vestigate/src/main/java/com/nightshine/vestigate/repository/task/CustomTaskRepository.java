package com.nightshine.vestigate.repository.task;

import java.util.List;

public interface CustomTaskRepository <T,ID>{

    void deleteAll(List<String> ids);
}
