// Trabalho Programação Orientada a Objetos
// Jogo: Donkey Kong
// Integrantes do grupo:
//  Julio César Silva de Sousa - 241024617
//  Luiz Gustavo Nogueira Carvalho - 241025401
//  Thiago Toreto Damaceno de Souza - 241026164

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Foguinho {
    Image[] foguinhoImagem, foguinhoImagemEsq;
    int f = 0, nivelPlataforma = 0;
    double dx, dy, yBase;
    boolean direita = true;
    long ultimoTrocaDirecao;
    boolean subindo = false;
    boolean subindoEscada = false;
    boolean decidiuSubirEscada = false;
    boolean vaiSubirEscada = false;

    // Map para associar o nivel da plataforma com as escadas da plataforma
    static Map<Integer, double[]> escadasPorNivel = new HashMap<>();
    static {
        escadasPorNivel.put(0, new double[] {196, 444});
        escadasPorNivel.put(1, new double[] {224, 85});
        escadasPorNivel.put(2, new double[] {169, 251, 444});
        escadasPorNivel.put(3, new double[] {388, 194, 84});
        escadasPorNivel.put(4, new double[] {225, 444});
    }

    // altura do topo de cada escada
    double[] alturasPlataformasEsc = {330, 340, 274, 280, 210, 212, 219, 147, 154, 158, 93, 99};

    // Construtor
    Foguinho(Image[] foguinhoImagem, int f, double dx, double dy, Image[] foguinhoImagemEsq){
        this.foguinhoImagem = foguinhoImagem;
        this.f = f;
        this.dx = dx;
        this.dy = dy;
        this.yBase = dy;
        this.foguinhoImagemEsq = foguinhoImagemEsq;
        this.ultimoTrocaDirecao = System.currentTimeMillis();
    }

    // método para atualizar o estado do foguinho
    void atualizarF(){
        f = (f + 1) % 2;
        long agora = System.currentTimeMillis();

        if (agora - ultimoTrocaDirecao >= 1000) {
            if (Math.random() < 0.01) {
                direita = !direita;
                ultimoTrocaDirecao = agora;
            }
        }

        if (subindoEscada) {
            dy -= 2;
            int escadaAtual = encontrarEscada();

            if (nivelPlataforma < 5 && dy <= alturasPlataformasEsc[escadaAtual]) {
                yBase = alturasPlataformasEsc[escadaAtual];
                subindoEscada = false;
                decidiuSubirEscada = false;
                vaiSubirEscada = false;

                nivelPlataforma++;
            }
            return;
        } else{
            if (direita) {
            dx += 2;
            if(nivelPlataforma == 0 && dx > 245)
                yBase -= 0.08;
            else if(nivelPlataforma % 2 == 0 && nivelPlataforma != 0)
                yBase -= 0.08;
            else if(nivelPlataforma % 2 == 1)
                yBase += 0.08;
            if (dx > 510 && nivelPlataforma % 2 == 0) {
                direita = false;
                ultimoTrocaDirecao = agora;
            }
            if (dx > 483 && nivelPlataforma % 2 == 1) {
                direita = false;
                ultimoTrocaDirecao = agora;
            }
            } else {
                dx -= 2;
                if(nivelPlataforma == 0 && dx > 245)
                    yBase += 0.08;
                else if(nivelPlataforma % 2 == 0 && nivelPlataforma != 0)
                    yBase += 0.08;
                else if(nivelPlataforma % 2 == 1)
                    yBase -= 0.08;
                if (dx < 35 && nivelPlataforma % 2 == 0) {
                    direita = true;
                    ultimoTrocaDirecao = agora;
                }
                if(dx < 2 && nivelPlataforma % 2 == 1){
                    direita = true;
                    ultimoTrocaDirecao = agora;
                }
            }
        }

        dy = yBase + Math.sin(dx / 5) * 3; // pulo do foguinho

        if (subirEscada()) {
            if (!decidiuSubirEscada) {
                decidiuSubirEscada = true;
                vaiSubirEscada = Math.random() < 0.1; // 10% de chance de subir escada
            }
            if (vaiSubirEscada) {
                subindoEscada = true;
                return;
            }
        } else {
            decidiuSubirEscada = false;
        }
    }

    // verifica se o foguinho está na posição de alguma escada
    boolean subirEscada() {
        double[] escadas = escadasPorNivel.getOrDefault(nivelPlataforma, new double[0]);
        for (double escadaX : escadas) {
            if (Math.abs(dx - escadaX) < 10 && direita) {
                return true;
            }
            if (Math.abs(dx - escadaX) < 1 && !direita) {
                return true;
            }
        }
        return false;
    }

    // método para encontrar qual escada o foguinho está subindo
    int encontrarEscada() {
        double[] escadas = escadasPorNivel.getOrDefault(nivelPlataforma, new double[0]);
        int maisProxima = -1;
        double menorDistancia = Double.MAX_VALUE;

        for (double escadaX : escadas) {
            double distancia = Math.abs(dx - escadaX);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                if(escadaX == 196.0)
                    maisProxima = 0;
                if(escadaX == 444.0 && nivelPlataforma == 0)
                    maisProxima = 1;
                if(escadaX == 224.0)
                    maisProxima = 2;
                if(escadaX == 85.0)
                    maisProxima = 3;
                if(escadaX == 169.0)
                    maisProxima = 4;
                if(escadaX == 251.0)
                    maisProxima = 5;
                if(escadaX == 444.0 && nivelPlataforma == 2)
                    maisProxima = 6;
                if(escadaX == 388.0)
                    maisProxima = 7;
                if(escadaX == 194.0)
                    maisProxima = 8;
                if(escadaX == 84.0)
                    maisProxima = 9;
                if(escadaX == 225.0)
                    maisProxima = 10;
                if(escadaX == 444.0 && nivelPlataforma == 4)
                    maisProxima = 11;
            }
        }
        return maisProxima;
    }

    void desenharF(Graphics g) {
        if(direita)
            g.drawImage(foguinhoImagem[f], (int) dx, (int) dy, 35, 35, null);
        else
            g.drawImage(foguinhoImagemEsq[f], (int) dx, (int) dy, 35, 35, null);
    }
}
