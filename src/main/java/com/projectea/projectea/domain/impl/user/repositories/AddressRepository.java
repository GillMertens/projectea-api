package com.projectea.projectea.domain.impl.user.repositories;

import com.projectea.projectea.domain.impl.user.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
