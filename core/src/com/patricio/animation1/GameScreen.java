package com.patricio.animation1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements com.badlogic.gdx.Screen {
    GdxAnimation game;
    final static int IDLE = 0, UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
    int lastMovement = DOWN;
    Texture background;
    TextureRegion frameKey,backgroundRegion;
    OrthographicCamera camera;
    Rectangle up, down, left, right;
    float stateTime;
    int posX, posY;

    public float spriteHeight = 300;
    public float spriteWidth = 300;
    public float xSpritePos;
    public float ySpritePos;
    GameScreen(GdxAnimation game)  {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.GAME_WIDTH, game.GAME_HEIGHT);
        this.game = game;
        background = new Texture(Gdx.files.internal("grass.png"));
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        backgroundRegion = new TextureRegion(background);

        xSpritePos = (game.GAME_WIDTH - spriteWidth) / 2;
        ySpritePos = (game.GAME_HEIGHT - spriteHeight) / 2;

        up = new Rectangle(0, (float) (game.GAME_HEIGHT * 2) / 3, game.GAME_WIDTH, (float) game.GAME_HEIGHT / 3);
        down = new Rectangle(0, 0, game.GAME_WIDTH, (float) game.GAME_HEIGHT / 3);
        left = new Rectangle(0, 0, (float) game.GAME_WIDTH / 3, game.GAME_HEIGHT);
        right = new Rectangle((float) (game.GAME_WIDTH * 2) / 3, 0, (float) game.GAME_WIDTH / 3, game.GAME_HEIGHT);
    }

    private void updateLastMovement(int movement) {
        if (movement != IDLE) {
            game.redSprite.setLastMovementDirection(movement);
        }
    }

    protected int virtual_joystick_control() {
        for (int i=0; i < 4; i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touchPos);

                if (up.contains(touchPos.x, touchPos.y)) {
                    posY -= 20;
                    lastMovement = UP;
                    return UP;
                } else if (down.contains(touchPos.x, touchPos.y)) {
                    posY += 20;
                    lastMovement = DOWN;
                    return DOWN;
                } else if (left.contains(touchPos.x, touchPos.y)) {
                    posX -= 20;
                    lastMovement = LEFT;
                    return LEFT;
                } else if (right.contains(touchPos.x, touchPos.y)) {
                    posX += 20;
                    lastMovement = RIGHT;
                    return RIGHT;
                } else {
                    return IDLE;
                }
            }
        return IDLE;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Actualitzem la càmera
        camera.update();

        game.redSprite.spriteBatch.setProjectionMatrix(camera.combined);

        // Actualitzem el temps d'estat de l'animació
        stateTime += delta;
        int movement = virtual_joystick_control();
        // Determinem l'animació del personatge en funció del control del joystick
        switch (movement) {
            case 1:
                frameKey = game.redSprite.walkUpAnimation.getKeyFrame(stateTime, true);
                break;
            case 2:
                frameKey = game.redSprite.walkDownAnimation.getKeyFrame(stateTime, true);
                break;
            case 3:
                frameKey = game.redSprite.walkLeftAnimation.getKeyFrame(stateTime, true);
                break;
            case 4:
                frameKey = game.redSprite.walkRightAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                frameKey = game.redSprite.getIdleAnimation().getKeyFrame(stateTime, true);
                break;
        }
        updateLastMovement(movement);
        // Establim la posició de la regió del fons
        backgroundRegion.setRegion(posX, posY, 1920, 1980);

        game.redSprite.spriteBatch.begin();
        game.redSprite.spriteBatch.draw(backgroundRegion, 0, 0);
        game.redSprite.spriteBatch.draw(frameKey, xSpritePos, ySpritePos, 350, 350);
        game.redSprite.spriteBatch.end();
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
