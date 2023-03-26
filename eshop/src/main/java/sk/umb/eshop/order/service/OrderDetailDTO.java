package sk.umb.eshop.order.service;

public class OrderDetailDTO {
    private long orderId;
    private long customer_ID;
    private Type type;
    private boolean ordered;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(long customer_ID) {
        this.customer_ID = customer_ID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
}
