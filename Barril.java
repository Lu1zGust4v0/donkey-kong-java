// Trabalho Programação Orientada a Objetos
// Jogo: Donkey Kong
// Integrantes do grupo:
//  Julio César Silva de Sousa - 241024617
//  Luiz Gustavo Nogueira Carvalho - 241025401
//  Thiago Toreto Damaceno de Souza - 241026164

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class Barril{
    double dx, dy;
    int j = 0, e = 0;
    boolean direita;
    boolean caindo;
    boolean descendoEscada;
    boolean decidiuDescerEscada = false;
    boolean vaiDescerEscada = false;
    int nivelPlataforma;
    double[] alturasPlataformas;
    Image[] imagens;
    Image[] imagemBarrilFrente;
    static Map<Integer, double[]> escadasPorNivel = new HashMap<>();
    static {
        escadasPorNivel.put(0, new double[] {225, 444});
        escadasPorNivel.put(1, new double[] {388, 194, 84});
        escadasPorNivel.put(2, new double[] {169, 251, 444});
        escadasPorNivel.put(3, new double[] {224, 85});
        escadasPorNivel.put(4, new double[] {196, 444});
    }

    double[] alturasPlataformasEsc = {168, 158, 230, 224, 220, 291, 288, 280, 345, 342, 406, 400};

    Barril(double dx, double dy, boolean direita, double[] alturasPlataformas, Image[] imagens, Image[] imagemBarrilFrente) {
        this.dx = dx;
        this.dy = dy;
        this.direita = direita;
        this.caindo = false;
        this.descendoEscada = false;
        this.nivelPlataforma = 0;
        this.alturasPlataformas = alturasPlataformas;
        this.imagens = imagens;
        this.imagemBarrilFrente = imagemBarrilFrente;
    }

    void atualizar() {
        if(!vaiDescerEscada)
            j = (j + 1) % 4;
        else
            e = (e + 1) % 2;

        if (descendoEscada) {
            dy += 4;
            int escadaAtual = encontrarEscada();

            if (nivelPlataforma < alturasPlataformas.length && dy >= alturasPlataformasEsc[escadaAtual]) {
                descendoEscada = false;
                decidiuDescerEscada = false;
                vaiDescerEscada = false;

                if (nivelPlataforma % 2 == 0) {
                    direita = false;
                } else {
                    direita = true;
                }

                nivelPlataforma++;
            }
            return;
        }
        if (caindo) {
            dy += 5;
            if (nivelPlataforma % 2 == 0) {
                dx += 1;
            } else {
                dx -= 1;
            }
            if (dy >= alturasPlataformas[nivelPlataforma]) {
                caindo = false;
                nivelPlataforma++;
            }
        } else{
            if (direita) {
                dx += 5;
                dy += 0.16;
                if (dx > 495 && dx < 505) {
                    direita = false;
                    caindo = true;
                }
            } else {
                dx -= 5;
                if(!(nivelPlataforma==5 && dx < 245))
                    dy += 0.16;
                if (dx > 29 && dx < 35) {
                    direita = true;
                    caindo = true;
                }
            }
        }

        if (descerEscada()) {
            if (!decidiuDescerEscada) {
                decidiuDescerEscada = true;
                vaiDescerEscada = Math.random() < 0.4;
            }
            if (vaiDescerEscada) {
                descendoEscada = true;
                return;
            }
        } else {
            decidiuDescerEscada = false;
        }
        
    }

    boolean descerEscada() {
        double[] escadas = escadasPorNivel.getOrDefault(nivelPlataforma, new double[0]);
        for (double escadaX : escadas) {
            if (Math.abs(dx - escadaX) < 3) {
                return true;
            }
        }
        return false;
    }

    int encontrarEscada() {
        double[] escadas = escadasPorNivel.getOrDefault(nivelPlataforma, new double[0]);
        int maisProxima = -1;
        double menorDistancia = Double.MAX_VALUE;

        for (double escadaX : escadas) {
            double distancia = Math.abs(dx - escadaX);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                if(escadaX == 225.0)
                    maisProxima = 0;
                if(escadaX == 444.0 && nivelPlataforma == 0)
                    maisProxima = 1;
                if(escadaX == 388.0)
                    maisProxima = 2;
                if(escadaX == 194.0)
                    maisProxima = 3;
                if(escadaX == 84.0)
                    maisProxima = 4;
                if(escadaX == 169.0)
                    maisProxima = 5;
                if(escadaX == 251.0)
                    maisProxima = 6;
                if(escadaX == 444.0 && nivelPlataforma == 2)
                    maisProxima = 7;
                if(escadaX == 224.0)
                    maisProxima = 8;
                if(escadaX == 85.0)
                    maisProxima = 9;
                if(escadaX == 196.0)
                    maisProxima = 10;
                if(escadaX == 444.0 && nivelPlataforma == 4)
                    maisProxima = 11;
            }
        }
        return maisProxima;
    }

    void desenhar(Graphics g) {
        if(!vaiDescerEscada)
            g.drawImage(imagens[j], (int) dx, (int) dy, 25, 25, null);
        else
            g.drawImage(imagemBarrilFrente[e], (int) dx, (int) dy, 25, 25, null);
    }
}
