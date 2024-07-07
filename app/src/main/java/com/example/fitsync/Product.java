package com.example.fitsync;

public class Product {

    String productName, Description, imageUrl,quantity;

    public Product(String productName, String description,  String quantity,String imageUrl) {
        this.productName = productName;
        Description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }
    public Product() {
        // Default constructor required for Firebase
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
