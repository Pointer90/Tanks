package com.parallax.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parallax.game.Main;

public class MenuScr implements Screen {

    private Main main;
    private Batch batch;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private TextButton startBtn;
    private TextButton settingBtn;

    public MenuScr(final Main main) {
        this.main = main;
        this.batch = main.batch;

//        ---------------------------- Инициализация камеры и текстур ---------------------------

        skin = new Skin();
        skin.addRegions(new TextureAtlas("UI/Interface.atlas"));
        skin.load(Gdx.files.internal("UI/Interface.json"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);

//        ---------------------------- Инициализация кнопок -------------------------------------

        float widthBtn = Gdx.graphics.getWidth();
        float heightBtn = Gdx.graphics.getHeight();


//        ------- Кнопка старта -------
        startBtn = new TextButton("Start", skin);
        startBtn.setSize(widthBtn / 5, heightBtn / 10);
        startBtn.setPosition( widthBtn / 2 - startBtn.getWidth(), heightBtn * 2 / 3);


//        ------- Кнопка настроек -------
        settingBtn = new TextButton("Settings", skin);
        settingBtn.setSize(widthBtn / 5, heightBtn / 10);
        settingBtn.setPosition(widthBtn / 2 - settingBtn.getWidth(), heightBtn / 3);



//        ------- Добавление акторов -------
       stage.addActor(startBtn);
       stage.addActor(settingBtn);


//        ---------------------------- Инициализация слушателей ---------------------------------


//        ------- Слушатель кнопки старта -------
        startBtn.addListener(new InputListener(){

           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
           }

           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               main.setScreen(new GameScr(main));
           }
       });


//        ------- Слушатель кнопки настроек -------
        settingBtn.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                main.setScreen(new Test(main));
            }
        });
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
        startBtn.draw(batch, 1);
        settingBtn.draw(batch, 1);

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
        stage.dispose();
        skin.dispose();
        batch.dispose();
        this.dispose();
    }
}
