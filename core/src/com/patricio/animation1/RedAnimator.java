package com.patricio.animation1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class RedAnimator {
    // Constant rows and columns of the sprite sheet
    TextureRegion[] walkFrames;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private static final float FRAME_DURATION = 0.175f;
    Animation<TextureRegion> completeAnimation;
    Animation<TextureRegion> walkUpAnimation;
    Animation<TextureRegion> walkDownAnimation;
    Animation<TextureRegion> walkRightAnimation;
    Animation<TextureRegion> walkLeftAnimation;
    Animation<TextureRegion> IDLE_DOWN_LEFT_RIGHT_UP;
    Texture walkSheet;
    SpriteBatch spriteBatch;
    float stateTime;
    int lastMovementDirection = GameScreen.DOWN;

    public RedAnimator(Texture walkSheetProvided) {
        // Load the sprite sheet as a Textures
        walkSheet = walkSheetProvided;

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        completeAnimation = new Animation<TextureRegion>(FRAME_DURATION, walkFrames);

        walkDownAnimation = new Animation<TextureRegion>(FRAME_DURATION, Arrays.copyOfRange(walkFrames, 0, 4));
        walkLeftAnimation = new Animation<TextureRegion>(FRAME_DURATION, Arrays.copyOfRange(walkFrames, 4, 8));
        walkRightAnimation = new Animation<TextureRegion>(FRAME_DURATION, Arrays.copyOfRange(walkFrames, 8, 12));
        walkUpAnimation = new Animation<TextureRegion>(FRAME_DURATION, Arrays.copyOfRange(walkFrames, 12, 16));

        // Create idle animation using the down frame as default
        IDLE_DOWN_LEFT_RIGHT_UP = new Animation<>(FRAME_DURATION, new TextureRegion[]{walkFrames[0]});
        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void setLastMovementDirection(int direction) {
        lastMovementDirection = direction;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        switch (lastMovementDirection) {
            case GameScreen.UP:
                return new Animation<>(FRAME_DURATION, new TextureRegion[]{walkFrames[12]});
            case GameScreen.DOWN:
                return new Animation<>(FRAME_DURATION, new TextureRegion[]{walkFrames[0]});
            case GameScreen.LEFT:
                return new Animation<>(FRAME_DURATION, new TextureRegion[]{walkFrames[4]});
            case GameScreen.RIGHT:
                return new Animation<>(FRAME_DURATION, new TextureRegion[]{walkFrames[8]});
            default:
                return IDLE_DOWN_LEFT_RIGHT_UP;
        }
    }
}
