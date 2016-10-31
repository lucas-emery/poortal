package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject;
import com.game.models.Model;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;
import com.game.views.LevelObjectView;
import com.game.views.PlayerView;
import com.game.views.View;

public class Controller extends ApplicationAdapter {

	private static Model model;
	private static View view;
	private LevelController levelController;
	PlayerController playerController;
	private InputController inputController;
	CollisionController collisionController;

	@Override
	public void create () {

		AssetsService.initialize();
		Player player = new Player();
		PlayerView playerView = new PlayerView(player);
		playerController = new PlayerController(player,playerView);

		collisionController = new CollisionController();

		levelController = new LevelController(collisionController);
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
		updatePlayerCollisionState();
		view.render();
	}

	private void updatePlayerCollisionState() {
		boolean grounded = levelController.getcollisionController().isPlayerOnGround();
		Fixture vicinity = levelController.getcollisionController().getVicinity();
		playerController.updatePlayerCollisionState(grounded, vicinity);
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

	public static Vector2 getGraphicsCoords(Vector2 screenCoords) {
		return view.getGraphicsCoords(screenCoords);
	}

	public static  void addLevelObject(LevelObject object, LevelObjectView objectView) {
		model.addObject(object);
		view.addView(objectView);
	}

	public static void removeLevelObject(LevelObject object, LevelObjectView objectView) {
		model.removeObject(object);
		view.removeView(objectView);
	}
}
