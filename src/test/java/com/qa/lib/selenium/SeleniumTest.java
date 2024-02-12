package com.qa.lib.selenium;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class SeleniumTest {

	private RemoteWebDriver driver;

	@LocalServerPort
	private int port;

	@BeforeEach
	void init() {
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test
	@Order(1)
	void testCreateCustomer() {
		this.driver.get("http://localhost:" + this.port);
		String customer = "Barry";
		WebElement name = this.driver.findElement(
				By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(2) > form > div > input"));
		name.sendKeys(customer);

		WebElement register = this.driver.findElement(By.cssSelector("#button-addon2"));
		register.click();

		WebElement created = this.driver
				.findElement(By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(3) > h3"));
		Assertions.assertEquals(customer, created.getText());
	}

	@Test
	@Order(2)
	void testGetCustomer() {
		this.driver.get("http://localhost:" + this.port);

		WebElement created = this.driver
				.findElement(By.cssSelector("#root > main > div > section:nth-child(1) > div:nth-child(3) > h3"));
		Assertions.assertEquals("Barry", created.getText());
	}

}
