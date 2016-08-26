package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class backgruundTest implements ApplicationListener, GestureDetector.GestureListener, InputProcessor{
		private OrthographicCamera camera;
		private SpriteBatch batch;
		private Texture texture;
		private Sprite sprite;
		private Viewport viewport;
		private Skin skin;
		private Stage stage;
		private final float HEIGHT = 2560;
		private final float WIDTH = 1800;
		private BackActor backActor;
		private DialogActor dialogActor;
		private CharacterActor characterActor;
		private static float VIRTUAL_WIDTH;
		private static float VIRTUAL_HEIGHT;

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int width = Gdx.graphics.getWidth();
		if (screenX < width/2) {
			characterActor.setDirection(false);
			characterActor.setMoving(true);
		} else {
			characterActor.setDirection(true);
			characterActor.setMoving(true);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		characterActor.setMoving(false);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public class BackActor extends Actor {
			Texture texture2 = new Texture(Gdx.files.internal("Background_final.png"));
			public void draw(Batch batch, float alpha) {
				batch.draw(texture2, 0, 0);
			}
			public float getWidth() {
				return texture2.getWidth();
			}
			public float getHeight() {
				return texture2.getHeight();
			}
		}


		private static NinePatch processNinePatchFile(String fname) {
			final Texture t = new Texture(Gdx.files.internal(fname));
			final int width = t.getWidth() - 2;
			final int height = t.getHeight() - 2;
			return new NinePatch(new TextureRegion(t, 1, 1, width, height), 3, 3, 3, 3);
		}



		public class CharacterActor extends Actor {
			Texture manT = new Texture(Gdx.files.internal("man.9.png"));
			Sprite man = new Sprite (manT);
			float startScaleX = man.getScaleX();
			float startScaleY = man.getScaleY();
			float posX = 20;
			float posY = 50;
			boolean moving = false;
			int movementvalue = 10;
			ArrayList<String> characterDialog;
			int curI = 0;

			public CharacterActor() {
				setBounds(posX, posY, manT.getWidth(), manT.getHeight());
				this.addListener( new ClickListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
						dialog();
						return true;
					}
				});
				characterDialog = new ArrayList<String>();
				characterDialog.add("It's to dangerous to go");
				characterDialog.add("alone, take this!");
				characterDialog.add("Go kill some things and");
				characterDialog.add("save the world? or not.");
				characterDialog.add("It's up to you really.");
				characterDialog.add("");
			}

			public void draw(Batch batch, float alpha) {
				int scaleX = 250;
				int scaleY = 150;

				man.draw(batch);
				man.setScale(startScaleX/2, startScaleY/2);
				man.setPosition(posX, posY );
			}

			public void moveX(float num) {
				posX += num;
			}

			public void setMoving(boolean bool) {
				moving = bool;
			}

			public void setDirection(boolean bool) {
				if (bool) {
					movementvalue = 10;
				}
				if (!bool) {
					movementvalue = -10;
				}
			}

			public void dialog() {
				if (curI >= characterDialog.size()) {
					curI = 0;
					dialogActor.setVisible(false);
					return;
				}
				if (!dialogActor.isVisible()) {
					curI = 0;
					dialogActor.drawActualDialog(characterDialog.get(curI), characterDialog.get(curI + 1));
					dialogActor.setVisible(true);
					curI += 2;
					return;
				}
				dialogActor.drawActualDialog(characterDialog.get(curI), characterDialog.get(curI + 1));
				curI += 2;
			}
			/*
			if (dialog is not visible)
				start cycle
			else if (dialog is visible)
				if (next dialog is null)
					make dialog invisible
				else
					display next dialog
			 */
			public void act(float delta) {
				float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
				float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

				float w = Gdx.graphics.getWidth();
				float h = Gdx.graphics.getHeight();

				this.setBounds(posX, posY, man.getRegionWidth(), man.getRegionHeight());

				if (moving) {
					this.moveX(movementvalue);
				}
				if (posX > camera.position.x + w/2 - man.getWidth()) {
					camera.position.x += movementvalue;
					camera.position.x = MathUtils.clamp(camera.position.x, w/2 , backActor.getWidth() - effectiveViewportWidth/2);
				}
				if (posX + w/2 < camera.position.x) {
					camera.position.x += movementvalue;
					camera.position.x = MathUtils.clamp(camera.position.x, w/2 , backActor.getWidth() - effectiveViewportWidth/2);
				}
				System.out.println("posX: " + posX);
				posX = MathUtils.clamp(posX, camera.position.x - w/2 , backActor.getWidth() - man.getWidth());
			}
		}

		public class DialogActor extends Actor {
			NinePatch ninePatch = processNinePatchFile("mychatbubble2.9.png");
			BitmapFont bitmapFont = new BitmapFont();
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TanglewoodTales.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			float posX;
			float posY;
			int scaleX = 250;
			int scaleY = 150;
			float curAlpha;
			Label.LabelStyle labelStyle;
			Label firstLine;
			Label secondLine;

			public DialogActor() {
				bitmapFont = generator.generateFont(parameter);
				labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);

				firstLine = new Label("", labelStyle);

				secondLine = new Label("", labelStyle);
			}
			public void draw(Batch batch, float alpha) {
				posX = characterActor.getX() - characterActor.getWidth()/2.13f;
				posY = characterActor.getY() + characterActor.getHeight()/1.4f;
				ninePatch.draw(batch, posX, posY, scaleX, scaleY);

				parameter.size = 20;
				parameter.color = Color.BLACK;

				firstLine.setX(posX + 30);
				firstLine.setY(scaleY + posY - 40);
				firstLine.draw(batch, alpha);

				secondLine.setX(posX + 30);
				secondLine.setY(scaleY + posY - 70);
				secondLine.draw(batch, alpha);
			}

			public void act(float delta) {
				posX = characterActor.getX() + dialogActor.getWidth()/2;
				posY = characterActor.getY() + characterActor.getHeight()/1.4f;

				this.setPosition(posX, posY);
			}

			public void drawActualDialog(String string1, String string2) {

				if (string1 == null) {
					this.setVisible(false);
					return;
				}

				firstLine.setText(string1);
				secondLine.setText(string2);
			}



		}

		public class DPadActor extends Actor {

		}

		public static void ConfigureDimensions(int width, int height){
			VIRTUAL_WIDTH = width;
			VIRTUAL_HEIGHT = height;
		}

		@Override
		public void create() {

			batch = new SpriteBatch();
			backActor = new BackActor();
			characterActor = new CharacterActor();
			dialogActor = new DialogActor();

			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			camera = new OrthographicCamera(w, backActor.getHeight());
			viewport = new ScreenViewport(camera);
			stage = new Stage(viewport);

			camera.update();

			stage.addActor(backActor);
			stage.addActor(characterActor);
			stage.addActor(dialogActor);

			dialogActor.setVisible(false);
			characterActor.setTouchable(Touchable.enabled);

			stage.act();
			stage.draw();

			InputMultiplexer inputMultiplexer = new InputMultiplexer();

			inputMultiplexer.addProcessor(stage);
			inputMultiplexer.addProcessor(new GestureDetector(this));
			inputMultiplexer.addProcessor(this);

			Gdx.input.setInputProcessor(inputMultiplexer);
		}

		@Override
		public void dispose() {
			batch.dispose();
//			texture.dispose();
			stage.dispose();

		}

		@Override
		public void render() {
			float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
			float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();


//			camera.zoom = MathUtils.clamp(camera.zoom, HEIGHT/camera.viewportHeight, WIDTH/camera.viewportWidth);

//			camera.position.x = MathUtils.clamp(camera.position.x ,w/2, w/2 - WIDTH);
//			camera.position.y = MathUtils.clamp(camera.position.y, h/2, h/2 - HEIGHT);
//			camera.position.x = MathUtils.clamp(camera.position.x, w/2 , backActor.getWidth() - effectiveViewportWidth/2);
//			camera.position.y = MathUtils.clamp(camera.position.y, h/2, backActor.getHeight() - effectiveViewportHeight/2);
			camera.update();

			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.setProjectionMatrix(camera.combined);

			batch.begin();
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
			batch.end();
		}

		@Override
		public void resize(int width, int height) {
//			viewport.update(width, height);
			stage.getViewport().update(width, height, true);
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {

		}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
//		int h = Gdx.graphics.getHeight();
//		boolean inX = x > characterActor.getX() && x < characterActor.getX() + characterActor.getWidth();
//		boolean inY = y > h - (characterActor.getY() + characterActor.getHeight()/2) && y < h - characterActor.getY();
//
//		System.out.println("X: " + x);
//		System.out.println("Y: " + y);
//
//		System.out.println("posX: " + characterActor.getX());
//		System.out.println("posY: " + characterActor.getY());
//
//		if (inX && inY) {
//			if (dialogActor.isVisible()) {
//				dialogActor.setVisible(false);
//				return true;
//			}
//			dialogActor.setVisible(true);
//			return true;
//		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
//		camera.translate(deltaX, 0);
//		camera.update();
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {

	}
}
