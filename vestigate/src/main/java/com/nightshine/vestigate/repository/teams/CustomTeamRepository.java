package com.nightshine.vestigate.repository.teams;

import java.util.List;

public interface CustomTeamRepository <T,ID>{

    void deleteAll(List<String> ids);
}
