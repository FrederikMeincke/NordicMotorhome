package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Repository
public class RentalRepo implements CRUDRepo<Rental>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SQL_USE = "USE NMR;";

    /**
     * @auuthor Mads
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

            //motorhome
            String sqlMotorhome = "SELECT motorhomes.id, b.name as brand, m.name as model, price, m.fuel_type, type," +
                    " bed_amount, m.weight, m.width, m.height," +
                    " license_plate, register_date, odometer, ready_status, models_fk FROM motorhomes" +
                    " INNER JOIN models m on motorhomes.models_fk = m.id" +
                    " INNER JOIN brands b on m.brands_fk = b.id " +
                    " WHERE motorhomes.id = ?" +
                    " ORDER BY motorhomes.id;";
            RowMapper<Motorhome> motorhomeRowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
            List<Motorhome> motorhomeList = jdbcTemplate.query(sqlMotorhome, motorhomeRowMapper, rental.getMotorhomes_fk());
            rental.setMotorhome(motorhomeList.get(0));

            //season
            String sqlSeason = "SELECT * FROM rentals " +
                    "WHERE seasons_fk = ? ";
            RowMapper<Season> seasonRowMapper = new BeanPropertyRowMapper<>(Season.class);
            List<Season> seasonList = jdbcTemplate.query(sqlSeason, seasonRowMapper, rental.getSeasons_fk());
            rental.setSeason(seasonList.get(0));
        }

        return rentalList;
    }

    public void addNew(Rental rental) {
        rental.getCustomer().getFirst_name();

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
        rental.setAccessoryList(input.getAccessoryList());
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
}
