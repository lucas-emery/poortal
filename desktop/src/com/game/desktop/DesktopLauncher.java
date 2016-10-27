package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.controllers.Controller;
import com.game.services.VariablesService;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Poortal";
		config.width=977;
		config.height=550;
		config.fullscreen = VariablesService.SHOW_FULLSCREEN;
		new LwjglApplication(new Controller(), config);

	}
}
