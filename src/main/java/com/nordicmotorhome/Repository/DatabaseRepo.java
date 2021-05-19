package com.nordicmotorhome.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

@Repository
public class DatabaseRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void initializeDatabase() {
        System.out.println("SKY NET INITIALIZED");
        createDatabase();
        createTables();
    }

    public void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS NMR;";
        jdbcTemplate.update(sql);
    }

    public void createTables() {
        String sqlCountries = "CREATE TABLE IF NOT EXISTS NMR.countries (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY,\n" +
                "iso VARCHAR(2) NOT NULL,\n" +
                "name VARCHAR(80) NOT NULL,\n" +
                "phonecode INT(5) NOT NULL);";

        String sqlZipCodes = "CREATE TABLE IF NOT EXISTS NMR.zip_codes (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "zip INT NOT NULL,\n" +
                "city VARCHAR(45) NOT NULL,\n" +
                "countries_fk INT NOT NULL,\n" +
                "CONSTRAINT zip_codes_countries_fk\n" +
                "\tFOREIGN KEY (countries_fk) REFERENCES NMR.countries (id));";

        String sqlAddresses = "CREATE TABLE IF NOT EXISTS NMR.addresses (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "street VARCHAR(45) NOT NULL,\n" +
                "floor VARCHAR(45),\n" +
                "zip_codes_fk INT NOT NULL,\n" +
                "CONSTRAINT addresses_zip_codes_fk\n" +
                "\tFOREIGN KEY (zip_codes_fk) REFERENCES NMR.zip_codes(id));";

        String sqlCustomers = "CREATE TABLE IF NOT EXISTS NMR.customers (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "first_name VARCHAR(45) NOT NULL,\n" +
                "last_name VARCHAR(45) NOT NULL,\n" +
                "mobile VARCHAR(45) NOT NULL,\n" +
                "phone VARCHAR(45),\n" +
                "email VARCHAR(45) NOT NULL,\n" +
                "drivers_license VARCHAR(45) NOT NULL,\n" +
                "dl_issue_date DATE NOT NULL,\n" +
                "dl_expire_date DATE NOT NULL,\n" +
                "addresses_fk INT NOT NULL,\n" +
                "CONSTRAINT customers_addresses_fk\n" +
                "\tFOREIGN KEY (addresses_fk) REFERENCES NMR.addresses(id));";

        String sqlBrands = "CREATE TABLE IF NOT EXISTS NMR.brands (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(45) NOT NULL);";

        String sqlModels = "CREATE TABLE IF NOT EXISTS NMR.models (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(45) NOT NULL,\n" +
                "fuel_type ENUM('BENZIN', 'DIESEL', 'BATTERY') NOT NULL,\n" +
                "width DOUBLE NOT NULL,\n" +
                "height DOUBLE NOT NULL,\n" +
                "weight DOUBLE NOT NULL,\n" +
                "brands_fk INT NOT NULL,\n" +
                "CONSTRAINT models_brands_fk\n" +
                "\tFOREIGN KEY (brands_fk) REFERENCES NMR.brands(id));";

        String sqlMotorhomes = "CREATE TABLE IF NOT EXISTS NMR.motorhomes (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "type ENUM('Class A', 'Class B' , 'Class C', 'Fifth Wheel Camper', 'Toy Hauler', 'Travel Trailer', 'Teardrop Camper', 'Pop-Up Camper') NOT NULL,\n" +
                "bed_amount INT NOT NULL,\n" +
                "license_plate VARCHAR(45) NOT NULL,\n" +
                "register_date DATE NOT NULL,\n" +
                "price DOUBLE NOT NULL,\n" +
                "odometer DOUBLE NOT NULL,\n" +
                "ready_status TINYINT NOT NULL,\n" +
                "models_fk INT NOT NULL,\n" +
                "CONSTRAINT motorhomes_models_fk\n" +
                "\tFOREIGN KEY (models_fk) REFERENCES NMR.models(id));";

        String sqlUtilities = "CREATE TABLE IF NOT EXISTS NMR.utilities (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(45) NOT NULL,\n" +
                "price DOUBLE NOT NULL);";

        String sqlMU = "CREATE TABLE IF NOT EXISTS NMR.motorhome_utilities (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "motorhomes_fk INT NOT NULL,\n" +
                "utilities_fk INT NOT NULL,\n" +
                "CONSTRAINT mu_motorhomes_fk\n" +
                "\tFOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id),\n" +
                "CONSTRAINT mu_utilities_fk\n" +
                "\tFOREIGN KEY (utilities_fk) REFERENCES NMR.utilities(id));";

        String sqlSeasons = "CREATE TABLE IF NOT EXISTS NMR.seasons (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(45) NOT NULL,\n" +
                "rate INT(100) NOT NULL);";

        String sqlAccessories = "CREATE TABLE IF NOT EXISTS NMR.accessories (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(45) NOT NULL,\n" +
                "price DOUBLE NOT NULL);";

        String sqlRentals = "CREATE TABLE IF NOT EXISTS NMR.rentals (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "start_date DATE NOT NULL,\n" +
                "end_date DATE NOT NULL,\n" +
                "pick_up_location VARCHAR(45) NOT NULL,\n" +
                "drop_off_location VARCHAR(45) NOT NULL,\n" +
                "total_price DOUBLE NOT NULL,\n" +
                "customers_fk INT NOT NULL,\n" +
                "motorhomes_fk INT NOT NULL,\n" +
                "seasons_fk INT NOT NULL,\n" +
                "CONSTRAINT rentals_customers_fk\n" +
                "\tFOREIGN KEY (customers_fk) REFERENCES NMR.customers(id),\n" +
                "CONSTRAINT rentals_motorhomes_fk\n" +
                "\tFOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id),\n" +
                "CONSTRAINT rentals_seasons_fk\n" +
                "\tFOREIGN KEY (seasons_fk) REFERENCES NMR.seasons(id));";

        String sqlRA = "CREATE TABLE IF NOT EXISTS NMR.rental_accessories (\n" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,\n" +
                "accessories_fk INT NOT NULL,\n" +
                "rentals_fk INT NOT NULL,\n" +
                "CONSTRAINT ra_accessories_fk\n" +
                "\tFOREIGN KEY (accessories_fk) REFERENCES NMR.accessories(id),\n" +
                "CONSTRAINT ra_rentals_fk\n" +
                "\tFOREIGN KEY (rentals_fk) REFERENCES NMR.rentals(id));";

        jdbcTemplate.update(sqlCountries);
        jdbcTemplate.update(sqlZipCodes);
        jdbcTemplate.update(sqlAddresses);
        jdbcTemplate.update(sqlCustomers);
        jdbcTemplate.update(sqlBrands);
        jdbcTemplate.update(sqlModels);
        jdbcTemplate.update(sqlMotorhomes);
        jdbcTemplate.update(sqlUtilities);
        jdbcTemplate.update(sqlMU);
        jdbcTemplate.update(sqlSeasons);
        jdbcTemplate.update(sqlAccessories);
        jdbcTemplate.update(sqlRentals);
        jdbcTemplate.update(sqlRA);
    }

    // @author jimmy mads

    /**
     * @Author Jimmy Losang, Mads Westh
     * Checks if the countries table is empty and if so it will read a textfile that contains
     * prepared data and inserts the data into the database. This method is used to quickly
     * store data in the database in order to check the programs functionality for demos.
     */
    public void dataDump() {
        String sqlCheck = "SELECT COUNT('id') FROM NMR.countries;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCheck);
        rowSet.next();
        boolean empty = rowSet.getInt(1) == 0;

        if(empty) {
            String sqlDump = "";
            try {
                Scanner scanner = new Scanner(new File("dataDump.txt"));
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    sqlDump += line + "\n";
                    if(line.contains(";")) {
                        jdbcTemplate.update(sqlDump);
                        sqlDump = "";
                    }
                }
            } catch (IOException ioException) {
                System.out.println("Data file not found or unreadable.");
            }
        }
    }
}
