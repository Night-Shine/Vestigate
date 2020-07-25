package com.nightshine.vestigate.repository.boards;

import java.util.List;

public interface CustomBoardsRepository<T,ID> {
    void deleteAll(List<String> ids);
}
