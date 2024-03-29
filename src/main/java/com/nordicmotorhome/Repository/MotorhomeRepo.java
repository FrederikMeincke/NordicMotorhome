package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class MotorhomeRepo implements CRUDRepo<Motorhome>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SQL_USE = "USE NMR;";

    /**
     * Gets all the motorhomes from the database and adds all the utilities that a motorhome has to the motorhome object
     * @author Mads
     * @return List<Motorhome>
     */
    public List<Motorhome> fetchAll() {
        String sqlMotorhome =
                "SELECT motorhomes.id, b.name as brand, m.name as model, price, m.fuel_type, type," +
                        " bed_amount, m.weight, m.width, m.height," +
                        " license_plate, register_date, odometer, models_fk, ready_status FROM motorhomes" +
                        " INNER JOIN models m on motorhomes.models_fk = m.id" +
                        " INNER JOIN brands b on m.brands_fk = b.id" +
                        " ORDER BY motorhomes.id;";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);

        jdbcTemplate.update(SQL_USE);    //selects the database
        List<Motorhome> motorhomeList = jdbcTemplate.query(sqlMotorhome,rowMapper);

        // adds the utilities to the motorhome object
        setUtilitiesForMotorhomes(motorhomeList);
        return motorhomeList;
    }

    /**
     * adds utilities to motorhomes
     * @author Mads
     * @param motorhomeList List
     */
    public void setUtilitiesForMotorhomes (List<Motorhome> motorhomeList) {
        for (Motorhome motor: motorhomeList) {
            int id = motor.getId();

            String sqlUtil = "SELECT u.id FROM utilities u\n " +
                    "INNER JOIN motorhome_utilities mu on u.id = mu.utilities_fk\n " +
                    "WHERE mu.motorhomes_fk = ?;";

            //gets all the ids of the utilities belonging to the motorhome
            jdbcTemplate.update(SQL_USE);
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlUtil, id);

            boolean[] tmpUtilArray = motor.getUtilityArray();

            while (rowSet.next()) {
                tmpUtilArray[rowSet.getInt(1) - 1] = true;
                //sets the utility to true if the sql statement has the respective id for the utility
            }
            motor.setUtilityArray(tmpUtilArray);    //sets the array of the motorhome to the updated one.
            // Dont know if this is necessary if the array is reference type
        }
    }

    /**
     * @author Kasper N. Jensen
     * @param motorhome Motorhome
     * This method adds a new motorhome to the db
     */
    public void addNew(Motorhome motorhome) {

        // Step that adds a brand and finds the last added brand id
        String brandSql = "INSERT INTO NMR.brands (id, name) " +
                "VALUES (DEFAULT, ?);";
        jdbcTemplate.update(SQL_USE);
        jdbcTemplate.update(brandSql, motorhome.getBrand());
        String lastAddedBrand = "SELECT * FROM NMR.brands " +
                "ORDER BY id DESC LIMIT 1;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(lastAddedBrand);
        rowSet.next();

        // takes the last added brand id to create a new model under that brand then finds the last added model id
        int lastBrandId = rowSet.getInt("id");
        String modelSql = "INSERT INTO NMR.models (id, name, fuel_type, width, height, weight, brands_fk) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(modelSql, motorhome.getModel(), motorhome.getFuel_type(), motorhome.getWidth(),
                motorhome.getHeight(), motorhome.getWeight(), lastBrandId);
        String lastAddedModel = "SELECT * FROM NMR.models " +
                "ORDER BY id DESC LIMIT 1;";
        rowSet = jdbcTemplate.queryForRowSet(lastAddedModel);
        rowSet.next();


        // takes the last added model id to create a new motorhome and finds the last added motorhome id
        int lastModelId = rowSet.getInt("id");
        String motorhomeSql = "INSERT INTO NMR.motorhomes (id, type, bed_amount, license_plate, register_date, price, " +
                "odometer, ready_status, models_fk) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(motorhomeSql, motorhome.getType(), motorhome.getBed_amount(), motorhome.getLicense_plate(),
                motorhome.getRegister_date(), motorhome.getPrice(), motorhome.getOdometer(), 1, lastModelId);
        String lastAddedMotorhome = "SELECT id FROM NMR.motorhomes " +
                "ORDER BY id DESC LIMIT 1;";

        rowSet = jdbcTemplate.queryForRowSet(lastAddedMotorhome);
        rowSet.next();
        int lastMotorhomeId = rowSet.getInt("id");


        // Uses the last added motorhome id to apply utilities to the motorhome.
        utilInsert(motorhome.getUtility_0(),lastMotorhomeId,1); //TV
        utilInsert(motorhome.getUtility_1(),lastMotorhomeId,2); //Fridge
        utilInsert(motorhome.getUtility_2(),lastMotorhomeId,3); //Shower
        utilInsert(motorhome.getUtility_3(),lastMotorhomeId,4); //Toilet
        utilInsert(motorhome.getUtility_4(),lastMotorhomeId,5); //Sofa
        utilInsert(motorhome.getUtility_5(),lastMotorhomeId,6); //Table
        utilInsert(motorhome.getUtility_6(),lastMotorhomeId,7); //Kitchen
        utilInsert(motorhome.getUtility_7(),lastMotorhomeId,8); //Awning
    }

    /**
     * Method takes util id from addNewMotorhome.html and gets the last added id from a motorhome
     * and finally it takes a utillity id
     * @author Kasper N. Jensen
     * @param utilTrue int
     * @param lastMotorhomeId int
     * @param utilNr int
     */
    public void utilInsert(int utilTrue, int lastMotorhomeId, int utilNr){
        if(utilTrue == 1) {
            String util_1_sql = "INSERT INTO NMR.motorhome_utilities (id, motorhomes_fk, utilities_fk) " +
                    "VALUES (DEFAULT, ?, ?);";
            jdbcTemplate.update(SQL_USE);
            jdbcTemplate.update(util_1_sql, lastMotorhomeId, utilNr);
        }
    }


    /**
     * Deleting a Motorhome from the database, and removes external settings(Brand, Model,Daily Price, etc)
     * @param id int
     * @author Frederik M.
     */
        public void delete(int id) {
            String sqlDeleteUtil = "DELETE FROM motorhome_utilities WHERE motorhomes_fk = ?";
            String sql = "DELETE FROM NMR.motorhomes WHERE id = ?";
            jdbcTemplate.update(SQL_USE);
            jdbcTemplate.update(sqlDeleteUtil, id);
            jdbcTemplate.update(sql, id);
        }

    /**
     * This method updates a motorhome
     * @author Kasper N. Jensen, Jimmy, Mads
     * @param inputMotorhome Motorhome
     * @param id int
     */
    public void update(Motorhome inputMotorhome, int id) {
        Motorhome motorhome = findById(id);

        jdbcTemplate.update(SQL_USE);

        String sqlBrandExists = " SELECT " +
                " CASE WHEN EXISTS "+
                " (" +
                "SELECT * FROM brands WHERE brands.name = ? " +
                ")" +
                " THEN 'TRUE' " +
                " ELSE 'FALSE'" +
                " END;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlBrandExists, inputMotorhome.getBrand());
        rowSet.next();
        boolean brandExists = Boolean.parseBoolean(rowSet.getString(1));

        String sqlUpdateBrand = "UPDATE models " +
                "SET brands_fk = ? " +
                "WHERE id = ?;";

        String sqlFindBrandID = "SELECT brands.id " +
                "FROM brands " +
                "WHERE brands.name = ?";


        if (brandExists) {
            SqlRowSet rowSetBrandId = jdbcTemplate.queryForRowSet(sqlFindBrandID, inputMotorhome.getBrand());
            rowSetBrandId.next();
            int brandId = rowSetBrandId.getInt(1);


            jdbcTemplate.update(sqlUpdateBrand, brandId, motorhome.getModels_fk());
        } else {   //creates a new entry in the brands table if the entered brand doesnt already exists
            String sqlCreateBrand = "INSERT INTO brands " +
                    "VALUES(DEFAULT, ?)";
            jdbcTemplate.update(sqlCreateBrand, inputMotorhome.getBrand());

            String sqlLastAddedBrand = "SELECT * FROM NMR.brands " +
                    "ORDER BY id DESC LIMIT 1;";
            SqlRowSet rowSetLastAdded = jdbcTemplate.queryForRowSet(sqlLastAddedBrand);
            rowSetLastAdded.next();

            int lastAddedBrandId = rowSetLastAdded.getInt(1);   //id of the last added brand so we can update current motorhome
            jdbcTemplate.update(sqlUpdateBrand, lastAddedBrandId, motorhome.getModels_fk());
        }

        String sqlModelExists = " SELECT " +
                " CASE WHEN EXISTS "+
                " (" +
                "SELECT * FROM models WHERE models.name = ? " +
                ")" +
                " THEN 'TRUE' " +
                " ELSE 'FALSE'" +
                " END;";
        SqlRowSet rowSetModel = jdbcTemplate.queryForRowSet(sqlModelExists, inputMotorhome.getModel());
        rowSetModel.next();
        boolean modelExists = Boolean.parseBoolean(rowSetModel.getString(1));

        String sqlUpdateModel = "UPDATE motorhomes " +
                "SET models_fk = ? " +
                "WHERE id = ?;";

        if (modelExists) {
            String sqlFindModelID = "SELECT models.id " +
                    "FROM models " +
                    "WHERE models.name = ?";
            SqlRowSet rowSetModelId = jdbcTemplate.queryForRowSet(sqlFindModelID, inputMotorhome.getModel());
            rowSetModelId.next();
            int modelId = rowSetModelId.getInt(1);

            jdbcTemplate.update(sqlUpdateModel, modelId, motorhome.getId());

        } else {   //creates a new entry in the brands table if the entered model doesnt already exists
            String sqlCreateModel = "INSERT INTO models " +
                    "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";

            SqlRowSet rowSetBrandId = jdbcTemplate.queryForRowSet(sqlFindBrandID, inputMotorhome.getBrand());
            rowSetBrandId.next();
            int brandId = rowSetBrandId.getInt(1);

            jdbcTemplate.update(sqlCreateModel, inputMotorhome.getModel(), inputMotorhome.getFuel_type(),
                    inputMotorhome.getWidth(), inputMotorhome.getHeight(), inputMotorhome.getWeight(),
                    brandId);

            String sqlLastAddedModel = "SELECT id FROM models " +
                    "ORDER BY id DESC LIMIT 1;";
            SqlRowSet rowSetLastAdded = jdbcTemplate.queryForRowSet(sqlLastAddedModel);
            rowSetLastAdded.next();

            int lastAddedModelId = rowSetLastAdded.getInt(1);   //id of the last added brand so we can update current motorhome
            jdbcTemplate.update(sqlUpdateModel, lastAddedModelId, motorhome.getId());

        }

        String sqlMotorhome = "UPDATE motorhomes \n" +
                "SET type = ?, bed_amount = ?, license_plate = ?, register_date = ?, " +
                "price = ?, odometer = ?, ready_status = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sqlMotorhome, inputMotorhome.getType(), inputMotorhome.getBed_amount(), inputMotorhome.getLicense_plate(),
                inputMotorhome.getRegister_date(), inputMotorhome.getPrice(), inputMotorhome.getOdometer(),
                inputMotorhome.getReady_status(), id);

        String sqlUpdateModelDimensions = "UPDATE models " +
                "SET width = ?, height = ?,  weight = ? " +
                "WHERE models.id = ?";

        jdbcTemplate.update(sqlUpdateModelDimensions, inputMotorhome.getWidth(), inputMotorhome.getHeight(),
                inputMotorhome.getWeight(), motorhome.getModels_fk());

        String sqlDropUtils = "DELETE FROM NMR.motorhome_utilities WHERE motorhomes_fk = ?";

        jdbcTemplate.update(SQL_USE);
        jdbcTemplate.update(sqlDropUtils,id);

        utilInsert(inputMotorhome.getUtility_0(),id,1); //TV
        utilInsert(inputMotorhome.getUtility_1(),id,2); //Fridge
        utilInsert(inputMotorhome.getUtility_3(),id,4); //Toilet
        utilInsert(inputMotorhome.getUtility_4(),id,5); //Sofa
        utilInsert(inputMotorhome.getUtility_5(),id,6); //Table
        utilInsert(inputMotorhome.getUtility_2(),id,3); //Shower
        utilInsert(inputMotorhome.getUtility_6(),id,7); //Kitchen
        utilInsert(inputMotorhome.getUtility_7(),id,8); //Awning
    }

    /**
     * This method finds a motorhome by its id.
     * @author Mads
     * @param id int
     * @return Motorhome
     */
    public Motorhome findById(int id) {
        String sqlFind = "SELECT motorhomes.id, b.name as brand, m.name as model, price, m.fuel_type, type,\n" +
                "bed_amount, m.weight, m.width, m.height,\n" +
                "license_plate, register_date, odometer, models_fk FROM motorhomes\n" +
                "INNER JOIN models m on motorhomes.models_fk = m.id\n" +
                "INNER JOIN brands b on m.brands_fk = b.id\n" +
                "WHERE motorhomes.id = ?;";

        RowMapper rowMapper = new BeanPropertyRowMapper(Motorhome.class);
        jdbcTemplate.update(SQL_USE);
        List<Motorhome> motorhomeList = jdbcTemplate.query(sqlFind, rowMapper, id);
        //Dont know how to use queryForObject because it uses lambda expressions so this is done with a query instead
        //even if its only returning one object
        setUtilitiesForMotorhomes(motorhomeList);
        return motorhomeList.get(0);
    }

    /**
     * @author Jimmy, Mads
     *
     * @param id int
     * @return boolean
     */
    public boolean hasConstraint(int id) {
        String sql = "SELECT\n" +
                "\tCASE WHEN EXISTS \n" +
                "    (\n" +
                "    SELECT * FROM NMR.rentals WHERE motorhomes_fk = ?\n" +
                "    )\n" +
                "    THEN 'TRUE'\n" +
                "    ELSE 'FALSE'\n" +
                "END";
        jdbcTemplate.update(SQL_USE);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        rowSet.next();
        return Boolean.parseBoolean(rowSet.getString(1));
    }
}