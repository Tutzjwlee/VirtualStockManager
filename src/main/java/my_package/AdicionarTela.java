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
        editButton.addActionListener(evt -> editButtonActionPerformed(evt));
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

     // Método para encontrar uma string no arquivo, salvar a linha e retornar o número da linha
     public int findStringInFile(String filePath, String searchString, StringBuilder foundLine) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            lineNumber++;
            if (line.contains(searchString)) {
                foundLine.setLength(0); // Garante que o StringBuilder está limpo antes de usar
                foundLine.append(line); // Salva o conteúdo da linha no StringBuilder
                System.out.println("String encontrada na linha " + lineNumber + ": " + line);
                reader.close();
                return lineNumber; // Retorna o número da linha onde a string foi encontrada
            }
        }

        reader.close();
        System.out.println("String não encontrada no arquivo.");
        return -1; // Retorna -1 se a string não for encontrada
    }
    public static String[] splitString(String input) {
        if (input == null || input.isEmpty()) {
            return new String[0]; // Retorna um array vazio se a string for nula ou vazia
        }
        return input.split(",");
    }
    public static void replaceLineInFile(String filePath, int lineNumber, String newLineContent) throws IOException {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();

        // Lê todas as linhas do arquivo
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int currentLine = 0;

        while ((line = reader.readLine()) != null) {
            currentLine++;
            if (currentLine == lineNumber) {
                // Substitui a linha desejada pelo novo conteúdo
                lines.add(newLineContent);
            } else {
                lines.add(line); // Mantém as outras linhas inalteradas
            }
        }

        reader.close();

        // Reescreve o arquivo com o novo conteúdo
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String fileLine : lines) {
            writer.write(fileLine);
            writer.newLine();
        }

        writer.close();
        System.out.println("Linha " + lineNumber + " substituída com sucesso.");
    }
    public void editButtonActionPerformed(java.awt.event.ActionEvent evt) { 
        String filePath = "src/main/java/my_package/DataBase.txt";
        int lineNumber = 0;
        StringBuilder foundLine = new StringBuilder();
    
        if (selectedPanel != null) {
            String productName = selectedPanel.getProductName();
            String newProductName = "", Qtd = "", price = "", unityPv = "", date = "", QtdAddSub = "";
           
    
            try {
                lineNumber = findStringInFile(filePath, productName, foundLine);
                String[] values = splitString(foundLine.toString());
                Qtd = values[1];
                price = values[2];
                unityPv = values[3];
                date = values[4];
                
                int newQtd = Integer.parseInt(Qtd);
                if (JOptionPane.showConfirmDialog(this, "Deseja editar o produto: " + productName + "?") == 0) {
                    // Loop para editar o nome do produto
                    while (true) {
                        newProductName = JOptionPane.showInputDialog("Digite o nome do produto (ou deixe vazio para manter o nome atual):");
                        
                        if (newProductName == null) {
                            // Se o usuário cancelar, manter o nome atual
                            newProductName = productName;
                            break;
                        } else if (newProductName.trim().isEmpty()) {
                            // Se o usuário não digitar nada, manter o nome atual
                            newProductName = productName;
                            break;
                        } else if (verifyStringInFile(filePath, newProductName)) {
                            // Se o nome já existir, mostrar mensagem de erro e continuar o loop
                            JOptionPane.showMessageDialog(this, "Produto já cadastrado. Por favor, insira um nome diferente.");
                        } if (newProductName != null && matchesPattern(newProductName, ".+") && verifyStringInFile(filePath, newProductName)) {
                            // Nome válido e não existente, sair do loop
                            break;
                        }
                    }
                    
                    // Entrada e validação para o preço
                    while (true) {
                        price = JOptionPane.showInputDialog("Digite o preço do produto:");
                        if (price == null || !matchesPattern(price, "\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(this, "Preço inválido.");
                        } else {
                            break;
                        }
                    }
    
                    // Entrada e validação para o PV
                    while (true) {
                        unityPv = JOptionPane.showInputDialog("Digite o PV do produto:");
                        if (unityPv == null || !matchesPattern(unityPv, "\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(this, "PV inválido.");
                        } else {
                            break;
                        }
                    }
    
                    // Adição ou subtração de quantidade
                    if (JOptionPane.showConfirmDialog(this, "Deseja adicionar ou subtrair de " + productName + "?") == JOptionPane.YES_OPTION) {
                        String[] options = {"Adicionar", "Subtrair"};
                        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Operação",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    
                        if (choice != JOptionPane.CLOSED_OPTION) {
                            while (true) {
                                QtdAddSub = JOptionPane.showInputDialog(choice == 0 ? "Digite a quantidade a ser adicionada:" : "Digite a quantidade a ser subtraída:");
                                if (QtdAddSub == null || !matchesPattern(QtdAddSub, "\\d+")) {
                                    JOptionPane.showMessageDialog(this, "Quantidade inválida.");
                                    QtdAddSub = "0"; // Define como 0 em caso de cancelamento ou entrada inválida
                                } else {
                                    int currentQtd = Integer.parseInt(Qtd);
                                    int adjustment = choice == 0 ? Integer.parseInt(QtdAddSub) : -Integer.parseInt(QtdAddSub);
                                    newQtd = currentQtd + adjustment;
                                    if (newQtd < 0) {
                                        JOptionPane.showMessageDialog(this, "Quantidade inválida.");
                                        QtdAddSub = "0"; // Define como 0 se o resultado for negativo
                                    } else {
                                        QtdAddSub = (choice == 0 ? "+" : "-") + QtdAddSub;
                                        break;
                                    }
                                }
                            }
                        } else {
                            QtdAddSub = "0"; // Define como 0 se o usuário fechar a caixa de opções
                        }
                    } else {
                        QtdAddSub = "0"; // Define como 0 se o usuário clicar em "Não"
                    }
    
                    // Entrada e validação para a data
                    while (true) {
                        date = JOptionPane.showInputDialog("Digite a data da última entrada ou saída do produto (dd/MM/yyyy):");
                        if (date == null || !matchesPattern(date, "\\d{2}/\\d{2}/\\d{4}")) {
                            JOptionPane.showMessageDialog(this, "Data inválida.");
                        } else {
                            break;
                        }
                    }
    
                    // Construção da nova linha de dados
                    String dataString = String.join(",", newProductName, String.valueOf(newQtd), price, unityPv, date, QtdAddSub);
    
                    // Substituição da linha no arquivo e atualização do painel
                    replaceLineInFile(filePath, lineNumber, dataString);
                    selectedPanel.setText(newProductName, String.valueOf(newQtd), price, calcTotalPV(String.valueOf(newQtd), price, unityPv), QtdAddSub, calcTotalValue(String.valueOf(newQtd), price), date, unityPv);
                    selectedPanel.setBackground(null);
                    selectedPanel = null;
                    repaint();
                    revalidate();
    
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar o produto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum painel selecionado para editar.");
        }
    }
    

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedPanel != null) {
            // Obtém o nome do produto para identificar a linha no arquivo
            String productName = selectedPanel.getProductName();
            
            // Exibe uma caixa de diálogo para confirmar a remoção
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Tem certeza de que deseja remover o produto: " + productName + "?",
                    "Confirmação de Remoção",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE);
    
            // Se o usuário confirmar a remoção
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Remove o painel da interface
                insidePanel.remove(selectedPanel);
                panels.remove(selectedPanel);
                insidePanel.revalidate();
                insidePanel.repaint();
                selectedPanel = null;
    
                // Chama o método para remover a linha do arquivo
                removeLineFromFile(productName);
            }
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
            productName = JOptionPane.showInputDialog("Digite o nome do produto:");
            if(JOptionPane.CANCEL_OPTION == 0){
            
                break;
            }
            if (productName == null && JOptionPane.CANCEL_OPTION != 0){
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
