package com.example.restservice.repository;


import com.example.restservice.model.Invoice;
import com.example.restservice.model.QInvoices;
import com.example.restservice.vaildation.RestExampleException;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.List;

import static com.example.restservice.model.QInvoices.invoices;
import static com.querydsl.core.types.Projections.bean;


/**
 * Created by melvin on 10/12/17.
 */
@EnableTransactionManagement
@Repository("invoiceRepository")
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Inject
    SQLQueryFactory sqlQueryFactory;

    /**
     *
     * @return
     */
    @Override
    @Transactional
    public List<Invoice> findAllInvoices() {

        final QBean<Invoice> invoiceBean = bean(Invoice.class,
                invoices.all()
        );

        List<Invoice> invoiceList = sqlQueryFactory.from(QInvoices.invoices)
                .transform(GroupBy.groupBy(invoices.id).list(invoiceBean));
        return invoiceList;
    }


    /**
     *
     * @param offset
     * @param limit
     * @param searchQuery
     * @return
     */
    @Override
    @Transactional
    public List<Invoice> searchInvoices(final Long offset, final Long limit, final String searchQuery) {
        final QBean<Invoice> invoiceBean = bean(Invoice.class,
                invoices.id,
                invoices.invoiceNumber,
                invoices.poNumber,
                invoices.createdAt,
                invoices.amountCents,
                invoices.dueDate
                //SQLExpressions.count().over().as("totalCount") //if we need the total count for pagination
        );

        /*
            we do a like search. if search query is empty we return everything (like '%')
            if search query is "ABC" we perform a like query (like '%ABC%')
            like query is performed on both po number or invoice number
         */
        QueryModifiers queryModifiers = new QueryModifiers(limit, offset);
        return sqlQueryFactory.from(invoices)
                                .restrict(queryModifiers)
                                .where(invoices.invoiceNumber.likeIgnoreCase(searchQuery.isEmpty() ? "%" : "%"+searchQuery+"%")
                                        .or(invoices.poNumber.likeIgnoreCase(searchQuery.isEmpty() ? "%" : "%"+searchQuery+"%")))
                                //if the creation date is the same then we order by id desc as the latter will have a larger id.
                                .orderBy(invoices.createdAt.desc()).orderBy(invoices.id.desc())
                                .transform(GroupBy.groupBy(invoices.id).list(invoiceBean));
    }

    /**
     *
     * @param invoice
     * @return
     * @throws RestExampleException
     */
    @Override
    @Transactional
    public Invoice save(final Invoice invoice) throws RestExampleException {

        Assert.hasText(invoice.getPoNumber(), "po number can't be null");
        Assert.hasText(invoice.getInvoiceNumber(), "invoice number can't be null");
        Assert.notNull(invoice.getDueDate(), "due date can't be null");
        Assert.notNull(invoice.getAmountCents(), "amount cents can't be null");

        /* //example of error handling if invoice already exists

        Long count = sqlQueryFactory.from(invoices)
                .where(invoices.invoiceNumber.eq(invoice.getInvoiceNumber())).fetchCount();

        if (count >= 1) {
            throw new RestExampleException("Invoice has already has been added");
        }*/

        DateTime created = new DateTime();
        invoice.setCreatedAt(created);

        SQLInsertClause invoiceInsert = sqlQueryFactory.insert(invoices);
        invoiceInsert.set(invoices.invoiceNumber, invoice.getInvoiceNumber());
        invoiceInsert.set(invoices.poNumber, invoice.getPoNumber());
        invoiceInsert.set(invoices.amountCents, invoice.getAmountCents());
        invoiceInsert.set(invoices.createdAt, created );
        invoiceInsert.set(invoices.dueDate,  invoice.getDueDate());
        Long id = invoiceInsert.executeWithKey(invoices.id);
        invoice.setId(id);
        return invoice;
    }

}
