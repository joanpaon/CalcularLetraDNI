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

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_LOOK_AND_FEEL_PROFILE = "form_look_and_feel_profile";
    public static final String PRP_FAVICON_RESOURCE = "form_favicon_resource";
    public static final String PRP_FORM_TITLE = "form_title";
    public static final String PRP_FORM_HEIGHT = "form_height";
    public static final String PRP_FORM_WIDTH = "form_width";
    public static final String PRP_FORM_BACKGROUND_RESOURCE = "form_background_resource";
    public static final String PRP_FORM_FONT_RESOURCE = "form_font_resource";

    // Valores por Defecto
    public static final String DEF_LOOK_AND_FEEL_PROFILE = UtilesSwing.LNF_WINDOWS_PROFILE;
    public static final String DEF_FAVICON_RESOURCE = "img/favicon.png";
    public static final String DEF_FORM_TITLE = "Swing Manual App";
    public static final int DEF_FORM_HEIGHT = 300;
    public static final int DEF_FORM_WIDTH = 500;
    public static final String DEF_FORM_BACKGROUND_RESOURCE = "img/background.jpg";
    public static final String DEF_FORM_FONT_RESOURCE = "fonts/default_font.ttf";

    // Referencias
    private Properties prp;
    
    // Componentes
    private JTextField txfDNI;
    private JLabel lblDNI;
    private JPanel pnlDNI;
    private JPanel pnlPpal;

    // Constructor
    public GUI(Properties prp) {
        // Conectar Referencia
        this.prp = prp;

        // Inicialización Anterior
        initBefore();

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción - GUI
    private void initComponents() {
        // Número de DNI
        txfDNI = new JTextField("");
        txfDNI.setFont(UtilesSwing.importarFuenteRecurso(
                prp.getProperty(PRP_FORM_FONT_RESOURCE, DEF_FORM_FONT_RESOURCE)).
                deriveFont(Font.PLAIN, 80f));
        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(JTextField.RIGHT);
        txfDNI.addActionListener(new AEM(this));

        // Control de DNI
        lblDNI = new JLabel("•");
        lblDNI.setFont(UtilesSwing.importarFuenteRecurso(
                prp.getProperty(PRP_FORM_FONT_RESOURCE, DEF_FORM_FONT_RESOURCE)).
                deriveFont(Font.PLAIN, 80f));
        lblDNI.setOpaque(true);
        lblDNI.setBackground(Color.WHITE);
        lblDNI.setBorder(txfDNI.getBorder());

        // Panel DNI
        pnlDNI = new JPanel();
        pnlDNI.add(txfDNI);
        pnlDNI.add(lblDNI);

        // Imagen de Fondo
        String pthDNI = prp.getProperty(
                PRP_FORM_BACKGROUND_RESOURCE, DEF_FORM_BACKGROUND_RESOURCE);
        URL urlDNI = ClassLoader.getSystemResource(pthDNI);
        Image imgDNI = new ImageIcon(urlDNI).getImage();

        // Panel Principal
        pnlPpal = new BackgroundPanel(imgDNI);
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(pnlDNI);

        // Ventana Principal
        setContentPane(pnlPpal);
        setTitle(prp.getProperty(PRP_FORM_TITLE, DEF_FORM_TITLE));
        try {
            int height = Integer.parseInt(prp.getProperty(PRP_FORM_HEIGHT));
            int width = Integer.parseInt(prp.getProperty(PRP_FORM_WIDTH));
            setSize(width, height);
        } catch (NumberFormatException e) {
            setSize(DEF_FORM_WIDTH, DEF_FORM_HEIGHT);
        }
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty(
                PRP_LOOK_AND_FEEL_PROFILE, DEF_LOOK_AND_FEEL_PROFILE));
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(
                PRP_FAVICON_RESOURCE, DEF_FAVICON_RESOURCE));
    }

    // Calcula la letra del DNI actual
    public void procesarDNI(ActionEvent ae) {
        try {
            // Obtiene y Depura el Número de DNI Introducido
            String numDNI = txfDNI.getText().trim().toUpperCase();
            
            // Procesa el número de DNI
            if (UtilesDNI.validarNumero(numDNI)) {
                // Número de DNI
                int numero = Integer.parseInt(UtilesDNI.normalizarNumero(numDNI));

                // Calcular letra
                char letra = UtilesDNI.calcularControl(numero);

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
