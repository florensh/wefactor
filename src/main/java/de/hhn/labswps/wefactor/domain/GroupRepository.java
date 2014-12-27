package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {

    List<Group> findByNameContaining(String name);

    List<Group> findByMembers(Account a);
}
