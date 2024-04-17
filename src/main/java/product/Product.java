package product;

import product.markup.Markup;
import product.promotion.Promotion;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal unitCost;
    private Markup markupStrategy;
    private Promotion promotionStrategy;
    private BigDecimal standardUnitPrice;
    private BigDecimal promotionalUnitPrice;

    public Product(int id, String name, BigDecimal unitCost, Markup markup, Promotion promotion) {
        this.id = id;
        this.name = name;
        this.markupStrategy = markup;
        this.unitCost = unitCost;
        this.promotionStrategy = promotion;
    }

    public BigDecimal getStandardUnitPrice() {
        if (standardUnitPrice == null) {
            standardUnitPrice = calculateStandardUnitPrice();
        }
        return standardUnitPrice;
    }

    public BigDecimal calculateStandardUnitPrice() {

        return markupStrategy.calculateStandardPriceUnit(unitCost);
    }

    public BigDecimal getPromotionalUnitPrice() {
        if (promotionalUnitPrice == null) {
            promotionalUnitPrice = calculatePromotionalUnitPrice();
        }
        return promotionalUnitPrice;
    }

    public BigDecimal calculatePromotionalUnitPrice() {
        return promotionStrategy.calculatePromotionalUnitPrice(standardUnitPrice);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        this.standardUnitPrice = calculateStandardUnitPrice();
        this.promotionalUnitPrice = calculatePromotionalUnitPrice();
    }

    public Markup getMarkup() {
        return markupStrategy;
    }

    public void setMarkup(Markup markup) {
        this.markupStrategy = markup;
        this.standardUnitPrice = calculateStandardUnitPrice();
        this.promotionalUnitPrice = calculatePromotionalUnitPrice();
    }

    public Promotion getPromotion() {
        return promotionStrategy;
    }

    public void setPromotion(Promotion promotion) {
        this.promotionStrategy = promotion;
        this.standardUnitPrice = calculateStandardUnitPrice();
        this.promotionalUnitPrice = calculatePromotionalUnitPrice();
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", unitCost=" + unitCost +
            ", markupStrategy=" + markupStrategy +
            ", promotionStrategy=" + promotionStrategy +
            ", standardUnitPrice=" + standardUnitPrice +
            ", promotionalUnitPrice=" + promotionalUnitPrice +
            '}';
    }
}
