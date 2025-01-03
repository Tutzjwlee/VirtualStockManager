package my_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdicionarTela extends javax.swing.JFrame {

    private JPanel insidePanel;
    private ArrayList<ProductPanel> panels;
    private ProductPanel selectedPanel;

    public AdicionarTela() {
        initComponents();

        // Inicialização dos atributos
        insidePanel = new JPanel();
        panels = new ArrayList<>();
        selectedPanel = null;

        // Configuração do layout de insidePanel
        insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.Y_AXIS));

        // Exemplo de adicionar um painel fixo no topo
        ProductPanel productPanelLabel = new ProductPanel();
        productPanelLabel.setEditableFalse();
        insidePanel.add(productPanelLabel);

        // Lê dados do banco e popula o painel
        Product product = new Product();
        product.readDataBase();
        List<String[]> products = product.getProducts();

        // Adiciona os painéis dos produtos
        for (String[] parts : products) {
            ProductPanel panel = new ProductPanel();
            panel.setText(parts[0], parts[1], parts[2], parts[6], parts[5], parts[7], parts[4], parts[3]);
            panels.add(panel);

            // Configura o comportamento de clique
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    for (ProductPanel p : panels) {
                        p.setBackground(null); // Cor padrão
                    }
                    panel.setBackground(java.awt.Color.LIGHT_GRAY); // Cor de seleção
                    selectedPanel = panel; // Define o painel selecionado
                }
            });

            insidePanel.add(panel);
        }

       
        insidePanel.revalidate();
        insidePanel.repaint();

        // Configura o JScrollPane
        scrollPanel.setViewportView(insidePanel);
        scrollPanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(10);

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        editButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane(); // Alterado para JScrollPane

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        editButton.setText("Edit Product");
        
        removeButton.setText("Remove Product");
        removeButton.addActionListener(evt -> removeButtonActionPerformed(evt));

        addButton.setText("Add new Product");
        addButton.addActionListener(evt -> addButtonActionPerformed(evt));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                    .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        pack();
    }


    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedPanel != null) {
            // Obtém o nome do produto para identificar a linha no arquivo
            String productName = selectedPanel.getProductName();
    
            // Remove o painel da interface
            insidePanel.remove(selectedPanel);
            panels.remove(selectedPanel);
            insidePanel.revalidate();
            insidePanel.repaint();
            selectedPanel = null;
    
            // Chama o método para remover a linha do arquivo
            removeLineFromFile(productName);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Nenhum painel selecionado para remover.");
        }
    }
    /**
 * Remove uma linha do arquivo com base no nome do produto.
 * @param productName Nome do produto a ser removido do arquivo.
 */
