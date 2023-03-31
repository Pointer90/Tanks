package com.parallax.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parallax.game.Main;
import com.sun.tools.javac.comp.Todo;

public class GameScr implements Screen {

    private Main main;
    private Batch batch;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Touchpad joystickBody;
    private Touchpad joystickHead;
    private ImageButton shotBtn;

    public GameScr(Main main) {
        this.main = main;
        this.batch = main.batch;

//        ---------------------------- Инициализация камеры и текстур ---------------------------

        skin = new Skin();
        skin.addRegions(new TextureAtlas("UI/Interface.atlas"));
        skin.load(Gdx.files.internal("UI/Interface.json"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);

//        ---------------------------- Инициализация контроллеров -------------------------------

        float sizeJoystick = Gdx.graphics.getHeight() / 3;
        float joyBodyX = Gdx.graphics.getWidth() / 2 + sizeJoystick * 1.5f;
        float joyBodeY = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() + 100;
        float joyHeadX = sizeJoystick / 2;
        float joyHeadY = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() + 100;

        float sizeBtn = Gdx.graphics.getHeight() / 5;
        float shotBtnX = joyBodyX - sizeBtn * 1.5f;
        float shotBtnY = joyHeadY;


//        ------------ Инициализация Joystick ------------
        joystickBody = new Touchpad(25, skin);
        joystickBody.setSize(sizeJoystick, sizeJoystick);
        joystickBody.setPosition(joyBodyX, joyBodeY);


        joystickHead = new Touchpad(25, skin);
        joystickHead.setSize(sizeJoystick, sizeJoystick);
        joystickHead.setPosition(joyHeadX, joyHeadY);


//        ------------ Инициализация кнопок ------------
        shotBtn = new ImageButton(skin);
        shotBtn.setSize( sizeBtn, sizeBtn);
        shotBtn.setPosition(shotBtnX, shotBtnY);


//        ------- Добавление акторов -------
        stage.addActor(joystickBody);
        stage.addActor(joystickHead);
        stage.addActor(shotBtn);

//        TODO Сделать загрузку танка
//        TODO Связать джостики с моделью
    }

    @Override
    public void show() {

//        --------- Инициализация главного процессора ввода ---------
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

//        -------- Обновление данный актеров и разрешения экрана --------
        stage.act(delta);
        viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());


//        -------- Обновление кадра --------
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//        -------- Рисование объектов --------
        batch.begin();

        stage.draw();

        joystickBody.draw(batch, 1);
        joystickHead.draw(batch, 1);
        shotBtn.draw(batch, 1);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

//        Проверка размера окна
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

//        Освобождение памяти
        skin.dispose();
        batch.dispose();
        stage.dispose();
        this.dispose();
    }
}
