package com.example.restservice.repository;

import com.example.restservice.model.Invoice;
import com.example.restservice.vaildation.RestExampleException;

import java.util.List;

/**
 * Created by melvin on 10/12/17.
 */
public interface InvoiceRepository {
    /**
     *
     * @return
     */
    public List<Invoice> findAllInvoices();

    /**
     * @param invoice
     * @return
     * @throws RestExampleException
     */
    public Invoice save(final Invoice invoice) throws RestExampleException;

    /**
     * search query can be empty or it will do a like query.
     * @param offset
     * @param limit
     * @param searchQuery
     * @return
     */
    public List<Invoice> searchInvoices(final Long offset, final Long limit, final String searchQuery);
}
