package com.epam.inet.provider.entity;

import java.util.Date;

/**
 * Tariff order
 */
public class Order extends Entity{

    /**
     * client
     */
    private User user;

    /**
     * ordered tariff
     */
    private Tariff tariff;

    /**
     * date and time of purchase
     */
    private Date dateTime;

    /**
     * amount paid
     */
    private double amount;

    private boolean paid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.amount, amount) != 0) return false;
        if (paid != order.paid) return false;
        if (dateTime != null ? !dateTime.equals(order.dateTime) : order.dateTime != null) return false;
        if (tariff != null ? !tariff.equals(order.tariff) : order.tariff != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = user != null ? user.hashCode() : 0;
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (paid ? 1 : 0);
        return result;
    }
}
