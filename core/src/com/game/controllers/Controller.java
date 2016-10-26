package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.models.Model;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.views.PlayerView;
import com.game.views.View;

public class Controller extends ApplicationAdapter {

	private Model model;
	private View view;
	private LevelController levelController;
	
	@Override
	public void create () {
		AssetsService.initialize();
		Player player = new Player();
		PlayerView playerView = new PlayerView(player);
		levelController = new LevelController();
		levelController.setPlayer(player);
		levelController.generateLevel();
		model = new Model(levelController.getLevelObjects(), player, levelController.getLevelWorld());
		view = new View(model, levelController.getLevelObjectsViews(), playerView);
		Gdx.input.setInputProcessor(new InputController());
	}

	@Override
	public void render () {
		model.update();
		view.render();
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public void resize(int width, int height) {
		view.resize(width, height);
	}
}
