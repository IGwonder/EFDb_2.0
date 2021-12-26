package Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "rental", schema = "sakila", catalog = "")
public class PaymentEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_date")
    private Timestamp paymentDate;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public PaymentEntity(Integer paymentId, Integer customerId, Integer staffId, Integer rentalId, BigDecimal amount, Timestamp paymentDate, Timestamp lastUpdate) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.lastUpdate = lastUpdate;
    }
    public PaymentEntity(){}

}
