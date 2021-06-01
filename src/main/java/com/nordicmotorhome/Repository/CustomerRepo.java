package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo implements CRUDRepo<Customer>{

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SQL_USE = "USE NMR;";

    /**
     * @author Kasper N. Jensen
     * This method retreives all Customers from the MySQL database
     * @return List<Customer>
     */
    public List<Customer> fetchAll() {
        String sqlCustomer =
                "SELECT customers.id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                "dl_expire_date, street, floor, zip, city, countries.name as 'country', addresses_fk" +
                        " FROM NMR.customers " +
                "INNER JOIN addresses on addresses.id = addresses_fk " +
                "INNER JOIN zip_codes on zip_codes.id = zip_codes_fk " +
                "INNER JOIN countries on countries.id = countries_fk;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        jdbcTemplate.update(SQL_USE);
        return jdbcTemplate.query(sqlCustomer,rowMapper);
    }

    /**
     * @author Kasper N. Jensen, Jimmy Losang
     * @param customer Customer
     * This method adds a customer to our MySQL database.
     * Adding a customer to a database over multiple tables with dependencies and forign keys,
     * has to be done in a specific order as you can't add a customer before you have added its address furthermore
     * a zip and city has to be created before the address can be added, this results in 3 SQL statements that has to
     * executed one after another. As all of these steps need to happen you could make these as a transaction
     * in SQL so that if there are any data loss we can rollback in the DB.
     */
    public void addNew(Customer customer) {
        int zipInt = getProperZipCode(customer);

        //The saved zipcode id zipInt is then used as the forign key in the address SQL Query
        String sqlAddress = "INSERT INTO addresses (id, street, floor, zip_codes_fk) VALUES (DEFAULT,?,?," + zipInt + ");";

        //–||–
        jdbcTemplate.update(sqlAddress,customer.getStreet(),customer.getFloor());
        String sqlLastAddedAddress = "SELECT id FROM NMR.addresses ORDER BY id DESC limit 1;";
        SqlRowSet adr = jdbcTemplate.queryForRowSet(sqlLastAddedAddress);
        adr.next();
        int addressInt = adr.getInt("id");

        String sqlCustomer = "INSERT INTO customers (id ,first_name, last_name , mobile, phone, email, " +
                "drivers_license, dl_issue_date, dl_expire_date, addresses_fk) VALUES (DEFAULT,?,?,?,?,?,?,?,?," + addressInt + ");";

        // Lastly everything can be added into the customer using the address forgin key that then uses the zip forgin key
        jdbcTemplate.update(sqlCustomer,customer.getFirst_name(),customer.getLast_name(),customer.getMobile(),
                customer.getPhone(),customer.getEmail(),customer.getDrivers_license(),
                customer.getDl_issue_date(),customer.getDl_expire_date());
    }

    /**
     * @author Kasper N. Jensen
     * This method finds a specific customer using their id
     * @param id int
     * @return Customer
     */
    public Customer findById(int id){
        String sqlFindCustomerById = "SELECT *, co.name as country_name, co.id as country" +
                " FROM NMR.customers " +
                " inner join addresses on addresses_fk = addresses.id" +
                " inner join zip_codes on zip_codes_fk = zip_codes.id" +
                " inner join countries co on countries_fk = co.id" +
                " WHERE customers.id = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        jdbcTemplate.update("USE NMR;");
        Customer customer = jdbcTemplate.queryForObject(sqlFindCustomerById, rowMapper, id);
        return customer;
    }

    /**
     * @author Jimmy
     * Updates the customer entity in the database, but doesn't change the addresses_fk in the customers table, instead it
     * updates the entry in the addresses table.
     * @param inputCustomer Customer
     */
    public void update(Customer inputCustomer, int id) {
        Customer customer = findById(id);
        int zipInt = getProperZipCode(inputCustomer);

        String sqlAddress = "UPDATE NMR.addresses " +
                "SET street = ?, floor = ?, zip_codes_fk = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sqlAddress, inputCustomer.getStreet(), inputCustomer.getFloor(), zipInt, customer.getAddresses_fk());

        String sqlCustomer = "UPDATE NMR.customers " +
                "SET first_name = ?, last_name = ?, mobile = ?, phone = ?, email = ?, drivers_license = ?, " +
                "dl_issue_date = ?, dl_expire_date = ? " +
                "WHERE id = ?;";

        // Lastly everything can be added into the customer using the address foreign key that then uses the zip foreign key
        jdbcTemplate.update(sqlCustomer,inputCustomer.getFirst_name(),inputCustomer.getLast_name(),inputCustomer.getMobile(),
                inputCustomer.getPhone(),inputCustomer.getEmail(),inputCustomer.getDrivers_license(),
                inputCustomer.getDl_issue_date(),inputCustomer.getDl_expire_date(), id);
    }

    /**
     * @author Jimmy
     * Method checks if an existing zip code, in regard to a country, already exists and returns the
     * corresponding boolean.
     * @param zip int
     * @param country int
     * @return boolean
     */
    public boolean validateZipForCustomer(int zip, int country) {
        // This sql statement will check for two conditions.
        String sqlValidateZip = "SELECT\n" +
                "\tCASE WHEN EXISTS \n" +
                "    (\n" +
                "    SELECT * FROM NMR.zip_codes WHERE zip = ? AND countries_fk = ?\n" +
                "    )\n" +
                "    THEN 'TRUE'\n" +
                "    ELSE 'FALSE'\n" +
                "END";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlValidateZip, zip, country);
        rowSet.next();
        return Boolean.parseBoolean(rowSet.getString(1));
    }

    /**
     * Deletes a customer entity from the database with a given primary key
     * @param id the primary key in customer table in the Database
     * @author Mads
     */
    public void delete(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * @author Jimmy
     * Returns the primary key for a zip code, given the zip code and country.
     * @param zipcode int
     * @param country int
     * @return int
     */
    public int getZipCodePrimaryKey(int zipcode, int country) {
        String sql = "SELECT id FROM NMR.zip_codes " +
                "WHERE zip = ? AND countries_fk = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, zipcode, country);
        rowSet.next();
        return rowSet.getInt(1);
    }

    /**
     * @author Jimmy
     * Checks if a zip code already exists in the database and if so, returns that specific zip codes id (primary key),
     * otherwise it returns the last added zip code id.
     * @param customer Customer
     * @return int
     */
    public int getProperZipCode(Customer customer) {
        int countryForeignKey = Integer.parseInt(customer.getCountry());
        int zipParse = Integer.parseInt(customer.getZip());
        int zipInt;
        if (validateZipForCustomer(zipParse,countryForeignKey)) {
            // SELECT STATEMENT
            zipInt = getZipCodePrimaryKey(zipParse, countryForeignKey);
        } else {
            //SQL Statement that inserts later declared wildcard variables (?) into the DB
            String sqlZipCity = "INSERT INTO zip_codes (id, zip, city, countries_fk) VALUES (DEFAULT, ?, ?, ?)";

            //Using the jdbcTemplate method 'update', we take the SQL statement, and the needed
            // values from our Customer object using getters
            jdbcTemplate.update("USE NMR;");
            jdbcTemplate.update(sqlZipCity, customer.getZip(), customer.getCity(), customer.getCountry());

            //Finds the latest added zip entry in the DB and saves the id into a variable
            String sqlLastAddedZip = "SELECT id FROM NMR.zip_codes ORDER BY id DESC limit 1;";

            //queryForRowSet returns a SqlRowSet, much like a ResultSet, we use this to get access to the rows that has
            // the value of sqlLastAddedZip in the id column with zip.next().
            SqlRowSet zip = jdbcTemplate.queryForRowSet(sqlLastAddedZip);
            zip.next();
            zipInt = zip.getInt("id");
        }
        return zipInt;
    }

    /**
     * @author Jimmy
     * @param id int
     * @return boolean
     * The method checks if a customer entity's primary key is a foreign key in the rentals table.
     */
    public boolean hasConstraint(int id) {
        String sql = "SELECT " +
                "\tCASE WHEN EXISTS " +
                "    ( " +
                "    SELECT * FROM NMR.rentals WHERE customers_fk = ? " +
                "    ) " +
                "    THEN 'TRUE' " +
                "    ELSE 'FALSE' " +
                "END";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        rowSet.next();
        return Boolean.parseBoolean(rowSet.getString(1));
    }
}