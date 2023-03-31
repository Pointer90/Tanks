package com.parallax.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.parallax.game.Main;

public class Test implements Screen {

    private Main main;
    private Batch batch;
    private Stage stage;
    private Viewport viewport;

    public ModelBatch modelBatch;
    public Environment env;
    public CameraInputController camcontrl;
    public PerspectiveCamera cam;
    public AssetManager assets;
    public boolean loading;
    public Array<ModelInstance> instances = new Array<ModelInstance>();


    public Test(final Main main) {
        this.main = main;
        this.batch = main.batch;

//        ---------------------------- Инициализация камеры и освещения ---------------------------

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,0.4f,0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f,0.8f,0.8f, -1f, -0.8f, -0.2f));

        camcontrl = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camcontrl);

//        ---------------------------- Инициализация танка ---------------------------

        modelBatch = new ModelBatch();
        assets = new AssetManager();
        assets.load("Models/Tank.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model ship = assets.get("Models/Tank.obj", Model.class);
        for (float x = -5f; x <= 5f; x += 2f) {
            for (float z = -5f; z <= 5f; z += 2f) {
                ModelInstance shipInstance = new ModelInstance(ship);
                shipInstance.transform.setToTranslation(x, 0, z);
                instances.add(shipInstance);
            }
        }
        loading = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {



//        -------- Обновление кадра --------
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (assets.update()) doneLoading();
        camcontrl.update();

        modelBatch.begin(cam);
        modelBatch.render(instances, env);
        modelBatch.end();
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

    }
}
