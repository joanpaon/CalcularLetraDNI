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
package org.japo.java.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class BackgroundPanel extends JPanel {

    // Referencia Imagen Fondo
    private Image img;

    // Constructor parametrizado
    public BackgroundPanel(Image img) {
        if (img != null) {
            this.img = img;
        }
    }

    @Override
    public void paint(Graphics g) {
        // Dibuja la imagen en el área de la etiqueta
        g.drawImage(
                img,        // Objeto Image - Imagen de fondo para el panel
                0, 0,       // X e Y donde se va a insertar la imagen
                getWidth(), // Ancho de la imagen - Ancho del panel
                getHeight(),// Alto de la imagen - Alto del panel
                this);      // Dónde se dibujará la imagen - Panel

        // Panel Trasparente - Mostrar Imagen
        setOpaque(false);

        // Dibujar Panel
        super.paint(g);
    }
}
