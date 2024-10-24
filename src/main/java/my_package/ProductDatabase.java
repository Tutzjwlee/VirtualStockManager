package my_package;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    private String filePath;

    public ProductDatabase(String filePath) {
        this.filePath = filePath;
    }

    // Carrega todos os produtos do arquivo
    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    int id = Integer.parseInt(fields[0]);
                    String name = fields[1];
                    int qtd = Integer.parseInt(fields[2]);
                    float price = Float.parseFloat(fields[3]);
                    float pv = Float.parseFloat(fields[4]);
                    String date = fields[5];
                    int editQtd = Integer.parseInt(fields[6]);
                    products.add(new Product(id, name, qtd, price, pv, date, editQtd));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Salva todos os produtos no arquivo
    private void saveProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getQtd() + ","
                        + product.getPrice() + "," + product.getPv() + "," + product.getDate() + ","
                        + product.getEditQtd());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adiciona um novo produto
    public void addProduct(Product product) {
        List<Product> products = loadProducts();
        products.add(product);
        saveProducts(products);
    }

    // Edita um produto existente pelo ID
    public void editProduct(int id, Product updatedProduct) {
        List<Product> products = loadProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, updatedProduct);
                break;
            }
        }
        saveProducts(products);
    }

    // Deleta um produto pelo ID
    public void deleteProduct(int id) {
        List<Product> products = loadProducts();
        products.removeIf(product -> product.getId() == id);
        saveProducts(products);
    }

    // Busca um produto pelo ID
    public Product getProductById(int id) {
        List<Product> products = loadProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; // Produto não encontrado
    }
}
