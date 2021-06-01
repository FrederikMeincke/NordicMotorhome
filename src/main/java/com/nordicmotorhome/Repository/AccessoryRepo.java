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
public class AccessoryRepo implements CRUDRepo<Accessory>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    static final String SQL_USE = "USE NMR;";

    /**
     *
     * @return
     */
    public List<Accessory> fetchAll() {
        jdbcTemplate.update(SQL_USE);
        String sql = "SELECT * FROM nmr.accessories;";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     *
     * @param accessory
     */
    public void addNew(Accessory accessory) {
        String sql = "INSERT INTO NMR.accessories " +
                "VALUES (DEFAULT, ?, ?);";
        jdbcTemplate.update(sql, accessory.getName(), accessory.getPrice());
    }

    /**
     *
     * @param inputAccessory
     * @param id
     */
    public void update(Accessory inputAccessory, int id) {
        String sql = "UPDATE NMR.accessories " +
                "SET name = ?, price = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, inputAccessory.getName(), inputAccessory.getPrice(), id);
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

    /**
     *
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM NMR.accessories " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean hasConstraint(int id) {
        String sql = "SELECT\n" +
                "\tCASE WHEN EXISTS \n" +
                "    (\n" +
                "    SELECT * FROM NMR.rental_Accessories WHERE accessories_fk = ?\n" +
                "    )\n" +
                "    THEN 'TRUE'\n" +
                "    ELSE 'FALSE'\n" +
                "END";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        rowSet.next();
        return Boolean.parseBoolean(rowSet.getString(1));
    }
}