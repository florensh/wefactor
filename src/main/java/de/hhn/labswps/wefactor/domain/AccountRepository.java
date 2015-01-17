package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link Account}.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

}
