package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Accessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccessoryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Accessory> fetchAll() {
        String sqlUse = "USE NMR;";
        jdbcTemplate.update(sqlUse);
        String sql = "SELECT * FROM nmr.accessories;";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNew(Accessory accessory) {
        String sql = "INSERT INTO NMR.accessories " +
                "VALUES (DEFAULT, ?, ?);";
        jdbcTemplate.update(sql, accessory.getName(), accessory.getPrice());
    }

    public void update(Accessory inputAccessory, int id) {
        Accessory accessory = findById(id);
        String sql = "UPDATE NMR.accessories " +
                "SET name = ?, price = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, inputAccessory.getName(), inputAccessory.getPrice(), accessory.getId());
    }

    /**
     * @Author Jimmy
     * @param id
     * @return
     */
    public Accessory findById(int id) {
        String sql = "SELECT * FROM NMR.accessories " +
                "WHERE id = ?;";
        RowMapper<Accessory> rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM NMR.accessories " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
