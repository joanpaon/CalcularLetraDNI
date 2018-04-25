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
import org.japo.java.components.BackgroundPanel;
import org.japo.java.events.AEM;
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
    public static final String DEF_BACKGROUND = "img/background.png";

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

    // Construcción - GUI
    private void initComponents() {
        // Número de DNI
        txfDNI = new JTextField("");
        txfDNI.setFont(new Font("Consolas", Font.BOLD, 80));
        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(JTextField.RIGHT);
        txfDNI.addActionListener(new AEM(this));

        // Control de DNI
        lblDNI = new JLabel("•");
        lblDNI.setFont(new Font("Consolas", Font.BOLD, 80));
        lblDNI.setOpaque(true);
        lblDNI.setBackground(Color.WHITE);
        lblDNI.setBorder(txfDNI.getBorder());

        // Panel DNI
        JPanel pnlDNI = new JPanel();
        pnlDNI.add(txfDNI);
        pnlDNI.add(lblDNI);

        // Imagen de Fondo
        String pthDNI = prp.getProperty(PRP_BACKGROUND, DEF_BACKGROUND);
        URL urlDNI = ClassLoader.getSystemResource(pthDNI);
        Image imgDNI = new ImageIcon(urlDNI).getImage();

        // Panel Principal
        JPanel pnlPpal = new BackgroundPanel(imgDNI);
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(pnlDNI);

        // Ventana Principal
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

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(PRP_FAVICON, DEF_FAVICON));
    }

    // Calcula la letra del DNI actual
    public void procesarDNI(ActionEvent ae) {
        try {
            // Obtiene y Depura el Número de DNI Introducido
            String numDNI = txfDNI.getText().trim().toUpperCase();
            
            // Procesa el número de DNI
            if (UtilesValidacion.validarDato(numDNI, UtilesDNI.ER_NUM_DNI)) {
                // Número de DNI
                int numero = Integer.parseInt(UtilesDNI.normalizarNumeroDNI(numDNI));

                // Calcular letra
                char letra = UtilesDNI.calcularLetraDNI(numero);

                // Publicar la letra
                lblDNI.setText(letra + "");

                // Seleccionar Contenido Campo de Texto
                txfDNI.setSelectionStart(0);
            } else {
                throw new Exception("ERROR: Formato de DNI incorrecto");
            }
        } catch (Exception e) {
            // Mostrar El error
            System.out.println(e.getMessage());

            // Publicar la letra
            txfDNI.setText("");
            lblDNI.setText("•");
        }
    }
}
