package my_package;

public class ProductEntry {
    private int id;
    private int quantity;
    private double pricePerProduct;
    private double pvPerProduct;
    private String entryOrExitDate;  // Data e se é Entry ou Exit
    private int quantityChange;  // Quantidade adicionada ou subtraída
    private String changeType;  // "added" ou "subtracted"

    public ProductEntry(int id, int quantity, double pricePerProduct, double pvPerProduct,
                        String entryOrExitDate, int quantityChange, String changeType) {
        this.id = id;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.pvPerProduct = pvPerProduct;
        this.entryOrExitDate = entryOrExitDate;
        this.quantityChange = quantityChange;
        this.changeType = changeType;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(double pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public double getPvPerProduct() {
        return pvPerProduct;
    }

    public void setPvPerProduct(double pvPerProduct) {
        this.pvPerProduct = pvPerProduct;
    }

    public String getEntryOrExitDate() {
        return entryOrExitDate;
    }

    public void setEntryOrExitDate(String entryOrExitDate) {
        this.entryOrExitDate = entryOrExitDate;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Quantity: " + quantity + ", Price per Product: " + pricePerProduct +
                ", PV per Product: " + pvPerProduct + ", Date: " + entryOrExitDate +
                ", Quantity Change: " + quantityChange + " (" + changeType + ")";
    }
    public String toCSV() {
        return id + "," + quantity + "," + pricePerProduct + "," + pvPerProduct + "," + entryOrExitDate + "," + quantityChange + "," + changeType;
    }

}
