package my_package;

public class Product {

    private int id;
    private String name;
    private int qtd;
    private float price;
    private float pv;
    private String date;
    private int editQtd;

    public Product(int id, String name, int qtd, float price, float pv, String date, int editQtd) {
        this.id = id;
        this.name = name;
        this.qtd = qtd;
        this.price = price;
        this.pv = pv;
        this.date = date;
        this.editQtd = editQtd;
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
    public int getQtd() {
        return qtd;
    }
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getPv() {
        return pv;
    }
    public void setPv(float pv) {
        this.pv = pv;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getEditQtd() {
        return editQtd;
    }
    public float getTotalPrice() {
        return price * qtd;
    }
    public float getTotalPv() {
        return pv * qtd;
    }
}
