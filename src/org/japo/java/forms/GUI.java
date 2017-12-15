/*
 * Copyright 2017 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import org.japo.java.components.BackgroundPanel;
import org.japo.java.events.AEM;
import org.japo.java.events.DEM;
import org.japo.java.libraries.UtilesDNI;
import org.japo.java.libraries.UtilesSwing;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_LOOK_AND_FEEL = "look_and_feel";
    public static final String PRP_FAVICON = "favicon";
    public static final String PRP_BACKGROUND = "background";

    // Valores por Defecto
    public static final String DEF_LOOK_AND_FEEL = UtilesSwing.LNF_NIMBUS;
    public static final String DEF_FAVICON = "img/favicon.png";
    public static final String DEF_BACKGROUND = "img/dni.png";

    // Referencias
    private Properties prp;
    private JTextField txfDNI;
    private JLabel lblDNI;

    // Constructor
    public GUI(Properties prp) {
        // Inicialización Anterior
        initBefore(prp);

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción del IGU
    private void initComponents() {
        // Número de NIF
        txfDNI = new JTextField("");
        txfDNI.setFont(new Font("Consolas", Font.BOLD, 80));
        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(JTextField.CENTER);
        txfDNI.addActionListener(new AEM(this));
        txfDNI.getDocument().addDocumentListener(new DEM(this));

        // Letra del NIF
        lblDNI = new JLabel(" ");
        lblDNI.setFont(new Font("Consolas", Font.BOLD, 80));
        lblDNI.setOpaque(true);
        lblDNI.setBackground(Color.WHITE);
        lblDNI.setBorder(txfDNI.getBorder());

        // Panel DNI
        JPanel pnlDNI = new JPanel();
        pnlDNI.add(txfDNI);
        pnlDNI.add(lblDNI);

        // Panel Principal
        String fondoPpal = prp.getProperty(PRP_BACKGROUND, DEF_BACKGROUND);
        URL urlPpal = ClassLoader.getSystemResource(fondoPpal);
        Image imgPpal = new ImageIcon(urlPpal).getImage();
        JPanel pnlPpal = new BackgroundPanel(imgPpal);
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(pnlDNI);

        // Ventana principal
        setContentPane(pnlPpal);
        setTitle("Swing Manual #06");
        setResizable(false);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore(Properties prp) {
        // Memorizar Referencia
        this.prp = prp;

        // Establecer LnF
        UtilesSwing.establecerLnF(prp.getProperty(PRP_LOOK_AND_FEEL, DEF_LOOK_AND_FEEL));
    }

    // Inicialización Anterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(PRP_FAVICON, DEF_FAVICON));
    }

    // Calcula la letra del DNI actual
    public void procesarDNI(ActionEvent ae) {
        try {
            // Convierte el texto a mayúsculas
            String texto = txfDNI.getText().toUpperCase();

            if (UtilesValidacion.validarNumeroDNI(texto)) {
                // Actualiza el campo de texto
                txfDNI.setText(texto);

                // Procesa Dígito Inicial (Extranjeros)
                texto = UtilesDNI.procesarDigitoInicial(texto);

                // Número de DNI
                int numero = Integer.parseInt(texto);

                // Calcular letra
                char letra = UtilesDNI.calcularControl(numero);

                // Publicar la letra
                lblDNI.setText(letra + "");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            // Publicar la letra
            txfDNI.setBackground(Color.RED);
            lblDNI.setText("•");
        }
    }

    // Reinicia interfaz para nuevo DNI
    public void reiniciarInterfaz(DocumentEvent e) {
        txfDNI.setBackground(Color.WHITE);
        lblDNI.setText(" ");
    }
}
