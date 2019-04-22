/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.japo.java.components.BackgroundPanel;
import org.japo.java.events.AEM;
import org.japo.java.events.DEM;
import org.japo.java.libraries.UtilesDNI;
import org.japo.java.libraries.UtilesSwing;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class GUI extends JFrame implements ClipboardOwner {

    // Referencias
    private final Properties prp;

    // Modelos
    private Document doc;
    private DocumentListener dem;

    // Componentes
    private JButton btnClip;
    private JTextField txfDNI;
    private JLabel lblDNI;
    private JPanel pnlDNI;
    private JPanel pnlPpal;

    // Fuentes
    private Font fntDNI;

    // Imágenes
    private Image imgBack;
    private Image imgClip;

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
        // Portapapeles
        btnClip = new JButton();
        btnClip.setIcon(new ImageIcon(UtilesSwing.escalarImagen(imgClip, 64, 64)));
        btnClip.setFocusable(false);

        // Campo de Texto - Número de DNI
        txfDNI = new JTextField("");
        txfDNI.setFont(fntDNI.deriveFont(Font.PLAIN, 80f));
        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(JTextField.RIGHT);

        // Modelo Campo de Texto - Número de DNI
        doc = txfDNI.getDocument();

        // Gestor de Eventos de Documento - Número de DNI
        dem = new DEM(this);

        // Etiqueta - Control de DNI
        lblDNI = new JLabel("•"); // Alt + NUMPAD7
        lblDNI.setFont(fntDNI.deriveFont(Font.PLAIN, 80f));
        lblDNI.setOpaque(true);
        lblDNI.setBackground(Color.WHITE);
        lblDNI.setBorder(txfDNI.getBorder());

        // Panel DNI
        pnlDNI = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlDNI.setBackground(new Color(255, 255, 255, 150));
        pnlDNI.setBorder(new LineBorder(Color.DARK_GRAY));
        pnlDNI.add(btnClip);
        pnlDNI.add(txfDNI);
        pnlDNI.add(lblDNI);

        // Panel Principal
        pnlPpal = new BackgroundPanel(imgBack);
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(pnlDNI);

        // Ventana Principal
        setContentPane(pnlPpal);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty("look_and_feel_profile"));

        // Fuentes
        fntDNI = UtilesSwing.generarFuenteRecurso(prp.getProperty("font_resource"));

        // Imágenes
        imgBack = UtilesSwing.importarImagenRecurso(prp.getProperty("img_back_resource"));
        imgClip = UtilesSwing.importarImagenRecurso(prp.getProperty("img_clip_resource"));
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty("img_favicon_resource"));

        // Ventana Principal - Propiedades
        setTitle(prp.getProperty("form_title"));
        int width = Integer.parseInt(prp.getProperty("form_width"));
        int height = Integer.parseInt(prp.getProperty("form_height"));
        setSize(width, height);
        setLocationRelativeTo(null);

        // Ajustes de tamaño
        btnClip.setPreferredSize(new Dimension(
                btnClip.getPreferredSize().width + 2,
                txfDNI.getPreferredSize().height + 2));
        lblDNI.setPreferredSize(new Dimension(
                lblDNI.getPreferredSize().width + 2,
                txfDNI.getPreferredSize().height));

        // Registra Gestores de Eventos
        btnClip.addActionListener(new AEM(this));
        doc.addDocumentListener(dem);
    }

    // Notificación Pérdida Propiedad Portapapeles
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // No hacer nada
    }

    // Campo de texto > Portapapeles
    public final void procesarPortapapeles(ActionEvent e) {
        // Obtiene el Número de DNI Introducido
        String txtDNI = txfDNI.getText();

        // Forma DNI
        if (UtilesDNI.validarNumero(txtDNI)) {
            // Si Numero de DNI OK > Letra ya Calculada
            txtDNI += lblDNI.getText();
        }

        // Texto > Portapapeles
        UtilesSwing.exportarTextoPortapapeles(txtDNI, this);

        // Portapapeles > Consola - Realimentación
        System.out.println("Portapapeles: " + UtilesSwing.importarTextoPortapapeles());
    }

    // Procesamiento - Eliminación de Texto
    public final void procesarCambioTexto(DocumentEvent e) {
        SwingUtilities.invokeLater(() -> {
            procesarDNI();
        });
    }

    // Calcula la letra del DNI actual
    private void procesarDNI() {
        try {
            // Obtiene y Depura el Número de DNI Introducido
            String numDNI = txfDNI.getText().trim().toUpperCase();

            // Actualiza DNI ( *** NO Nuevos Eventos *** )
            doc.removeDocumentListener(dem);
            txfDNI.setText(numDNI);
            doc.addDocumentListener(dem);

            // Procesa Número de DNI
            if (UtilesDNI.validarNumero(numDNI)) {
                // Número de DNI
                int numero = Integer.parseInt(UtilesDNI.normalizarNumero(numDNI));

                // Calcula Letra de DNI
                char letra = UtilesDNI.calcularControl(numero);

                // Publica Letra de DNI
                lblDNI.setText(letra + "");

                // Seleccionar Contenido Campo de Texto
                txfDNI.setSelectionStart(0);
            } else {
                throw new Exception("ERROR: Formato de DNI incorrecto");
            }
        } catch (Exception e) {
            lblDNI.setText("•"); // Alt + NUMPAD7
        }
    }
}
