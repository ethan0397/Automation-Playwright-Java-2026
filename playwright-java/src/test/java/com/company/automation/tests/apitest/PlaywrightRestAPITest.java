package com.company.automation.tests.apitest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.HashMap;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import netscape.javascript.JSObject;

public class PlaywrightRestAPITest {
    private static Playwright playwright;
    protected static BrowserContext browserContext;
    protected static Browser browser;

    static Page page;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class MakingAPICalls {
        public record Product(String name, Double price) {

        }

        private static APIRequestContext requestContext;

        @BeforeAll
        public static void setupRequestContext() {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true));
            requestContext = playwright.request().newContext(
                    new APIRequest.NewContextOptions()
                            .setBaseURL("https://api.practicesoftwaretesting.com/")
                            .setExtraHTTPHeaders(new HashMap<>() {
                                {
                                    put("Accept", "application/json");
                                }
                            }));
        }

        @BeforeEach
        void setUpBrowser() {
            browserContext = browser.newContext();
            page = browserContext.newPage();
            page.navigate("https://practicesoftwaretesting.com");
            page.getByPlaceholder("Search").waitFor();
        }

        @DisplayName("Check presence of know products")
        @ParameterizedTest(name = "Checking product {0}")
        @MethodSource("products")

        void checkKnownProduct(Product product) {
            page.fill("[placeholder='Search']", product.name);
            // page.getByPlaceholder("Search").fill(product.name);
            page.click("button:has-text('Search')");

            Locator productCard = page.locator(".card")
                    .filter(new Locator.FilterOptions()
                            .setHasText(product.name));

            assertThat(productCard)
                    .containsText(String.valueOf(product.price));
        }

        @AfterAll
        void tearDown() {
            requestContext.dispose();
            browser.close();
            playwright.close();
        }

        static Stream<Product> products() {
            APIResponse response = requestContext.get("products?page=2");
            Assertions.assertThat(response.status()).isEqualTo(200);
            JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");

            return data.asList().stream()
                    .map(jsonElment -> {
                        JsonObject productJson = jsonElment.getAsJsonObject();
                        return new Product(
                                productJson.get("name").getAsString(),
                                productJson.get("price").getAsDouble()

                    );
                    });

        }
    }

}
