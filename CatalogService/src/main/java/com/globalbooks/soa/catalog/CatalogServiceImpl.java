package com.globalbooks.soa.catalog;

import jakarta.jws.WebService; // ✅ නැවත 'jakarta' වෙත මාරු විය
import java.util.HashMap;
import java.util.Map;

@WebService(serviceName = "CatalogService")
public class CatalogServiceImpl {
    // (මෙම class එකේ ඉතිරි code එකේ වෙනසක් නැත, ඒ නිසා එය මෙහි නැවත නොදැක්වේ)
    // ...ඔබේ getBookDetails සහ updateStock methods...
    private static final Map<String, Book> bookRepository = new HashMap<>();

    static {
        bookRepository.put("978-0321765723", new Book("The Lord of the Rings", "J.R.R. Tolkien", 25.99, 100));
        bookRepository.put("978-0743273565", new Book("The Da Vinci Code", "Dan Brown", 15.50, 250));
    }

    public Book getBookDetails(String isbn) {
        return bookRepository.get(isbn);
    }

    public String updateStock(String isbn, int quantity) {
        Book book = bookRepository.get(isbn);
        if (book != null) {
            book.setStockQuantity(quantity);
            bookRepository.put(isbn, book);
            return "Success: Stock for ISBN " + isbn + " updated to " + quantity;
        } else {
            return "Error: Book with ISBN " + isbn + " not found.";
        }
    }
}

class Book {
    private String title;
    private String author;
    private double price;
    private int stockQuantity;

    public Book(String title, String author, double price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}