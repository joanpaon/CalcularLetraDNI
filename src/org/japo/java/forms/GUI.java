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

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.japo.java.events.AEM;
import org.japo.java.lib.UtilesDNI;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class GUI extends JFrame {
    // Tamaño de la ventana
    public static final int VENTANA_ANC = 400;
    public static final int VENTANA_ALT = 300;
    
    // Referncias a elementos
    private JTextField txfDNI;
    private JLabel lblDNI;

    public GUI() {
        // Inicialización PREVIA
        beforeInit();

        // Creación del interfaz
        initComponents();

        // Inicialización POSTERIOR
        afterInit();
    }

    // Construcción del IGU
    private void initComponents() {
        // Fuente personalizada
        Font f = new Font("Consolas", Font.BOLD, 30);
        
        // Gestor de Eventos de Acción
        AEM aem = new AEM(this);
        
        // Número de NIF
        txfDNI = new JTextField("");
        txfDNI.setFont(f);
        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(JTextField.RIGHT);
        txfDNI.addActionListener(aem);
        
        // Guión separador
        JLabel lblGuion = new JLabel("-");
        lblGuion.setFont(f);
        
        // Letra del NIF
        lblDNI = new JLabel("*");
        lblDNI.setFont(f);
        
        // Panel Principal
        JPanel pnlPpal = new JPanel();
        pnlPpal.add(txfDNI);
        pnlPpal.add(lblGuion);
        pnlPpal.add(lblDNI);
        
        // Ventana principal
        setTitle("Cálculo NIF");
        setContentPane(pnlPpal);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    // Inicialización antes del IGU
    private void beforeInit() {

    }

    // Inicialización después del IGU
    private void afterInit() {

    }

    public void gestionarNIF(ActionEvent e) {
        // Texto introducido por el usuario
        String texto = txfDNI.getText();
        
        // Número de DNI
        int numero = UtilesDNI.extraerNumeroDNI(texto);
        
        // Procesa el numero
        if (UtilesDNI.validarNumeroDNI(numero)) {
            // Calcular letra
            char letra = UtilesDNI.calcularLetraDNI(numero);
            
            // Publicar la letra
            lblDNI.setText(letra + "");
        } else {
            // Publicar la letra
            lblDNI.setText("*");
        }
    }

}
