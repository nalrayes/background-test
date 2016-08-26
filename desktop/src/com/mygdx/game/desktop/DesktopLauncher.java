package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.backgruundTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "camera";
		cfg.useGL30 = false;
		cfg.width = 637;
		cfg.height = 512;
		new LwjglApplication(new backgruundTest(), cfg);
	}
}
