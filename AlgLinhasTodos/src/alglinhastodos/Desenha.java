//Autor marcio luis
//github: marcio661
package alglinhastodos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Desenha extends JFrame {
private int option;
private Color fillColor; // variável para armazenar a cor selecionada
private final String[] mainOptions = {"Linhas", "Poligonos", "Circulos"};
private final String[] linesOptions = {"Analítico", "DDA", "Bresenham"};
private final String[] polygonsOptions = {"Varredura", "BoundaryFill", "Análise Geométrica"};
private final String[] circlesOptions = {"Paramétrica", "Incremental com Simetria", "Bresenham"};

    public Desenha() {
    int mainSelectedOption = JOptionPane.showOptionDialog(
        null,
        "Escolha uma primitiva:",
        "Primitiva",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        mainOptions,
        mainOptions[0]
    );

    switch (mainSelectedOption) {
        case 0:
            int linesSelectedOption = JOptionPane.showOptionDialog(
                null,
                "Escolha um método:",
                "Linhas",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                linesOptions,
                linesOptions[0]
            );
            option = linesSelectedOption + 1;
            break;
        case 1:
            int polygonsSelectedOption = JOptionPane.showOptionDialog(
                null,
                "Escolha um método:",
                "Poligonos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                polygonsOptions,
                polygonsOptions[0]
            );
            option = polygonsSelectedOption + linesOptions.length + 1;
            break;
        case 2:
            int circlesSelectedOption = JOptionPane.showOptionDialog(
                null,
                "Escolha um método:",
                "Circulos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                circlesOptions,
                circlesOptions[0]
            );
            option = circlesSelectedOption + linesOptions.length + polygonsOptions.length + 1;
            break;
    }

    // Adiciona uma caixa de diálogo para escolha da cor
    Object[] colors = {"Vermelho", "Verde", "Azul"};
    int selectedColor = JOptionPane.showOptionDialog(
        null,
        "Escolha uma cor:",
        "Cor",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        colors,
        colors[0]
    );
    // Armazena a cor selecionada na variável fillColor
    switch (selectedColor) {
        case 0:
            fillColor = Color.RED;
            break;
        case 1:
            fillColor = Color.GREEN;
            break;
        case 2:
            fillColor = Color.BLUE;
            break;
    }

        this.setTitle("Trabalho 2");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

@Override
public void paint(Graphics g) {
    switch (option) {
        case 1:
            desenhaAnalitico(g);
            break;
        case 2:
            desenhaDDA(g);
            break;
        case 3:
            desenhaBres(g);
            break;
        case 4:
            desenhaVarredura(g);
            break;
        case 5:
            desenhaBoundaryFill(g);
            break;
        case 6:
            desenhaAnaliseGeometrica(g);
            break;
        case 7:
            desenhaParamétrica(g);
            break;
        case 8:
            desenhaIncrementalComSimetria(g);
            break;
        case 9:
            desenhaCircBresenham(g);
            break;
    }
}
private void desenhaCircBresenham(Graphics g) {
    int xc = 200;  // coordenadas do centro do círculo
    int yc = 200;
    int r = 100;   // raio do círculo

    int x = 0;
    int y = r;
    int d = 3 - 2 * r;

    drawCircle(xc, yc, x, y, g);

    while (y >= x) {
        x++;

        if (d > 0) {
            y--;
            d = d + 4 * (x - y) + 10;
        } else {
            d = d + 4 * x + 6;
        }

        drawCircle(xc, yc, x, y, g);
    }
}

public void drawCircle(int xc, int yc, int x, int y, Graphics g) {
    // Adiciona os pontos de intersecção com a borda do círculo à lista
    List<Integer> points = new ArrayList<>();
    points.add(xc + x);
    points.add(xc - x);

    points.add(xc + y);
    points.add(xc - y);

    // Pinta as linhas horizontais que cruzam o círculo com a cor selecionada
    for (int i = yc - y; i <= yc + y; i++) {
        // Encontra os pontos de intersecção da linha com a borda do círculo
        List<Integer> intersections = new ArrayList<>();
        for (int j = 0; j < points.size(); j++) {
            int xIntersect = points.get(j);
            int yIntersect = i;
            if (Math.pow(xIntersect - xc, 2) + Math.pow(yIntersect - yc, 2) <= Math.pow(y, 2)) {
                intersections.add(xIntersect);
            }
        }

        // Pinta a linha entre os pontos de intersecção
        if (intersections.size() > 1) {
            int x1 = intersections.get(0);
            int x2 = intersections.get(intersections.size() - 1);
            g.setColor(fillColor);
            g.drawLine(x1, i, x2, i);
            g.setColor(Color.BLACK);
        }
    }

    // Adiciona os pontos de intersecção com a borda do círculo à lista
    points.add(xc + y);
    points.add(xc - y);

    points.add(xc + x);
    points.add(xc - x);
}

private void desenhaIncrementalComSimetria(Graphics g) {
    int xc = 200; // coordenadas do centro do círculo
    int yc = 200;
    int r = 100; // raio do círculo

    int x = 0;
    int y = r;
    int d = 1 - r;

    while (x <= y) {
        desenhaSimetria(xc, yc, x, y, g);
        x++;
        if (d < 0) {
            d += 2 * x + 1;
        } else {
            y--;
            d += 2 * (x - y) + 1;
        }
    }
}

private void desenhaSimetria(int xc, int yc, int x, int y, Graphics g) {
    g.drawLine(xc + x, yc + y, xc + x, yc + y);
    g.drawLine(xc - x, yc + y, xc - x, yc + y);
    g.drawLine(xc + x, yc - y, xc + x, yc - y);
    g.drawLine(xc - x, yc - y, xc - x, yc - y);
    g.drawLine(xc + y, yc + x, xc + y, yc + x);
    g.drawLine(xc - y, yc + x, xc - y, yc + x);
    g.drawLine(xc + y, yc - x, xc + y, yc - x);
    g.drawLine(xc - y, yc - x, xc - y, yc - x);
}
private void desenhaParamétrica(Graphics g) {
int xc = 200; // coordenadas do centro do círculo
int yc = 200;
int r = 100; // raio do círculo
// desenha pontos na circunferência do círculo usando a parametrização
for (double theta = 0; theta < 2 * Math.PI; theta += 0.01) {
    int x = (int) (xc + r * Math.cos(theta));
    int y = (int) (yc + r * Math.sin(theta));
    g.drawLine(x, y, x, y);
}
}
 private void desenhaVarredura(Graphics g) {
int x1 = 100, y1 = 100; // coordenadas do primeiro vértice
int x2 = 200, y2 = 200; // coordenadas do segundo vértice
int x3 = 300, y3 = 150; // coordenadas do terceiro vértice
// ordena os vértices de acordo com suas coordenadas y
if (y1 > y2) {
    int tempX = x1;
    int tempY = y1;
    x1 = x2;
    y1 = y2;
    x2 = tempX;
    y2 = tempY;
}
if (y1 > y3) {
    int tempX = x1;
    int tempY = y1;
    x1 = x3;
    y1 = y3;
    x3 = tempX;
    y3 = tempY;
}
if (y2 > y3) {
    int tempX = x2;
    int tempY = y2;
    x2 = x3;
    y2 = y3;
    x3 = tempX;
    y3 = tempY;
}

// inicializa as arestas do triângulo
int[][] edges = {{x1, y1, x2, y2}, {x1, y1, x3, y3}, {x2, y2, x3, y3}};
double[] slopes = new double[3];
int[] yMin = new int[3];
int[] yMax = new int[3];
for (int i = 0; i < 3; i++) {
    int x1i = edges[i][0];
    int y1i = edges[i][1];
    int x2i = edges[i][2];
    int y2i = edges[i][3];
    slopes[i] = (double) (x2i - x1i)/ (y2i - y1i); // calcula a inclinação da aresta
yMin[i] = Math.min(y1i, y2i); // guarda a coordenada y mínima
yMax[i] = Math.max(y1i, y2i); // guarda a coordenada y máxima
}

// percorre cada linha horizontal varrendo o triângulo
for (int y = y1; y <= y3; y++) {
// verifica se a linha horizontal cruza cada uma das arestas do triângulo
for (int i = 0; i < 3; i++) {
if (y >= yMin[i] && y <= yMax[i]) {
// calcula a coordenada x correspondente ao ponto de interseção com a linha horizontal
double x = (y - edges[i][1]) * slopes[i] + edges[i][0];
// desenha o ponto na coordenada (x, y)
g.drawLine((int) x, y, (int) x, y);
}
}
}
}       

private void desenhaBoundaryFill(Graphics g) {
// Desenha o triângulo
int[] xPoints = {100, 250, 175};
int[] yPoints = {100, 100, 250};
int nPoints = 3;
g.setColor(Color.BLACK);
g.drawPolygon(xPoints, yPoints, nPoints);
// Escolhe uma cor para preencher o triângulo
Color fillColor = Color.RED;

// Escolhe um ponto interno ao triângulo
int x = 150;
int y = 150;

// Chama a função de preenchimento
boundaryFill(x, y, fillColor, g);
}

public void boundaryFill(int x, int y, Color fillColor, Graphics g) {
// Pega a cor atual do pixel
Color currentColor = new Color(g.getColor().getRGB());
// Se a cor atual for diferente da cor de preenchimento e
// da cor da borda do triângulo, preenche o pixel com a nova cor
if (!currentColor.equals(fillColor) && !currentColor.equals(Color.BLACK)) {
    g.setColor(fillColor);
    g.drawLine(x, y, x, y);

    // Chama a função de preenchimento recursivamente para os pixels vizinhos
    boundaryFill(x + 1, y, fillColor, g);
    boundaryFill(x - 1, y, fillColor, g);
    boundaryFill(x, y + 1, fillColor, g);
    boundaryFill(x, y - 1, fillColor, g);
}
}

private void desenhaAnaliseGeometrica(Graphics g) {
    int[] x = {100, 150, 200};  // coordenadas x dos vértices
    int[] y = {200, 100, 200};  // coordenadas y dos vértices
    
    g.drawPolygon(x, y, 3);     // desenha o triângulo
}


    private void desenhaBres(Graphics g) {
        g.drawString("Bresenham", 50, 50);
        g.setColor(Color.red);
        algBres(g, 50, 50, 250, 250);
        g.setColor(Color.blue);
        algBres(g, 50, 50, 250, 50);
        g.setColor(Color.green);
        algBres(g, 50, 50, 50, 250);
        g.setColor(Color.gray);
        algBres(g, 50, 50, 250, 150);
        g.setColor(Color.yellow);
        algBres(g, 50, 50, 150, 250);
    }

    private void desenhaAnalitico(Graphics g) {
        g.drawString("Analitico", 50, 50);
        g.setColor(Color.red);
        algAnalitico(g, 50, 50, 250, 250);
        g.setColor(Color.blue);
        algAnalitico(g, 50, 50, 250, 50);
        g.setColor(Color.green);
        algAnalitico(g, 50, 50, 50, 250);
        g.setColor(Color.gray);
        algAnalitico(g, 50, 50, 250, 150);
        g.setColor(Color.yellow);
        algAnalitico(g, 50, 50, 150, 250);
    }

    private void desenhaDDA(Graphics g) {
        g.drawString("DDA", 50, 50);
        g.setColor(Color.blue);
        algDDA(g,50,50,250,50);
        g.setColor(Color.green);
        algDDA(g,50,50,50,250);
        g.setColor(Color.gray);
        algDDA(g,50,50,250,150);
        g.setColor(Color.yellow);
        algDDA(g,50,50,150,250);
     }

    public void algDDA(Graphics g, int xi, int yi, int xf, int yf){
    int steps, dx, dy;
    float x=xi, y=yi, incX, incY;
    
    dx=xf-xi;
    dy=yf-yi;
    
    if (Math.abs(dx)>Math.abs(dy)){
        steps = Math.abs(dx); 
        incX = 1; 
        incY=(float) dy/dx;
    }
    else{
    steps = Math.abs(dy); 
    incY = 1; 
    incX=(float) dx/dy;
    }
    
    System.out.println(steps + "-" + dx + "-" + dy+ "-"+"-" +incX+ incY);    
    for (int i=0;i<steps;i++){
        x = x + incX;
        y = y+ incY;
       putPixel(g, Math.round(x), Math.round(y)); 
    } 
    }   
   private void algAnalitico(Graphics g, int xi, int yi, int xf, int yf) {
    int dx = xf - xi;
    int dy = yf - yi;
    int steps = Math.max(Math.abs(dx), Math.abs(dy));
    float x = xi;
    float y = yi;
    float xIncrement = (float)dx / steps;
    float yIncrement = (float)dy / steps;
    for (int i = 0; i <= steps; i++) {
        putPixel(g, Math.round(x), Math.round(y));
        x += xIncrement;
        y += yIncrement;
    }
}

private void algBres(Graphics g, int x1, int y1, int x2, int y2) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int x = x1;
    int y = y1;
    int incX = (x1 < x2) ? 1 : -1;
    int incY = (y1 < y2) ? 1 : -1;
    int e = 0;
    putPixel(g, x, y);
    if (dx > dy) {
        e = 2 * dy - dx;
        for (int i = 0; i < dx; i++) {
            x += incX;
            if (e >= 0) {
                y += incY;
                e -= 2 * dx;
            }
            e += 2 * dy;
            putPixel(g, x, y);
        }
    } else {
        e = 2 * dx - dy;
        for (int i = 0; i < dy; i++) {
            y += incY;
            if (e >= 0) {
                x += incX;
                e -= 2 * dy;
            }
            e += 2 * dx;
            putPixel(g, x, y);
        }
    }
}


    public void putPixel(Graphics g, int x, int y){
        g.drawLine(x, y, x, y);
    }

   
   
public void run() {
    int opcao = 1;
    while (true) {
        try {
            atualizaTela(opcao);
            opcao = (opcao % 3) + 1;
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(Desenha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    public void atualizaTela(int opcao) {
    option = opcao;
    repaint();
}

    private void algBres(Graphics g, int i, int i0, int i2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void algAnalitico(Graphics g, int i, int i1, int i2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getPixel(Graphics g, int i, int y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}