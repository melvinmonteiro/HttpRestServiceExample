package com.example.restservice.controller;

import com.example.restservice.config.IntegrationTestWebAppContext;
import static org.hamcrest.Matchers.is;

import com.example.restservice.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by melvin on 10/13/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationTestWebAppContext.class})
@WebAppConfiguration
public class InvoiceRestControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * test the formatting of json
     * @throws Exception
     */
    @Test
    public void saveInvoice() throws Exception {

        String json = "{\n" +
                "  \"invoice_number\": \"ABC12345\",\n" +
                "  \"po_number\": \"X1B23C4D5E\",\n" +
                "  \"amount_cents\": \"100000\",\n" +
                "  \"due_date\": \"2017-03-15\"\n" +
                "}";

       mockMvc.perform(post("/invoices")
                .contentType(APPLICATION_JSON_UTF8)
                .content(json)
        ).andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.invoice_number", is("ABC12345")))
                .andExpect(jsonPath("$.po_number", is("X1B23C4D5E")))
                .andExpect(jsonPath("$.amount_cents", is(100000)))
                .andExpect(jsonPath("$.created_at",  matchesPattern("[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}Z")));
    }

}
