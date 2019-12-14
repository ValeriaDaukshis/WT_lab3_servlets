package Beans;

public class Book {
    private int Id;
    private String name;
    private String author;
    private int pages;
    private double price;

    public Book(int Id, String name, String author, int pages, double price) {
        this.Id = Id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return Id;
    }

    public int getPages() {
        return pages;
    }

    public double getPrice() {
        return price;
    }
}
