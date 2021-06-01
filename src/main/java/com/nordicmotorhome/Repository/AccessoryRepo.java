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
     * @author Jimmy
     * Returns a List<Accessory> of all accessory entities from the database.
     * @return List<Accessory>
     */
    public List<Accessory> fetchAll() {
        jdbcTemplate.update(SQL_USE);
        String sql = "SELECT * FROM nmr.accessories;";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * @author Jimmy
     * Inserts a new entity to the accessories table with information from the Accessory accessory.
     * @param accessory
     */
    public void addNew(Accessory accessory) {
        String sql = "INSERT INTO NMR.accessories " +
                "VALUES (DEFAULT, ?, ?);";
        jdbcTemplate.update(sql, accessory.getName(), accessory.getPrice());
    }

    /**
     * @author Jimmy
     * Updates the column values for an accessory entity from an Accessory inputAccessory and identifies the entity in
     * the accessories table with int id.
     * @param inputAccessory
     * @param id int
     */
    public void update(Accessory inputAccessory, int id) {
        String sql = "UPDATE NMR.accessories " +
                "SET name = ?, price = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, inputAccessory.getName(), inputAccessory.getPrice(), id);
    }

    /**
     * @author Jimmy
     * Finds an accessory entity in the database from its primary key and returns an Accessory object with the relevant data.
     * @param id int
     * @return Accessory
     */
    public Accessory findById(int id) {
        String sql = "SELECT * FROM NMR.accessories " +
                "WHERE id = ?;";
        RowMapper<Accessory> rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    /**
     * @author Jimmy
     * Deletes an accessory entity in the database.
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM NMR.accessories " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    /**
     * @author Jimmy
     * The method checks if an accessory entity's primary key is a foreign key in the rental_accessories table.
     * @param id int
     * @return boolean
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