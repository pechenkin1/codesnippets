package com.myworktechs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Q3_Circular implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Q3_Circular());
    }

    private static final int DRAWING_WIDTH = 300;
    private static final int DRAWING_HEIGHT = DRAWING_WIDTH;

    private DrawingRectangle drawingRectangle;

    public Q3_Circular() {
        int center = DRAWING_WIDTH / 2;
        Rectangle2D rectangle = new Rectangle2D.Double(center, center, 32D, 32D);
        drawingRectangle = new DrawingRectangle(Color.RED, rectangle);
    }


    public void run() {
        JFrame frame = new JFrame("Animated Square");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel drawingPanel = new DrawingPanel(DRAWING_WIDTH,
                DRAWING_HEIGHT, drawingRectangle);
        frame.add(drawingPanel);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        new Thread(new DrawingAnimation(drawingPanel, drawingRectangle))
                .start();
    }




    public class DrawingPanel extends JPanel {

        private static final long serialVersionUID = 8226587438110549806L;

        private DrawingRectangle drawingRectangle;

        public DrawingPanel(int width, int height,
                            DrawingRectangle drawingRectangle) {
            this.setPreferredSize(new Dimension(width, height));
            this.drawingRectangle = drawingRectangle;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(drawingRectangle.getColor());

            Rectangle2D rectangle = drawingRectangle.getRectangle();
            int x = (int) Math.round(rectangle.getX());
            int y = (int) Math.round(rectangle.getY());
            int width = (int) Math.round(rectangle.getWidth());
            int height = (int) Math.round(rectangle.getHeight());
            g.fillRect(x - width / 2, y - height / 2, width, height);
        }

    }




    public class DrawingAnimation implements Runnable {

        private DrawingPanel drawingPanel;

        private DrawingRectangle drawingRectangle;

        public DrawingAnimation(DrawingPanel drawingPanel,
                                DrawingRectangle drawingRectangle) {
            this.drawingPanel = drawingPanel;
            this.drawingRectangle = drawingRectangle;
        }


        public void run() {
            int xCenter = drawingPanel.getWidth() / 2;
            int yCenter = drawingPanel.getHeight() / 2;
            double radius = drawingPanel.getWidth() / 3;

            for (int degree = 0; degree < 360; degree++) {
                double radians = Math.toRadians((double) degree);
                double x = radius * Math.cos(radians) + xCenter;
                double y = radius * Math.sin(radians) + yCenter;
                drawingRectangle.setRectangleOrigin(x, y);
                repaint();
                sleep(100L);
            }
        }

        private void sleep(long interval) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {

            }
        }

        private void repaint() {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    drawingPanel.repaint();
                }
            });
        }
    }

    public class DrawingRectangle {

        private final Color color;

        private Rectangle2D rectangle;

        public DrawingRectangle(Color color, Rectangle2D rectangle) {
            this.color = color;
            this.rectangle = rectangle;
        }

        public void setRectangleOrigin(double x, double y) {
            rectangle
                    .setRect(x, y, rectangle.getWidth(), rectangle.getHeight());
        }

        public Color getColor() {
            return color;
        }

        public Rectangle2D getRectangle() {
            return rectangle;
        }

    }}
