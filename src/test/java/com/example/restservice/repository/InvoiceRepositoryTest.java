package com.example.restservice.repository;

import com.example.restservice.config.IntegrationTestWebAppContext;
import com.example.restservice.model.Invoice;
import com.example.restservice.vaildation.RestExampleException;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by melvin on 10/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {IntegrationTestWebAppContext.class})
@WebAppConfiguration
public class InvoiceRepositoryTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;


    /**
     *
     * @throws RestExampleException
     */
    @Transactional
    @Test
    public void saveInvoiceTest() throws RestExampleException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("12345");
        invoice.setPoNumber("12313");
        invoice.setAmountCents(10000L);
        invoice.setDueDate(new LocalDate());
        Invoice result = invoiceRepository.save(invoice);
        Assert.assertTrue("Unable to save invoice", result.getId() != null);
    }

    /**
     *
     * @throws RestExampleException
     */
    @Transactional
    @Test
    public void searchInvoicesOrderedByDueDate() throws RestExampleException {

        List<Invoice> results = new ArrayList<Invoice>();
        for (int i = 0 ; i < 10 ; ++i) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(""+i); //invoice number of 2 digits.
            invoice.setPoNumber((10 -i)+""); // po number of 2 digits
            invoice.setAmountCents(new Long(i));
            invoice.setDueDate(new LocalDate().plusDays(i));
            results.add(invoiceRepository.save(invoice));
        }

        //test for the size of limit
        List<Invoice> result1 = invoiceRepository.searchInvoices(0L, 10L, "");
        Assert.assertTrue("limit does not work", result1.size() == 10);
        LocalDate localDate = result1.get(0).getDueDate();
        for (int i = 1 ; i < 10 ; ++i) {
            Assert.assertTrue("ordering is not proper",localDate.isAfter(result1.get(i).getDueDate()));
            localDate = result1.get(i).getDueDate();

        }

        //test size of limit
        List<Invoice> result2 = invoiceRepository.searchInvoices(0L, 3L, "");
        Assert.assertTrue("limit does not work", result2.size() == 3);

        //test if offset is larger than size.
        List<Invoice> result3 = invoiceRepository.searchInvoices(11L, 10L, "");
        Assert.assertTrue("offset is not larger than size", result3.size() == 0);


        List<Invoice> result4 = invoiceRepository.searchInvoices(0L, 1L, "");
        Assert.assertTrue("paging does not work", result4.get(0).getInvoiceNumber().equals("9"));

        List<Invoice> result5 = invoiceRepository.searchInvoices(1L, 1L, "");
        Assert.assertTrue("paging does not work", result5.get(0).getInvoiceNumber().equals("8"));

        List<Invoice> result6 = invoiceRepository.searchInvoices(8L, 1L, "");
        Assert.assertTrue("paging does not work", result6.get(0).getInvoiceNumber().equals("1"));



    }

}
