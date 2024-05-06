package com.dorayd.sports.features.address.repositories;

import com.dorayd.sports.features.address.models.Address;

import java.util.Optional;

public interface AddressRepository {
    Optional<Address> findById(long id);
    Address create(Address address);
    Address update(long id, Address address);
    boolean delete(long id);
}
