package my_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductDatabase {
    private List<ProductEntry> entries;

    public ProductDatabase() {
        entries = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] records = line.split(";"); // Separar os diferentes registros
                for (String record : records) {
                    String[] fields = record.split(","); // Separar os campos por vírgula
                    if (fields.length == 7) {
                        int id = Integer.parseInt(fields[0].trim());
                        int quantity = Integer.parseInt(fields[1].trim());
                        double pricePerProduct = Double.parseDouble(fields[2].trim());
                        double pvPerProduct = Double.parseDouble(fields[3].trim());
                        String entryOrExitDate = fields[4].trim();
                        int quantityChange = Integer.parseInt(fields[5].trim());
                        String changeType = fields[6].trim();

                        ProductEntry entry = new ProductEntry(id, quantity, pricePerProduct, pvPerProduct,
                                                              entryOrExitDate, quantityChange, changeType);
                        entries.add(entry);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void saveToFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (ProductEntry entry : entries) {
                bw.write(entry.toCSV() + ";");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    public void addEntry(ProductEntry newEntry, String filePath) {
        entries.add(newEntry);
        saveToFile(filePath);  // Salva o novo estado da lista no arquivo
        System.out.println("Entrada adicionada com sucesso: " + newEntry);
    }

    public boolean removeEntryById(int id, String filePath) {
        Iterator<ProductEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            ProductEntry entry = iterator.next();
            if (entry.getId() == id) {
                iterator.remove();
                saveToFile(filePath);  // Salva o novo estado da lista no arquivo
                System.out.println("Entrada com ID " + id + " removida com sucesso.");
                return true;
            }
        }
        System.out.println("Entrada com ID " + id + " não encontrada.");
        return false;
    }

    public boolean editEntryById(int id, int newQuantity, double newPricePerProduct, double newPvPerProduct,
                                 String newEntryOrExitDate, int newQuantityChange, String newChangeType, String filePath) {
        for (ProductEntry entry : entries) {
            if (entry.getId() == id) {
                entry.setQuantity(newQuantity);
                entry.setPricePerProduct(newPricePerProduct);
                entry.setPvPerProduct(newPvPerProduct);
                entry.setEntryOrExitDate(newEntryOrExitDate);
                entry.setQuantityChange(newQuantityChange);
                entry.setChangeType(newChangeType);
                saveToFile(filePath);  // Salva o novo estado da lista no arquivo
                System.out.println("Entrada com ID " + id + " editada com sucesso.");
                return true;
            }
        }
        System.out.println("Entrada com ID " + id + " não encontrada.");
        return false;
    }

    // Adicionado método para acessar a lista de entradas
    public List<ProductEntry> getEntries() {
        return entries;
    }
}
