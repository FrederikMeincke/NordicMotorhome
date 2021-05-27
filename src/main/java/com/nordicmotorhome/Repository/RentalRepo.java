package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalRepo implements CRUDRepo<Rental>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SQL_USE = "USE NMR;";

    /**
     * @author Mads
     * @return
     */
    public List<Rental> fetchAll() {
        String sqlFetch = "SELECT * FROM rentals;";
        RowMapper rowMapper = new BeanPropertyRowMapper(Rental.class);

        jdbcTemplate.update(SQL_USE);
        List<Rental> rentalList = jdbcTemplate.query(sqlFetch, rowMapper);

        return getRentals(rentalList);
    }

    /**
     * @author Mads
     * @param id
     * @return
     */
    public Rental findById(int id) {
        String sqlFetch = "SELECT * FROM rentals WHERE rentals.id = ?";

        RowMapper rowMapper = new BeanPropertyRowMapper(Rental.class);
        List<Rental> rentalList = jdbcTemplate.query(sqlFetch, rowMapper, id);

        return getRentals(rentalList).get(0);   //returns the only entry in the list
    }

    /**
     * @author Mads
     * @param rentalList
     * @return
     */
    private List<Rental> getRentals(List<Rental> rentalList) {
        for (Rental rental : rentalList) {
            //customer
            String sqlCustomer =
                    "SELECT customers.id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                            "dl_expire_date, street, floor, zip, city, countries.name as 'country', addresses_fk" +
                            " FROM NMR.customers " +
                            "INNER JOIN addresses on addresses.id = addresses_fk " +
                            "INNER JOIN zip_codes on zip_codes.id = zip_codes_fk " +
                            "INNER JOIN countries on countries.id = countries_fk " +
                            "WHERE customers.id = ?;";
            RowMapper<Customer> customerRowMapper = new BeanPropertyRowMapper<>(Customer.class);
            List<Customer> customerList =  jdbcTemplate.query(sqlCustomer, customerRowMapper, rental.getCustomers_fk());
            rental.setCustomer(customerList.get(0));

            setMotorhomeById(rental);

            String sqlSeason = "SELECT * FROM rentals " +
                    "WHERE seasons_fk = ? ";
            RowMapper<Season> seasonRowMapper = new BeanPropertyRowMapper<>(Season.class);
            List<Season> seasonList = jdbcTemplate.query(sqlSeason, seasonRowMapper, rental.getSeasons_fk());
            rental.setSeason(seasonList.get(0));
        }

        return rentalList;
    }

    private void setSeasonById(Rental rental) {
        //season
        String sqlSeason = "SELECT * FROM seasons " +
                "WHERE id = ? ";
        RowMapper<Season> seasonRowMapper = new BeanPropertyRowMapper<>(Season.class);
        List<Season> seasonList = jdbcTemplate.query(sqlSeason, seasonRowMapper, rental.getSeasons_fk());
        rental.setSeason(seasonList.get(0));
    }

    private void setMotorhomeById(Rental rental) {
        //motorhome
        String sqlMotorhome = "SELECT motorhomes.id, b.name as brand, m.name as model, price, m.fuel_type, type," +
                " bed_amount, m.weight, m.width, m.height," +
                " license_plate, register_date, odometer, models_fk FROM motorhomes" +
                " INNER JOIN models m on motorhomes.models_fk = m.id" +
                " INNER JOIN brands b on m.brands_fk = b.id " +
                " WHERE motorhomes.id = ?" +
                " ORDER BY motorhomes.id;";
        RowMapper<Motorhome> motorhomeRowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        List<Motorhome> motorhomeList = jdbcTemplate.query(sqlMotorhome, motorhomeRowMapper, rental.getMotorhomes_fk());
        rental.setMotorhome(motorhomeList.get(0));
    }

    public int lastAddedRentalId() {
        String sql = "SELECT id " +
                "FROM rentals " +
                "ORDER BY ID DESC " +
                "LIMIT 1;";
        jdbcTemplate.update(SQL_USE);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        rowSet.next();
        return rowSet.getInt("id");
    }

    public void addNew(Rental inputRental) {
        jdbcTemplate.update(SQL_USE);
        String sqlRental = "INSERT INTO rentals " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String start_date = inputRental.getStart_date();
        String end_date = inputRental.getEnd_date();
        String pick_up_location = inputRental.getPick_up_location();
        String drop_off_location = inputRental.getDrop_off_location();
        int pick_up_distance = inputRental.getPick_up_distance();
        int drop_off_distance = inputRental.getDrop_off_distance();
        String cancel_date = null;
        int customers_fk = inputRental.getCustomers_fk();
        int motorhomes_fk = inputRental.getMotorhomes_fk();
        int seasons_fk = inputRental.getSeasons_fk();
        setMotorhomeById(inputRental);
        setSeasonById(inputRental);

        for(int i = 0; i < inputRental.getAcList().length; i++) {
            if(inputRental.getAcList()[i]) {
                String sql = "SELECT * FROM NMR.accessories " +
                        "WHERE id = ?;";
                SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, i+1);
                rowSet.next();
                Accessory accessory = new Accessory();
                accessory.setId(rowSet.getInt(1));
                accessory.setName(rowSet.getString(2));
                accessory.setPrice(rowSet.getDouble(3));
                inputRental.getAccessoryList()[i] = accessory;
            }
        }

        double total_price = Calculator.rentalPrice(inputRental);

        jdbcTemplate.update(sqlRental, start_date, end_date, pick_up_location, drop_off_location, pick_up_distance,
                drop_off_distance, cancel_date, total_price, customers_fk, motorhomes_fk, seasons_fk);

        for(Accessory accessory : inputRental.getAccessoryList()) {
            int accessoryId = accessory.getId();
                String sqlAccessory = "INSERT INTO NMR.rental_accessories " +
                        "VALUES (DEFAULT, ?, ?);";
                jdbcTemplate.update(sqlAccessory, accessoryId, lastAddedRentalId());
        }
    }

    // TODO: fix price
    public void update(int id, Rental input) {
        Rental rental = findById(id);
        // Customer, dates, motorhome, accessories
        String sql = "UPDATE NMR.rentals " +
                "SET start_date = ?, end_date = ?, pick_up_location = ?, drop_off_location = ?, pick_up_distance = ?, " +
                "drop_off_distance = ?, total_price = ?, " +
                "customers_fk = ?, motorhomes_fk = ?, seasons_fk = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, input.getStart_date(), input.getEnd_date(), input.getPick_up_location(),
                input.getDrop_off_location(), input.getPick_up_distance(), input.getDrop_off_distance(),
                Calculator.rentalPrice(rental), rental.getCustomers_fk(), rental.getMotorhomes_fk(),
                rental.getSeasons_fk(), id);

        for(Accessory accessory : input.getAccessoryList()) {
            int accessoryId = accessory.getId();
            if(!rental_accessoryExists(accessoryId, rental)) {
                String sqlAccessory = "INSERT INTO NMR.rental_accessories " +
                        "VALUES (DEFAULT, ?, ?);";
                jdbcTemplate.update(sqlAccessory, accessoryId, rental.getId());
            }
        }
    }

    /**
     * Delete a rental
     * @param id
     * @Author Frederik M.
     */
    public void delete(int id) {
        String delete = "DELETE FROM NMR.rentals WHERE id = ?";
        try {
            jdbcTemplate.update(delete, id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("An Error happened");
            e.printStackTrace();
        }
    }


    public boolean hasConstraint() {
        return false;
    }

    public boolean rental_accessoryExists(int accessoryId, Rental input) {
        String sqlExists = " SELECT " +
                " CASE WHEN EXISTS "+
                " (" +
                "SELECT * FROM NMR.rental_accessories " +
                "WHERE accessories_fk = ? AND rentals_fk = ?" +
                ")" +
                " THEN 'TRUE' " +
                " ELSE 'FALSE'" +
                " END;";
        SqlRowSet rowSetModel = jdbcTemplate.queryForRowSet(sqlExists, accessoryId, input.getId());
        rowSetModel.next();
        return Boolean.parseBoolean(rowSetModel.getString(1));
    }
}
