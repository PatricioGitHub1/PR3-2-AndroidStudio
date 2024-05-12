package com.patricio.animation1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GdxAnimation extends Game {
	Screen screen;
	Texture walkSheet;
	RedAnimator redSprite;
	OrthographicCamera camera;
	public int GAME_WIDTH = 1920;
	public int GAME_HEIGHT = 1080;


	@Override
	public void create () {
		//img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
		walkSheet = new Texture(Gdx.files.internal("redgold.png"));
		redSprite = new RedAnimator(walkSheet);

		screen = new GameScreen(this);
		this.setScreen(screen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		redSprite.spriteBatch.dispose();
	}
}
