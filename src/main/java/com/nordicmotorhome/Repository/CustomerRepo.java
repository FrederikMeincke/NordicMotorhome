package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAllCustomers() {
        String sqlCustomer =
                "SELECT customers.id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                "dl_expire_date, street, floor, zip, city, name as 'country' FROM NMR.customers " +
                "INNER JOIN addresses on addresses.id = addresses_fk " +
                "INNER JOIN zip_codes on zip_codes.id = zip_codes_fk " +
                "INNER JOIN countries on countries.id = countries_fk;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(sqlCustomer,rowMapper);
    }

    /**
     * @author Jimmy Losang
     * A customer has an address field which needs a valid zip code, so we must first either insert a new zip code into
     * the database if one doesn't already exist, or use an existing zip code from the database. This zip code also needs
     * a valid country, so we must first check for countries.
     * @param customer
     */
    public void addCustomer(Customer customer) {
        String sqlCountry = "IF NOT EXISTS (SELECT * FROM countries " +
                "WHERE name = ?) " +
                "INSERT INTO countries " +
                "VALUES (DEFAULT, ?);";
        jdbcTemplate.update(sqlCountry, customer.getCountry(), customer.getCountry());
    }

    /*
    public void addCustomer(Customer customer) {
        String sqlZipCity = "INSERT INTO zipcity (zip_id, zip, city, country) VALUES (DEFAULT, ?, ?, 'Denmark')";

        jdbcTemplate.update(sqlZipCity, customer.getZip(), customer.getCity());
        String sqlLastAddedZip = "SELECT zip_id FROM kailua_cars.zipcity ORDER BY zip_id DESC limit 1;";
        SqlRowSet zip = jdbcTemplate.queryForRowSet(sqlLastAddedZip);
        zip.next();
        int zipInt = zip.getInt("zip_id");

        String sqlAddress = "INSERT INTO address (address_id, address_zip, address_street, address_number, address_floor) VALUES (DEFAULT," + zipInt + ",?,?,?);";

        jdbcTemplate.update(sqlAddress,customer.getAddress_street(),customer.getAddress_number(),customer.getAddress_floor());
        String sqlLastAddedAddress = "SELECT address_id FROM kailua_cars.address ORDER BY address_id DESC limit 1;";
        SqlRowSet adr = jdbcTemplate.queryForRowSet(sqlLastAddedAddress);
        adr.next();
        int addressInt = adr.getInt("address_id");

        String sqlCustomer = "INSERT INTO customers (customers_id, customers_address ,customers_name, customers_mobile, customers_phone, customers_email, " +
                "customers_drivers_license, customers_drivers_license_issuedate, customers_drivers_license_expiredate) " +
                "VALUES (DEFAULT," + addressInt + ",?,?,?,?,?,?,?)";

        jdbcTemplate.update(sqlCustomer,customer.getCustomers_name(),customer.getCustomers_mobile(),
                customer.getCustomers_phone(),customer.getCustomers_email(),customer.getCustomers_drivers_license(),
                customer.getCustomers_drivers_license_issuedate(),customer.getCustomers_drivers_license_expiredate());
    }
*/

    public void initializeDatabase() {
        System.out.println("SKY NET INITIALIZED");
        String initializeSQL = "" +
                "DROP DATABASE IF EXISTS NMR; " +
                " " +
                "CREATE DATABASE IF NOT EXISTS NMR; " +
                "CREATE TABLE IF NOT EXISTS NMR.countries ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY, " +
                "iso VARCHAR(2) NOT NULL, " +
                "name VARCHAR(80) NOT NULL, " +
                "phonecode INT(5) NOT NULL); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.zip_codes ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "zip INT NOT NULL, " +
                "city VARCHAR(45) NOT NULL, " +
                "countries_fk INT NOT NULL, " +
                "CONSTRAINT zip_codes_countries_fk " +
                "FOREIGN KEY (countries_fk) REFERENCES NMR.countries (id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.addresses ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "street VARCHAR(45) NOT NULL, " +
                "floor VARCHAR(45), " +
                "zip_codes_fk INT NOT NULL, " +
                "CONSTRAINT addresses_zip_codes_fk " +
                "FOREIGN KEY (zip_codes_fk) REFERENCES NMR.zip_codes(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.customers ( " +
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
                "FOREIGN KEY (addresses_fk) REFERENCES NMR.addresses(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.brands ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.models ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "fuel_type ENUM('BENZIN', 'DIESEL', 'BATTERY') NOT NULL, " +
                "width DOUBLE NOT NULL, " +
                "height DOUBLE NOT NULL, " +
                "weight DOUBLE NOT NULL, " +
                "brands_fk INT NOT NULL, " +
                "CONSTRAINT models_brands_fk " +
                "FOREIGN KEY (brands_fk) REFERENCES NMR.brands(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.motorhomes ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "type VARCHAR(45) NOT NULL, " +
                "bed_amount INT NOT NULL, " +
                "license_plate VARCHAR(45) NOT NULL, " +
                "register_date DATE NOT NULL, " +
                "price DOUBLE NOT NULL, " +
                "odometer DOUBLE NOT NULL, " +
                "ready_status TINYINT NOT NULL, " +
                "models_fk INT NOT NULL, " +
                "CONSTRAINT motorhomes_models_fk " +
                "FOREIGN KEY (models_fk) REFERENCES NMR.models(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.utilities ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "price DOUBLE NOT NULL); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.motorhome_utilities ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "motorhomes_fk INT NOT NULL, " +
                "utilities_fk INT NOT NULL, " +
                "CONSTRAINT mu_motorhomes_fk " +
                "FOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id), " +
                "CONSTRAINT mu_utilities_fk " +
                "FOREIGN KEY (utilities_fk) REFERENCES NMR.utilities(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.seasons ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "rate INT(100) NOT NULL); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.accessories ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "price DOUBLE NOT NULL); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.rentals ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "pick_up_location VARCHAR(45) NOT NULL, " +
                "drop_off_location VARCHAR(45) NOT NULL, " +
                "total_price DOUBLE NOT NULL, " +
                "customers_fk INT NOT NULL, " +
                "motorhomes_fk INT NOT NULL, " +
                "seasons_fk INT NOT NULL, " +
                "CONSTRAINT rentals_customers_fk " +
                "FOREIGN KEY (customers_fk) REFERENCES NMR.customers(id), " +
                "CONSTRAINT rentals_motorhomes_fk " +
                "FOREIGN KEY (motorhomes_fk) REFERENCES NMR.motorhomes(id), " +
                "CONSTRAINT rentals_seasons_fk " +
                "FOREIGN KEY (seasons_fk) REFERENCES NMR.seasons(id)); " +
                " " +
                "CREATE TABLE IF NOT EXISTS NMR.rental_accessories ( " +
                "id INT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT, " +
                "accessories_fk INT NOT NULL, " +
                "rentals_fk INT NOT NULL, " +
                "CONSTRAINT ra_accessories_fk " +
                "FOREIGN KEY (accessories_fk) REFERENCES NMR.accessories(id), " +
                "CONSTRAINT ra_rentals_fk " +
                "FOREIGN KEY (rentals_fk) REFERENCES NMR.rentals(id)); " +
                " " +
                "USE NMR; " +
                " " +
                "-- " +
                "-- Data dump for countries " +
                "-- " +
                " " +
                "INSERT INTO `countries` (`id`, `iso`, `name`, `phonecode`) VALUES " +
                "(1, 'AF', 'Afghanistan', 93), " +
                "(2, 'AL', 'Albania', 355), " +
                "(3, 'DZ', 'Algeria', 213), " +
                "(4, 'AS', 'American Samoa', 1684), " +
                "(5, 'AD', 'Andorra', 376), " +
                "(6, 'AO', 'Angola', 244), " +
                "(7, 'AI', 'Anguilla', 1264), " +
                "(8, 'AQ', 'Antarctica', 0), " +
                "(9, 'AG', 'Antigua and Barbuda', 1268), " +
                "(10, 'AR', 'Argentina', 54), " +
                "(11, 'AM', 'Armenia', 374), " +
                "(12, 'AW', 'Aruba', 297), " +
                "(13, 'AU', 'Australia', 61), " +
                "(14, 'AT', 'Austria', 43), " +
                "(15, 'AZ', 'Azerbaijan', 994), " +
                "(16, 'BS', 'Bahamas', 1242), " +
                "(17, 'BH', 'Bahrain', 973), " +
                "(18, 'BD', 'Bangladesh', 880), " +
                "(19, 'BB', 'Barbados', 1246), " +
                "(20, 'BY', 'Belarus', 375), " +
                "(21, 'BE', 'Belgium',  32), " +
                "(22, 'BZ', 'Belize', 501), " +
                "(23, 'BJ', 'Benin', 229), " +
                "(24, 'BM', 'Bermuda', 1441), " +
                "(25, 'BT', 'Bhutan', 975), " +
                "(26, 'BO', 'Bolivia', 591), " +
                "(27, 'BA', 'Bosnia and Herzegovina', 387), " +
                "(28, 'BW', 'Botswana', 267), " +
                "(29, 'BV', 'Bouvet Island', 0), " +
                "(30, 'BR', 'Brazil', 55), " +
                "(31, 'IO', 'British Indian Ocean Territory', 246), " +
                "(32, 'BN', 'Brunei Darussalam', 673), " +
                "(33, 'BG', 'Bulgaria', 359), " +
                "(34, 'BF', 'Burkina Faso', 226), " +
                "(35, 'BI', 'Burundi', 257), " +
                "(36, 'KH', 'Cambodia', 855), " +
                "(37, 'CM', 'Cameroon', 237), " +
                "(38, 'CA', 'Canada', 1), " +
                "(39, 'CV', 'Cape Verde', 238), " +
                "(40, 'KY', 'Cayman Islands', 1345), " +
                "(41, 'CF', 'Central African Republic', 236), " +
                "(42, 'TD', 'Chad', 235), " +
                "(43, 'CL', 'Chile', 56), " +
                "(44, 'CN', 'China', 86), " +
                "(45, 'CX', 'Christmas Island', 61), " +
                "(46, 'CC', 'Cocos (Keeling) Islands', 672), " +
                "(47, 'CO', 'Colombia', 57), " +
                "(48, 'KM', 'Comoros', 269), " +
                "(49, 'CG', 'Congo', 242), " +
                "(50, 'CD', 'Congo, the Democratic Republic of the', 242), " +
                "(51, 'CK', 'Cook Islands', 682), " +
                "(52, 'CR', 'Costa Rica', 506), " +
                "(53, 'CI', 'Cote D''Ivoire', 225), " +
                "(54, 'HR', 'Croatia', 385), " +
                "(55, 'CU', 'Cuba', 53), " +
                "(56, 'CY', 'Cyprus', 357), " +
                "(57, 'CZ', 'Czech Republic', 420), " +
                "(58, 'DK', 'Denmark', 45), " +
                "(59, 'DJ', 'Djibouti', 253), " +
                "(60, 'DM', 'Dominica', 1767), " +
                "(61, 'DO', 'Dominican Republic', 1809), " +
                "(62, 'EC', 'Ecuador', 593), " +
                "(63, 'EG', 'Egypt', 20), " +
                "(64, 'SV', 'El Salvador', 503), " +
                "(65, 'GQ', 'Equatorial Guinea', 240), " +
                "(66, 'ER', 'Eritrea', 291), " +
                "(67, 'EE', 'Estonia', 372), " +
                "(68, 'ET', 'Ethiopia', 251), " +
                "(69, 'FK', 'Falkland Islands (Malvinas)', 500), " +
                "(70, 'FO', 'Faroe Islands', 298), " +
                "(71, 'FJ', 'Fiji', 679), " +
                "(72, 'FI', 'Finland', 358), " +
                "(73, 'FR', 'France', 33), " +
                "(74, 'GF', 'French Guiana', 594), " +
                "(75, 'PF', 'French Polynesia', 689), " +
                "(76, 'TF', 'French Southern Territories', 0), " +
                "(77, 'GA', 'Gabon', 241), " +
                "(78, 'GM', 'Gambia', 220), " +
                "(79, 'GE', 'Georgia', 995), " +
                "(80, 'DE', 'Germany', 49), " +
                "(81, 'GH', 'Ghana', 233), " +
                "(82, 'GI', 'Gibraltar', 350), " +
                "(83, 'GR', 'Greece', 30), " +
                "(84, 'GL', 'Greenland', 299), " +
                "(85, 'GD', 'Grenada', 1473), " +
                "(86, 'GP', 'Guadeloupe', 590), " +
                "(87, 'GU', 'Guam', 1671), " +
                "(88, 'GT', 'Guatemala', 502), " +
                "(89, 'GN', 'Guinea', 224), " +
                "(90, 'GW', 'Guinea-Bissau', 245), " +
                "(91, 'GY', 'Guyana', 592), " +
                "(92, 'HT', 'Haiti', 509), " +
                "(93, 'HM', 'Heard Island and Mcdonald Islands', 0), " +
                "(94, 'VA', 'Holy See (Vatican City State)', 39), " +
                "(95, 'HN', 'Honduras', 504), " +
                "(96, 'HK', 'Hong Kong', 852), " +
                "(97, 'HU', 'Hungary', 36), " +
                "(98, 'IS', 'Iceland', 354), " +
                "(99, 'IN', 'India', 91), " +
                "(100, 'ID', 'Indonesia', 62), " +
                "(101, 'IR', 'Iran, Islamic Republic of', 98), " +
                "(102, 'IQ', 'Iraq', 964), " +
                "(103, 'IE', 'Ireland', 353), " +
                "(104, 'IL', 'Israel', 972), " +
                "(105, 'IT', 'Italy', 39), " +
                "(106, 'JM', 'Jamaica', 1876), " +
                "(107, 'JP', 'Japan', 81), " +
                "(108, 'JO', 'Jordan', 962), " +
                "(109, 'KZ', 'Kazakhstan', 7), " +
                "(110, 'KE', 'Kenya', 254), " +
                "(111, 'KI', 'Kiribati', 686), " +
                "(112, 'KP', 'Korea, Democratic People''s Republic of', 850), " +
                "(113, 'KR', 'Korea, Republic of', 82), " +
                "(114, 'KW', 'Kuwait', 965), " +
                "(115, 'KG', 'Kyrgyzstan', 996), " +
                "(116, 'LA', 'Lao People''s Democratic Republic', 856), " +
                "(117, 'LV', 'Latvia', 371), " +
                "(118, 'LB', 'Lebanon', 961), " +
                "(119, 'LS', 'Lesotho', 266), " +
                "(120, 'LR', 'Liberia', 231), " +
                "(121, 'LY', 'Libyan Arab Jamahiriya', 218), " +
                "(122, 'LI', 'Liechtenstein', 423), " +
                "(123, 'LT', 'Lithuania', 370), " +
                "(124, 'LU', 'Luxembourg', 352), " +
                "(125, 'MO', 'Macao', 853), " +
                "(126, 'MK', 'Macedonia, the Former Yugoslav Republic of', 389), " +
                "(127, 'MG', 'Madagascar', 261), " +
                "(128, 'MW', 'Malawi', 265), " +
                "(129, 'MY', 'Malaysia', 60), " +
                "(130, 'MV', 'Maldives', 960), " +
                "(131, 'ML', 'Mali', 223), " +
                "(132, 'MT', 'Malta', 356), " +
                "(133, 'MH', 'Marshall Islands', 692), " +
                "(134, 'MQ', 'Martinique', 596), " +
                "(135, 'MR', 'Mauritania', 222), " +
                "(136, 'MU', 'Mauritius', 230), " +
                "(137, 'YT', 'Mayotte', 269), " +
                "(138, 'MX', 'Mexico', 52), " +
                "(139, 'FM', 'Micronesia, Federated States of', 691), " +
                "(140, 'MD', 'Moldova, Republic of', 373), " +
                "(141, 'MC', 'Monaco', 377), " +
                "(142, 'MN', 'Mongolia', 976), " +
                "(143, 'MS', 'Montserrat', 1664), " +
                "(144, 'MA', 'Morocco', 212), " +
                "(145, 'MZ', 'Mozambique', 258), " +
                "(146, 'MM', 'Myanmar', 95), " +
                "(147, 'NA', 'Namibia', 264), " +
                "(148, 'NR', 'Nauru', 674), " +
                "(149, 'NP', 'Nepal', 977), " +
                "(150, 'NL', 'Netherlands', 31), " +
                "(151, 'AN', 'Netherlands Antilles', 599), " +
                "(152, 'NC', 'New Caledonia', 687), " +
                "(153, 'NZ', 'New Zealand', 64), " +
                "(154, 'NI', 'Nicaragua', 505), " +
                "(155, 'NE', 'Niger', 227), " +
                "(156, 'NG', 'Nigeria', 234), " +
                "(157, 'NU', 'Niue', 683), " +
                "(158, 'NF', 'Norfolk Island', 672), " +
                "(159, 'MP', 'Northern Mariana Islands', 1670), " +
                "(160, 'NO', 'Norway', 47), " +
                "(161, 'OM', 'Oman', 968), " +
                "(162, 'PK', 'Pakistan', 92), " +
                "(163, 'PW', 'Palau', 680), " +
                "(164, 'PS', 'Palestinian Territory, Occupied', 970), " +
                "(165, 'PA', 'Panama', 507), " +
                "(166, 'PG', 'Papua New Guinea', 675), " +
                "(167, 'PY', 'Paraguay', 595), " +
                "(168, 'PE', 'Peru', 51), " +
                "(169, 'PH', 'Philippines', 63), " +
                "(170, 'PN', 'Pitcairn', 0), " +
                "(171, 'PL', 'Poland', 48), " +
                "(172, 'PT', 'Portugal', 351), " +
                "(173, 'PR', 'Puerto Rico', 1787), " +
                "(174, 'QA', 'Qatar', 974), " +
                "(175, 'RE', 'Reunion', 262), " +
                "(176, 'RO', 'Romania', 40), " +
                "(177, 'RU', 'Russian Federation', 70), " +
                "(178, 'RW', 'Rwanda', 250), " +
                "(179, 'SH', 'Saint Helena', 290), " +
                "(180, 'KN', 'Saint Kitts and Nevis', 1869), " +
                "(181, 'LC', 'Saint Lucia', 1758), " +
                "(182, 'PM', 'Saint Pierre and Miquelon', 508), " +
                "(183, 'VC', 'Saint Vincent and the Grenadines', 1784), " +
                "(184, 'WS', 'Samoa', 684), " +
                "(185, 'SM', 'San Marino', 378), " +
                "(186, 'ST', 'Sao Tome and Principe', 239), " +
                "(187, 'SA', 'Saudi Arabia', 966), " +
                "(188, 'SN', 'Senegal', 221), " +
                "(189, 'CS', 'Serbia and Montenegro', 381), " +
                "(190, 'SC', 'Seychelles', 248), " +
                "(191, 'SL', 'Sierra Leone', 232), " +
                "(192, 'SG', 'Singapore', 65), " +
                "(193, 'SK', 'Slovakia', 421), " +
                "(194, 'SI', 'Slovenia', 386), " +
                "(195, 'SB', 'Solomon Islands', 677), " +
                "(196, 'SO', 'Somalia', 252), " +
                "(197, 'ZA', 'South Africa', 27), " +
                "(198, 'GS', 'South Georgia and the South Sandwich Islands', 0), " +
                "(199, 'ES', 'Spain', 34), " +
                "(200, 'LK', 'Sri Lanka', 94), " +
                "(201, 'SD', 'Sudan', 249), " +
                "(202, 'SR', 'Suriname', 597), " +
                "(203, 'SJ', 'Svalbard and Jan Mayen', 47), " +
                "(204, 'SZ', 'Swaziland', 268), " +
                "(205, 'SE', 'Sweden', 46), " +
                "(206, 'CH', 'Switzerland', 41), " +
                "(207, 'SY', 'Syrian Arab Republic', 963), " +
                "(208, 'TW', 'Taiwan, Province of China', 886), " +
                "(209, 'TJ', 'Tajikistan', 992), " +
                "(210, 'TZ', 'Tanzania, United Republic of', 255), " +
                "(211, 'TH', 'Thailand', 66), " +
                "(212, 'TL', 'Timor-Leste', 670), " +
                "(213, 'TG', 'Togo', 228), " +
                "(214, 'TK', 'Tokelau', 690), " +
                "(215, 'TO', 'Tonga', 676), " +
                "(216, 'TT', 'Trinidad and Tobago', 1868), " +
                "(217, 'TN', 'Tunisia', 216), " +
                "(218, 'TR', 'Turkey', 90), " +
                "(219, 'TM', 'Turkmenistan', 7370), " +
                "(220, 'TC', 'Turks and Caicos Islands', 1649), " +
                "(221, 'TV', 'Tuvalu', 688), " +
                "(222, 'UG', 'Uganda', 256), " +
                "(223, 'UA', 'Ukraine', 380), " +
                "(224, 'AE', 'United Arab Emirates', 971), " +
                "(225, 'GB', 'United Kingdom', 44), " +
                "(226, 'US', 'United States', 1), " +
                "(227, 'UM', 'United States Minor Outlying Islands', 1), " +
                "(228, 'UY', 'Uruguay', 598), " +
                "(229, 'UZ', 'Uzbekistan', 998), " +
                "(230, 'VU', 'Vanuatu', 678), " +
                "(231, 'VE', 'Venezuela', 58), " +
                "(232, 'VN', 'Vietnam', 84), " +
                "(233, 'VG', 'Virgin Islands, British', 1284), " +
                "(234, 'VI', 'Virgin Islands, U.s.', 1340), " +
                "(235, 'WF', 'Wallis and Futuna', 681), " +
                "(236, 'EH', 'Western Sahara', 212), " +
                "(237, 'YE', 'Yemen', 967), " +
                "(238, 'ZM', 'Zambia', 260), " +
                "(239, 'ZW', 'Zimbabwe', 263); " +
                " " +
                "-- " +
                "-- Data dump for zip-codes " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for addresses " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for customers " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for brands " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for models " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for motorhomes " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for utilities " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for seasons " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for accessories " +
                "-- " +
                " " +
                "-- " +
                "-- Data dump for rentals " +
                "-- ";

        jdbcTemplate.update(initializeSQL);
    }

}
