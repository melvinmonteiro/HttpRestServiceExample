package com.example.restservice.controller;

import com.example.restservice.model.Invoice;
import com.example.restservice.repository.InvoiceRepository;
import com.example.restservice.vaildation.RestExampleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoint Controller for invoices.
 * Created by melvin on 10/12/17.
 */
@RequestMapping("/invoices")
@RestController("invoiceController")
public class InvoiceRestController {

    @Autowired
    InvoiceRepository invoiceRepository;

    /**
     *
     * @return
     */
    @RequestMapping(path = "" ,method = RequestMethod.GET)
    @ResponseBody
    public List<Invoice> findInvoices(){
        return invoiceRepository.findAllInvoices();
    }

    /**
     *
     * @param invoice
     * @return
     * @throws RestExampleException
     */
    @RequestMapping(path= "", method = RequestMethod.POST , consumes = "application/json")
    @ResponseBody
    public Invoice save(@RequestBody Invoice invoice) throws RestExampleException {

        if (StringUtils.isEmpty(invoice.getPoNumber())
                || StringUtils.isEmpty(invoice.getInvoiceNumber())
                || StringUtils.isEmpty(invoice.getAmountCents())
                   || StringUtils.isEmpty(invoice.getDueDate())) {
            throw new RestExampleException("Required fields is po number/invoice/amount/due date is missing");
        }
        try {
            return invoiceRepository.save(invoice);
        }
        catch (Exception ex) {
            throw new RestExampleException(ex.getMessage());
        }
    }

    /**
     *
     * @param offset
     * @param limit
     * @param searchQuery
     * @return
     */
    @RequestMapping(value = "/offset/{offset}/limit/{limit}", method = RequestMethod.GET)
    public List<Invoice> findPatents(@PathVariable(value = "offset") Long offset, //we can set a default
                                   @PathVariable(value = "limit") Long limit, //we can set a default
                                   @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery) {
        return invoiceRepository.searchInvoices(offset, limit, searchQuery);
    }

}
