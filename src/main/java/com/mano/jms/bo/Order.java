package com.mano.jms.bo;

import java.io.Serializable;

/**
 * Created by manodyas on 2/15/2018.
 */
public class Order implements Serializable {
    private String side;
    private int qty;
    private String symbol;

    public Order(String side, int qty, String symbol) {
        this.side = side;
        this.qty = qty;
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
