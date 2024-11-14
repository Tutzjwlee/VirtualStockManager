package my_package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    // Atributo que armazena a lista de produtos
    private List<String[]> products = new ArrayList<>();
    private static int i;

    // Método para ler o arquivo e carregar a lista de produtos
    public void readDataBase() {
        String filePath = "src/main/java/my_package/DataBase.txt";  // Substitua pelo caminho do seu arquivo

        try {
            List<String> lines = readFileByLine(filePath);
            for (String line : lines) {
                String[] parts = splitByComma(line);
                
                // Verifica se o array tem ao menos 5 elementos para evitar erros
                if (parts.length >= 5) {
                    // Calcula totalPv (qtd * pv) e totalValue (qtd * price)
                    try {
                        int qtd = Integer.parseInt(parts[1]);
                        float pv = Float.parseFloat(parts[3]);
                        float price = Float.parseFloat(parts[2]);
                        
                        float totalPv = qtd * pv;
                        float totalValue = qtd * price;
                        
                        // Adiciona os valores ao array parts
                        parts = appendToArray(parts, String.valueOf(totalPv), String.valueOf(totalValue));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter para número: " + e.getMessage());
                    }
                }
                
                products.add(parts); // Adiciona cada linha dividida como um array à lista
                i++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método auxiliar para ler o arquivo linha por linha
    private static List<String> readFileByLine(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    // Método auxiliar para dividir a linha por vírgulas
    private static String[] splitByComma(String line) {
        return line.split(",");
    }
    
    // Método auxiliar para adicionar elementos ao final de um array de String
    private static String[] appendToArray(String[] array, String... elements) {
        String[] newArray = new String[array.length + elements.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.arraycopy(elements, 0, newArray, array.length, elements.length);
        return newArray;
    }

    // Getter para acessar a lista de produtos
    public List<String[]> getProducts() {
        return products;
    }

    public static int getI() {
        return i;
    }
}
