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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
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

    private Tank tankTest;
    BitmapFont font = new BitmapFont();
    String str1;
    String str2;

    public GameScr(Main main) {
        this.main = main;
        this.batch = main.batch;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        modelBatch = new ModelBatch();
        camController = new CameraInputController(cam);

        initControlls();
        initCamEnv();
        initAssets();

        tank = new Tank(5, 0, -5);
        tankTest = new Tank(0, 0, 0);
        font.setColor(Color.BLACK);
    }

    @Override
    public void show() {

//        --------- Инициализация главного процессора ввода ---------
        Gdx.input.setInputProcessor(stage);
    }

    private void update(float delta){
        if (loading && assets.update()) doneLoading();

        if (!loading && assets.update()){

            if (!tank.isCollision(tankTest)){
                tank.moveBody(joystickBody, joystickHead, false);
            } else {
                tank.moveBody(joystickBody, joystickHead, true);
            }
        }
    }

    @Override
    public void render(float delta) {

//        -------- Движение модели --------
        update(delta);
        str1 = "x: " + Float.toString(0) + "  z: " + Float.toString(0);
        str2 = Float.toString(tank.health) + "   " + Float.toString(0);

//        -------- Обновление данный актеров и разрешения экрана --------
        stage.act(delta);
        viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());


        main.cleanScreen();

//        -------- Рисование объектов --------
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();

//        -------- Рисование UI --------
        batch.begin();
        stage.draw();
        joystickBody.draw(batch, 1);
        joystickHead.draw(batch, 1);
        shotBtn.draw(batch, 1);
        font.draw(batch, str1,50, 50);
        font.draw(batch, str2,50, 25);
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
        cam.position.set(0, 15, -10);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    private void initAssets(){

//        --------- Инициализация загрузчиков моделей ---------

        assets = new AssetManager();
        assets.load("Models/Tank.obj", Model.class);
        assets.load("Models/Head.obj", Model.class);
        loading = true;
    }

    private void doneLoading(){
        tank.loadModel(assets);
        tankTest.loadModel(assets);

        instances.add(tank.getBody());
        instances.add(tank.getHead());

        instances.add(tankTest.getBody());
        instances.add(tankTest.getHead());

        loading = false;
    }
}
