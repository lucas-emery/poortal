package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.game.models.Model;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.views.PlayerView;
import com.game.views.View;

public class Controller extends ApplicationAdapter {

	private Model model;
	private View view;
	private LevelController levelController;
	private AssetsService assetsService; //Que sean static todos lo metodos del service?? (creo que no se puede hacer static)
	
	@Override
	public void create () {
		assetsService = new AssetsService();
		Player player = new Player();
		PlayerView playerView = new PlayerView(player, assetsService);
		levelController = new LevelController();
		model = new Model(levelController.getLevelObjects(), player);
		view = new View(levelController.getLevelObjectsViews(), playerView);
	}

	@Override
	public void render () {
		model.update();
		view.render();
	}
	
	@Override
	public void dispose () {
	}
}
