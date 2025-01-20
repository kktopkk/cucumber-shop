package ku.shop;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyStepdefs {

    private ProductCatalog catalog;
    private Order order;
    private Exception exception;

    @Given("the store is ready to service customers")
    public void the_store_is_ready_to_service_customers() {
        catalog = new ProductCatalog();
        order = new Order();
    }

    @Given("a product {string} with price {float} and stock of {int} exists")
    public void a_product_exists(String name, double price, int stock) {
        catalog.addProduct(name, price, stock);
    }

    @When("I buy {string} with quantity {int}")
    public void i_buy_with_quantity(String name, int quantity) {
        Product prod = catalog.getProduct(name);
        try {
            order.addItem(prod, quantity);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("total should be {float}")
    public void total_should_be(double total) {
        assertEquals(total, order.getTotal());
    }

    @Then("{string} in stock is not enough")
    public void in_stock_is_not_enough(String name) {
        if (exception instanceof IllegalArgumentException) {
            assertEquals("Not enough stock available for product: " + name, exception.getMessage());
        } else {
            throw new AssertionError("Expected an IllegalArgumentException but got: " + (exception == null ? "null" : exception.getClass().getSimpleName()));
        }
    }

}