private void removeLineFromFile(String productName) {
    File inputFile = new File("src/main/java/my_package/DataBase.txt");
    File tempFile = new File("src/main/java/my_package/temp_DataBase.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // Divide a linha em partes para verificar o nome do produto
            String[] parts = currentLine.split(",");
            if (parts.length > 0 && parts[0].equalsIgnoreCase(productName)) {
                // Ignora esta linha (não escreve no arquivo temporário)
                continue;
            }
            // Escreve as outras linhas no arquivo temporário
            writer.write(currentLine);
            writer.newLine();
        }
    } catch (IOException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Erro ao atualizar o arquivo: " + e.getMessage());
    }

    // Substitui o arquivo original pelo temporário
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        javax.swing.JOptionPane.showMessageDialog(this, "Erro ao substituir o arquivo original.");
    }
}

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Implementar funcionalidade de adicionar novo painel
        String dataString = "";
        String filePath = "src/main/java/my_package/DataBase.txt";
        String QtdAddSub = "0";

        String datePattern = "\\d{2}/\\d{2}/\\d{4}";        // Data no formato dd/MM/yyyy
        String positiveIntPattern = "\\d+";                 // Inteiro positivo
        String negativeIntPattern = "-\\d+";                // Inteiro negativo
        String positiveFloatPattern = "\\d+\\.\\d+";        // Float positivo
        String positiveNumberPatter = "^\\d+$";            // Número positivo

        String productName = "";
        String Qtd = "";
        String price = "";
        String unityPv = "";
        String date = "";

        while(true){
            productName = JP("Digite o nome do produto:");
        if (productName == null){
            JOptionPane.showMessageDialog(this, "Nome do produto inválido.");
            
        } 
        if (!matchesPattern(productName, ".+")) {
            JOptionPane.showMessageDialog(this, "Nome do produto inválido.");
            
        }
        if(!verifyStringInFile(filePath, productName)){
            JOptionPane.showMessageDialog(this, "Produto já cadastrado.");
            
        }
    
        if(productName != null && matchesPattern(productName, ".+")&& verifyStringInFile(filePath, productName)){
        dataString += productName + ",";
        break;
        }}
        while(true){
            
            Qtd = JP("Digite a quantidade de produtos:");
        if (Qtd == null) JOptionPane.showMessageDialog(this, "Quantidade inválida.");;
        if (!matchesPattern(QtdAddSub, positiveIntPattern)) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            
        }
        if (matchesPattern(Qtd, positiveIntPattern)&& Qtd != null ) {
            dataString += Qtd + ",";
            break;
            }
        }
        while(true){
            price = JP("Digite o preço do produto:");
            if (price == null)JOptionPane.showMessageDialog(this, "Preço inválido."); 
            if (!matchesPattern(price, positiveNumberPatter)) {
                JOptionPane.showMessageDialog(this, "Preço inválido.");
                
        }
            if (matchesPattern(price, positiveNumberPatter)&& price != null) {
            dataString += price + ",";
            break;
        }
        }
        while(true){
        unityPv = JP("Digite o PV do produto:");
        if (unityPv == null) JOptionPane.showMessageDialog(this, "PV inválido.");
        if (!matchesPattern(unityPv, positiveNumberPatter)) {
            JOptionPane.showMessageDialog(this, "PV inválido.");
           
        }
        if (matchesPattern(unityPv, positiveNumberPatter)&& unityPv != null) {
            dataString += unityPv + ",";
            break;
        }
        }
        while (true) { 
            
        
            date = JP("Digite a data de validade do produto (dd/MM/yyyy):");
            if (date == null) JOptionPane.showMessageDialog(this, "Data inválida.");
            if (!matchesPattern(date, datePattern)) {
                JOptionPane.showMessageDialog(this, "Data inválida.");
            
             }
            if (matchesPattern(date, datePattern)&& date != null) {
                dataString += date + ",";
                break;
            }
     }
        dataString += QtdAddSub;
    
        try {
            appendToLastEmptyLine(filePath, dataString);
            ProductPanel panel = new ProductPanel();
            panel.setText(productName, Qtd, price, calcTotalPV(Qtd, price, unityPv), QtdAddSub, calcTotalValue(Qtd, price), date, unityPv);
            panels.add(panel);
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    for (ProductPanel p : panels) {
                        p.setBackground(null); // Cor padrão
                    }
                    panel.setBackground(java.awt.Color.LIGHT_GRAY); // Cor de seleção
                    selectedPanel = panel; // Define o painel selecionado
                }
            });
            insidePanel.add(panel);
            repaint();
            revalidate();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar o produto: " + e.getMessage());
        }
    }
    public static boolean verifyStringInFile(String filePath, String searchString) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove espaços e separa as strings por vírgulas
                String[] values = line.split(",");
                for (String value : values) {
                    // Verifica se há correspondência ignorando letras maiúsculas e minúsculas
                    if (value.trim().equalsIgnoreCase(searchString.trim())) {
                        return false; // Encontrou um match
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return true; // Não encontrou o match
    }
    public static boolean matchesPattern(String text, String regex) {
        return Pattern.matches(regex, text);
    }

    public String JP(String text){
        return JOptionPane.showInputDialog(text);
    }

    public String calcTotalPV(String qtd, String price, String pv){
        int qtdInt = Integer.parseInt(qtd);
       
        float pvFloat = Float.parseFloat(pv);
        float totalPv = qtdInt * pvFloat;
       
        return totalPv+"";
    }
    public String calcTotalValue(String qtd, String price){
        int qtdInt = Integer.parseInt(qtd);
        float priceFloat = Float.parseFloat(price);
        float totalValue = qtdInt * priceFloat;
        return totalValue+"";
    }
    public static void appendToLastEmptyLine(String filePath, String text) throws IOException {
        // Lê todas as linhas do arquivo
        Path path = Paths.get(filePath);
        StringBuilder content = new StringBuilder();

        if (Files.exists(path)) {
            // Lê o arquivo e mantém as linhas
            for (String line : Files.readAllLines(path)) {
                if (!line.isBlank()) {
                    content.append(line).append(System.lineSeparator());
                } else {
                    content.append(System.lineSeparator()); // Adiciona linhas em branco
                }
            }
        }

        // Adiciona o texto na última linha vazia
        content.append(text).append(System.lineSeparator());

        // Escreve o conteúdo atualizado no arquivo
        Files.write(path, content.toString().getBytes());
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdicionarTela().setVisible(true));
    }

    private javax.swing.JButton addButton;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton removeButton;
    private javax.swing.JScrollPane scrollPanel;
}
