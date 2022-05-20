package root.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ResponseDto {
    private SimpleStringProperty productName = new SimpleStringProperty();
    private SimpleDoubleProperty productCount = new SimpleDoubleProperty();
    private SimpleDoubleProperty productPrice = new SimpleDoubleProperty();
    private SimpleDoubleProperty exchangeValue = new SimpleDoubleProperty();

    public double getExchangeValue() {
        return exchangeValue.get();
    }

    public SimpleDoubleProperty exchangeValueProperty() {
        return exchangeValue;
    }

    public void setExchangeValue(double exchangeValue) {
        this.exchangeValue.set(exchangeValue);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public double getProductCount() {
        return productCount.get();
    }

    public SimpleDoubleProperty productCountProperty() {
        return productCount;
    }

    public void setProductCount(double productCount) {
        this.productCount.set(productCount);
    }

    public double getProductPrice() {
        return productPrice.get();
    }

    public SimpleDoubleProperty productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }
}
