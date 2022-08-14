package ru.embedika.dto;

public class PageRequestOfCarsDto {
    private int page;
    private int size;
    private SortOfCarsDto[] orders;

    public PageRequestOfCarsDto() {
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public SortOfCarsDto[] getOrders() {
        return orders;
    }

    public void setOrders(SortOfCarsDto[] orders) {
        this.orders = orders;
    }
}
