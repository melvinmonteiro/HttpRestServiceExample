package com.example.restservice.model;

import com.example.restservice.model.deserializers.CustomLocalDateDeserializer;
import com.example.restservice.model.serializers.CustomDateTimeSerializer;
import com.example.restservice.model.serializers.CustomLocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by melvin on 10/12/17.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @JsonProperty("invoice_number")
    private String invoiceNumber;

    @JsonProperty("po_number")
    private String poNumber;

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("amount_cents")
    private Long amountCents;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonProperty("created_at")
    private DateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(Long amountCents) {
        this.amountCents = amountCents;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

}
