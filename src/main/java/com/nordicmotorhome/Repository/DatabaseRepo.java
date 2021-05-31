package com.nordicmotorhome.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Repository
public class DatabaseRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @author Jimmy
     * This method simply calls other methods responsible for initialzing the database scripts
     */
    public void initializeDatabase() {
        createDatabase();
        createTables();
        selectCase();
        System.out.println("NORDIC MOTORHOME DB INITIALIZED");
    }

    /**
     * This method creates the schema NMR if it doesn't already exists
     * @author Jimmy
     */
    public void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS NMR;";
        jdbcTemplate.update(sql);
    }

    /**
     * This method creates all the tables of our db schema.
     * @author Jimmy
     */
    public void createTables() {
        String sqlCountries = "CREATE TABLE IF NOT EXISTS NMR.countries (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY, " +
                "iso VARCHAR(2) NOT NULL, " +
                "name VARCHAR(80) NOT NULL, " +
                "phonecode INT(5) NOT NULL);";

        String sqlZipCodes = "CREATE TABLE IF NOT EXISTS NMR.zip_codes ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "zip INT NOT NULL, " +
                "city VARCHAR(45) NOT NULL, " +
                "countries_fk INT NOT NULL, " +
                "CONSTRAINT zip_codes_countries_fk " +
                "\tFOREIGN KEY (countries_fk) REFERENCES NMR.countries (id));";

        String sqlAddresses = "CREATE TABLE IF NOT EXISTS NMR.addresses ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "street VARCHAR(45) NOT NULL, " +
                "floor VARCHAR(45), " +
                "zip_codes_fk INT NOT NULL, " +
                "CONSTRAINT addresses_zip_codes_fk " +
                "\tFOREIGN KEY (zip_codes_fk) REFERENCES NMR.zip_codes(id));";

        String sqlCustomers = "CREATE TABLE IF NOT EXISTS NMR.customers (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "first_name VARCHAR(45) NOT NULL, " +
                "last_name VARCHAR(45) NOT NULL, " +
                "mobile VARCHAR(45) NOT NULL, " +
                "phone VARCHAR(45), " +
                "email VARCHAR(45) NOT NULL, " +
                "drivers_license VARCHAR(45) NOT NULL, " +
                "dl_issue_date DATE NOT NULL, " +
                "dl_expire_date DATE NOT NULL, " +
                "addresses_fk INT NOT NULL, " +
                "CONSTRAINT customers_addresses_fk " +
                "\tFOREIGN KEY (addresses_fk) REFERENCES NMR.addresses(id));";

        String sqlBrands = "CREATE TABLE IF NOT EXISTS NMR.brands (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL);";

        String sqlModels = "CREATE TABLE IF NOT EXISTS NMR.models ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "fuel_type ENUM('PETROL', 'DIESEL', 'EV') NOT NULL, " +
                "width DOUBLE NOT NULL, " +
                "height DOUBLE NOT NULL, " +
                "weight DOUBLE NOT NULL, " +
                "brands_fk INT NOT NULL, " +
                "CONSTRAINT models_brands_fk " +
                "\tFOREIGN KEY (brands_fk) REFERENCES NMR.brands(id));";

        String sqlMotorhomes = "CREATE TABLE IF NOT EXISTS NMR.motorhomes ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "type ENUM('Class A', 'Class B' , 'Class C', 'Fifth Wheel Camper', 'Toy Hauler', 'Travel Trailer', 'Teardrop Camper', 'Pop-Up Camper') NOT NULL,\n" +
                "bed_amount INT NOT NULL, " +
                "license_plate VARCHAR(45) NOT NULL, " +
                "register_date DATE NOT NULL, " +
                "price DOUBLE NOT NULL, " +
                "odometer DOUBLE NOT NULL, " +
                "models_fk INT NOT NULL, " +
                "ready_status BOOL NOT NULL DEFAULT FALSE , " +
                "CONSTRAINT motorhomes_models_fk " +
                "\tFOREIGN KEY (models_fk) REFERENCES NMR.models(id));";

        String sqlUtilities = "CREATE TABLE IF NOT EXISTS NMR.utilities (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL);";

        String sqlMU = "CREATE TABLE IF NOT EXISTS NMR.motorhome_utilities (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "motorhomes_fk INT NOT NULL, " +
                "utilities_fk INT NOT NULL, " +
                "CONSTRAINT mu_motorhomes_fk " +
                "\tFOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id), " +
                "CONSTRAINT mu_utilities_fk " +
                "\tFOREIGN KEY (utilities_fk) REFERENCES NMR.utilities(id));";

        String sqlSeasons = "CREATE TABLE IF NOT EXISTS NMR.seasons ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "rate INT(100) NOT NULL, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL);";

        String sqlAccessories = "CREATE TABLE IF NOT EXISTS NMR.accessories ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "price DOUBLE NOT NULL);";

        String sqlRentals = "CREATE TABLE IF NOT EXISTS NMR.rentals (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "pick_up_location VARCHAR(45) NOT NULL, " +
                "drop_off_location VARCHAR(45) NOT NULL, " +
                "pick_up_distance INT NOT NULL, " +
                "drop_off_distance INT NOT NULL, " +
                "cancel_date DATE, " +
                "distance_driven INT, " +
                "half_fuel BOOL DEFAULT FALSE, " +
                "total_price DOUBLE NOT NULL, " +
                "customers_fk INT NOT NULL, " +
                "motorhomes_fk INT NOT NULL, " +
                "seasons_fk INT NOT NULL, " +
                "CONSTRAINT rentals_customers_fk " +
                "\tFOREIGN KEY (customers_fk) REFERENCES NMR.customers(id), " +
                "CONSTRAINT rentals_motorhomes_fk " +
                "\tFOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id), " +
                "CONSTRAINT rentals_seasons_fk " +
                "\tFOREIGN KEY (seasons_fk) REFERENCES NMR.seasons(id));";

        String sqlRA = "CREATE TABLE IF NOT EXISTS NMR.rental_accessories (" +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "accessories_fk INT NOT NULL, " +
                "rentals_fk INT NOT NULL, " +
                "CONSTRAINT ra_accessories_fk " +
                "\tFOREIGN KEY (accessories_fk) REFERENCES NMR.accessories(id), " +
                "CONSTRAINT ra_rentals_fk " +
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

    /**
     * @author Jimmy Losang, Mads Westh
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

    /**
     * @author Jimmy Losang, Mads Westh
     */
    public void selectCase() {
        String sql = "SELECT\n" +
                "\tCASE WHEN EXISTS \n" +
                "    (\n" +
                "    SELECT * FROM NMR.zip_codes WHERE zip = 9700 AND countries_fk = 58\n" +
                "    )\n" +
                "    THEN 'TRUE'\n" +
                "    ELSE 'FALSE'\n" +
                "END";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        rowSet.next();
        String s = rowSet.getString(1);
        boolean f = Boolean.parseBoolean(s);
    }
}