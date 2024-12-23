package my_package;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
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

        // Configura o tamanho preferido do insidePanel e adiciona ao scrollPanel
        insidePanel.setPreferredSize(new Dimension(1200, insidePanel.getPreferredSize().height));
        scrollPanel.setViewportView(insidePanel);
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
