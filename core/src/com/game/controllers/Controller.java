package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.Model;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;
import com.game.views.PlayerView;
import com.game.views.View;

public class Controller extends ApplicationAdapter {

	private static Model model;
	private static View view;
	private LevelController levelController;
	PlayerController playerController;
	private InputController inputController;

	@Override
	public void create () {

		AssetsService.initialize();
		Player player = new Player();
		PlayerView playerView = new PlayerView(player);
		playerController = new PlayerController(player);

		levelController = new LevelController();
		levelController.setPlayer(player);
		levelController.generateLevel();

		model = new Model(levelController.getLevelObjects(), player, levelController.getLevelWorld());
		view = new View(model, levelController.getLevelObjectsViews(), playerView, AssetsService.getLevelSprite(0));
		WallController.setWalls(levelController.getWalls());

		inputController = new InputController(playerController);
		Gdx.input.setInputProcessor(inputController);
	}

	@Override
	public void render () {
		model.update();
		inputController.update();
		view.render();
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public void resize(int width, int height) {
		view.resize(width, height);
	}

	public static void queryRayCast(RayCastCallback callback, Vector2 from, Vector2 to) {
		model.queryRayCast(callback, from, to);
	}
}
