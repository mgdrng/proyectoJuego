package com.mijuego;

import com.badlogic.gdx.Gdx; //para acceder al teclado, mouse, pantalla, audio, tiempo.
import com.badlogic.gdx.graphics.Texture; //para cargar la imagen del fondo.
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // para dibujar imagenes en pantalla.
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; // para dibujar rectangulos.
import com.badlogic.gdx.utils.Array; // lista dinámica de libgdx.
import java.util.Random; // para elegir carril aleatorio.

public class PlayGame extends BaseScreen{
    private Main juego;
    private SpriteBatch lote;
    private ShapeRenderer dibujoPaneles;
    private Texture flechaArriba;
    private Texture flechaAbajo;
    private Texture flechaIzquierda;
    private Texture flechaDerecha;

    private Array<Flechas> flechas = new Array<>();
    private float spawnTimer = 0f;
    private Random rng = new Random();

    private float tamanio = 45f;
    private float spacing = 55f;
    private float inicioX;
    private float golpeY  = 80f;
    private float panelW  = 65f;
    private float panelX  = 10f;

    public PlayGame(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        lote          = new SpriteBatch();
        dibujoPaneles = new ShapeRenderer();
        flechaArriba    = new Texture("flechaUp.png");
        flechaAbajo     = new Texture("flechaDown.png");
        flechaIzquierda = new Texture("flechaLeft.png");
        flechaDerecha   = new Texture("flechaRight.png");

        float screenWidth = Gdx.graphics.getWidth();
        float centerX     = screenWidth / 2f;
        float totalCarriles  = (4 * tamanio) + (3 * spacing);
        inicioX = centerX - (totalCarriles / 2f);
    }

    @Override
    protected void update(float tiempoFrame) {
        // Spawn de flechas
        spawnTimer += tiempoFrame;
        if (spawnTimer >= 0.8f) {
            spawnTimer = 0f;
            int carril = rng.nextInt(4);
            flechas.add(new Flechas(carril, carrilX(carril), Gdx.graphics.getHeight()));
        }

        // Mover flechas hacia abajo
        for (int i = flechas.size - 1; i >= 0; i--) {
            Flechas a = flechas.get(i);
            a.mover(220f * tiempoFrame);
            if (a.getY() + tamanio < 0) flechas.removeIndex(i);
        }
    }

    @Override
    protected void draw(float tiempoFrame) {
        // Dibujar paneles de carril
        dibujoPaneles.begin(ShapeRenderer.ShapeType.Filled);
        dibujoPaneles.setColor(0.12f, 0.12f, 0.12f, 1f);
        for (int carril = 0; carril < 4; carril++) {
            dibujoPaneles.rect(carrilX(carril) - panelX, 0, panelW, Gdx.graphics.getHeight());
        }
        dibujoPaneles.end();

        lote.begin();

        // Flechas cayendo
        for (int i = 0; i < flechas.size; i++) {

            Flechas flecha = flechas.get(i);
            Texture textura = texFor(flecha.getTipo());

            lote.draw(textura, flecha.getX(), flecha.getY(), tamanio, tamanio);
        }

        // Flechas estáticas (zona de golpe)
        lote.draw(flechaIzquierda, carrilX(0), golpeY, tamanio, tamanio);
        lote.draw(flechaAbajo,     carrilX(1), golpeY, tamanio, tamanio);
        lote.draw(flechaArriba,    carrilX(2), golpeY, tamanio, tamanio);
        lote.draw(flechaDerecha,   carrilX(3), golpeY, tamanio, tamanio);

        lote.end();
    }

    @Override
    public void dispose() {
        lote.dispose();
        dibujoPaneles.dispose();
        flechaArriba.dispose();
        flechaAbajo.dispose();
        flechaIzquierda.dispose();
        flechaDerecha.dispose();
    }

    private float carrilX(int carril) {
        return inicioX + carril * (tamanio + spacing);
    }

    private Texture texFor(int tipo) {
        switch (tipo) {
            case 0:  return flechaIzquierda;
            case 1:  return flechaAbajo;
            case 2:  return flechaArriba;
            default: return flechaDerecha;
        }
    }
}
