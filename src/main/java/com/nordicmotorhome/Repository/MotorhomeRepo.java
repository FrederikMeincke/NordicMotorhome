package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class MotorhomeRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Gets all the motorhomes from the database and adds all the utilities that a motorhome has to the motorhome object
     * @author Mads
     * @return
     */
    public List<Motorhome> fetchAllMotorhomes(){
        String sqluse = "USE NMR;";
        String sqlMotorhome =
                "SELECT motorhomes.id, b.name as brand, m.name as model, price, m.fuel_type, type," +
                        " bed_amount, m.weight, m.width, m.height," +
                        " license_plate, register_date, odometer, ready_status FROM motorhomes" +
                        " INNER JOIN models m on motorhomes.models_fk = m.id" +
                        " INNER JOIN brands b on m.brands_fk = b.id" +
                        " ORDER BY motorhomes.id;";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);

        jdbcTemplate.update(sqluse);    //selects the database
        List<Motorhome> motorhomeList = jdbcTemplate.query(sqlMotorhome,rowMapper);

        // adds the utilities to the motorhome object
        for (Motorhome motor: motorhomeList) {
            int id = motor.getId();

            String sqlUtil = "SELECT u.id FROM utilities u\n " +
                    "INNER JOIN motorhome_utilities mu on u.id = mu.utilities_fk\n " +
                    "WHERE mu.motorhomes_fk = ?;";

            //gets all the ids of the utilities belonging to the motorhome
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlUtil, id);

            boolean[] tmpUtilArray = motor.getUtilityArray();

            while (rowSet.next()) {
                tmpUtilArray[rowSet.getInt(1) - 1] = true;
                //sets the utility to true if the sql statement has the respective id for the utility
            }
            motor.setUtilityArray(tmpUtilArray);    //sets the array of the motorhome to the updated one.
            // Dont know if this is necessary if the array is reference type
        }
        return motorhomeList;
    }

    public void addMotorhome(Motorhome motorhome) {

        System.out.println(motorhome.getWeight());
        String brandsql = "INSERT INTO NMR.brands (id, name) " +
                "VALUES (DEFAULT, ?);";
        jdbcTemplate.update(brandsql, motorhome.getBrand());
        String lastAddedBrand = "SELECT * FROM NMR.brands " +
                "ORDER BY id DESC LIMIT 1;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(lastAddedBrand);
        rowSet.next();
        int lastBrandId = rowSet.getInt("id");

        String modelsql = "INSERT INTO NMR.models (id, name, fuel_type, width, height, weight, brands_fk) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(modelsql, motorhome.getModel(), motorhome.getFuel_type(), motorhome.getWidth(),
                motorhome.getHeight(), motorhome.getWeight(), lastBrandId);

        String lastAddedModel = "SELECT * FROM NMR.models " +
                "ORDER BY id DESC LIMIT 1;";
        rowSet = jdbcTemplate.queryForRowSet(lastAddedModel);
        rowSet.next();
        int lastModelId = rowSet.getInt("id");
        String motorhomesql = "INSERT INTO NMR.motorhomes (id, type, bed_amount, license_plate, register_date, price, " +
                "odometer, ready_status, models_fk) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(motorhomesql, motorhome.getType(), motorhome.getBed_amount(), motorhome.getLicense_plate(),
                motorhome.getRegister_date(), motorhome.getPrice(), motorhome.getOdometer(), 1, lastModelId);
    }
}
