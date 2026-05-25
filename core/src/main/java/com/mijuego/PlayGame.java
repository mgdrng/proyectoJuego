package com.mijuego;

import com.badlogic.gdx.Gdx; //para acceder al teclado, moouse, pantalla, audio, tiempo
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20; //la grafica, para mostrar los frames del juego
import com.badlogic.gdx.graphics.Texture; //para cargar la imagen del fondo
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // para dibujar imagenes en pantalla

public class PlayGame implements Screen {
    private Main game;
    private SpriteBatch batch;

    private Texture up;
    private Texture down;
    private Texture left;
    private Texture right;


    public PlayGame(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        up = new Texture("flechaUp.png");
        down = new Texture("flechaDown.png");
        left = new Texture("flechaLeft.png");
        right = new Texture("flechaRight.png");
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float size = 45;
        float spacing = 55;

        float screenWidth = Gdx.graphics.getWidth();
        float centerX = screenWidth / 2f;

        float totalWidth = (4 * size) + (3 * spacing);
        float startX = centerX - (totalWidth / 2f);

        float y = 80;

        batch.begin();

        batch.draw(left,  startX + (0 * (size + spacing)), y, size, size);
        batch.draw(down,  startX + (1 * (size + spacing)), y, size, size);
        batch.draw(up,    startX + (2 * (size + spacing)), y, size, size);
        batch.draw(right, startX + (3 * (size + spacing)), y, size, size);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        up.dispose();
        down.dispose();
        left.dispose();
        right.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
