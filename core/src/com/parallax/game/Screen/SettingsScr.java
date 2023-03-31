package com.parallax.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parallax.game.Main;

public class SettingsScr implements Screen {

    private Main main;
    private Batch batch;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    public SettingsScr(Main main) {

        this.main = main;
        this.batch = main.batch;

//        ---------------------------- Инициализация камеры и текстур ---------------------------

        skin = new Skin();
        skin.addRegions(new TextureAtlas("UI/Interface.atlas"));
        skin.load(Gdx.files.internal("UI/Interface.json"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);

//        TODO Добавить настройки: освещение, контраст, расположение джостиков

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
}
