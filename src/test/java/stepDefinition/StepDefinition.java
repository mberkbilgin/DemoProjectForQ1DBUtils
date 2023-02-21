package stepDefinition;

import io.cucumber.java.en.Given;
import org.junit.Assert;
import utils.DBUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StepDefinition {

    @Given("the customer id {string}, the name {string}, the city {string}, the country {string} and the sum {string} should match the DB record")
    public void the_customer_id_the_name_the_city_the_country_and_the_sum_should_match_the_DB_record(String customer_id, String name, String city, String country, String sum) throws SQLException {
        String query = "SELECT customer.customer_id,CONCAT(first_name,' ',last_name)AS name,country,city,SUM(amount) AS sum FROM customer \n" +
                "INNER JOIN address ON address.address_id = customer.address_id \n" +
                "INNER JOIN city ON city.city_id = address.city_id\n" +
                "INNER JOIN country ON country.country_id = city.country_id\n" +
                "INNER JOIN payment ON payment.customer_id = customer.customer_id\n" +
                "GROUP BY customer.customer_id,name,country,city\n" +
                "ORDER BY sum DESC\n" +
                "LIMIT 1";
        List<Map<String, Object>> resultMap = DBUtils.getQueryResultMap(query);
        String actualCustomer_id = resultMap.get(0).get("customer_id").toString();
        Assert.assertEquals(customer_id, actualCustomer_id);
        String actualName = resultMap.get(0).get("name").toString();
        Assert.assertEquals(name, actualName);
        String actualCity = resultMap.get(0).get("city").toString();
        Assert.assertEquals(city, actualCity);
        String actualCountry = resultMap.get(0).get("country").toString();
        Assert.assertEquals(country, actualCountry);
        String actualSum = resultMap.get(0).get("sum").toString();
        Assert.assertEquals(sum, actualSum);
    }
}
