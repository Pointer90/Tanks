package com.parallax.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parallax.game.Main;
import com.parallax.game.Models.Tank;
import com.sun.tools.javac.comp.Todo;

public class GameScr implements Screen {

    private Main main;
    private Batch batch;
    private ModelBatch modelBatch;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Touchpad joystickBody;
    private Touchpad joystickHead;
    private ImageButton shotBtn;
    private Environment environment;
    private PerspectiveCamera cam;
    private CameraInputController camController;
    private AssetManager assets;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private boolean loading;
    private Tank tank;

    private BitmapFont font;
    private String str;

    public GameScr(Main main) {
        this.main = main;
        this.batch = main.batch;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        modelBatch = new ModelBatch();
        camController = new CameraInputController(cam);

        font = new BitmapFont();
        font.setColor(Color.RED);

        initControlls();
        initCamEnv();
        initAssets();

        tank = new Tank(0, 0, 0);

//        TODO Связать джостики с моделью
    }

    @Override
    public void show() {

//        --------- Инициализация главного процессора ввода ---------
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        str = Float.toString(tank.position.x) + " | " + Float.toString(tank.position.y);

        if (loading && assets.update()) doneLoading();
        if (loading) tank.update(joystickBody.getHeight() + joystickBody.getKnobX(), joystickBody.getHeight() + joystickBody.getKnobY(), 0, delta);

//        -------- Обновление данный актеров и разрешения экрана --------
        stage.act(delta);
        viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());


//        -------- Обновление кадра --------
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


//        -------- Рисование объектов --------
        batch.begin();
        stage.draw();
        joystickBody.draw(batch, 1);
        joystickHead.draw(batch, 1);
        shotBtn.draw(batch, 1);


        font.draw(batch, str, 100, 100);
        batch.end();

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
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

//        --- Освобождение памяти ---

        skin.dispose();
        batch.dispose();
        modelBatch.dispose();
        assets.dispose();
        instances.clear();
        stage.dispose();
        this.dispose();
    }

    private void initControlls(){
//        ---------------------------- Инициализация текстур кнопок -------------------------------

        skin = new Skin();
        skin.addRegions(new TextureAtlas("UI/Interface.atlas"));
        skin.load(Gdx.files.internal("UI/Interface.json"));

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
    }

    private void initCamEnv() {
//            --------- Инициализация камеры и освещения ---------


//        --- Освещение ---

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.8f,-1f, -0.2f));

//        --- Камера ---

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(5f, 5f, 5f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    private void initAssets(){

//        --------- Инициализация загрузчиков моделей ---------

        assets = new AssetManager();
        assets.load("Models/Tank.obj", Model.class);
        loading = true;
    }

    private void doneLoading(){
        tank.loadModel(assets.get("Models/Tank.obj", Model.class));
        instances.add(tank.getBody());
        loading = false;
    }
}
