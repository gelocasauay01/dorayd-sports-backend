package com.dorayd.sports.features.address.repositories;

import com.dorayd.sports.features.address.models.Address;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("addresses")
            .usingGeneratedKeyColumns("id");

    }

    @Override
    public Optional<Address> findById(long id) {
        try {
            final String FIND_BY_ID_QUERY = "SELECT * FROM addresses WHERE id = ?";
            Address address = jdbcTemplate.queryForObject(
                FIND_BY_ID_QUERY,
                (rs, rowNum) -> new Address(
                    rs.getLong("id"),
                    rs.getString("address_line_one"),
                    rs.getString("address_line_two"),
                    rs.getString("municipality"),
                    rs.getString("province")
                ),
                id
            );
            return Optional.ofNullable(address);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Address create(Address address) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("address_line_one", address.getAddressLineOne());
        parameters.put("address_line_two", address.getAddressLineTwo());
        parameters.put("municipality", address.getMunicipality());
        parameters.put("province", address.getProvince());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findById(id.longValue()).orElseThrow();
    }

    @Override
    public Address update(long id, Address address) {
        final String UPDATE_QUERY = "UPDATE addresses SET address_line_one = ?, address_line_two = ?, municipality = ?, province = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_QUERY, address.getAddressLineOne(), address.getAddressLineTwo(), address.getMunicipality(), address.getProvince(), id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean delete(long id) {
        final String DELETE_QUERY = "DELETE FROM addresses WHERE id = ?";
        int deletedCount = jdbcTemplate.update(DELETE_QUERY, id);
        return deletedCount > 0;
    }


}
