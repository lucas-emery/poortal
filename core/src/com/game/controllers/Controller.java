package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject;
import com.game.models.Model;
import com.game.models.Player;
import com.game.models.Teleportation;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;
import com.game.views.LevelObjectView;
import com.game.views.PlayerView;
import com.game.views.View;

/**
 *The controller is a class which handles all of the input
 * within the program and interacts both with the model and
 * view updating the model's state and also sends messages
 * every frame to the view to render itself.
 */
public class Controller extends ApplicationAdapter {

	private static Model model;
	private static View view;

	/**
	 *
     */
	@Override
	public void create () {
		AssetsService.initialize();

		Player player = new Player();
		PlayerView playerView = new PlayerView(player);

		LevelController.setPlayer(player);
		LevelController.generateLevel();

		model = new Model(LevelController.getLevelObjects(), player, LevelController.getLevelWorld());
		view = new View(model, LevelController.getLevelObjectsViews(), playerView, AssetsService.getLevelSprite(0));
		WallController.setWalls(LevelController.getWalls());

		PlayerController.setPlayer(player);
		PlayerController.setPlayerView(playerView);
		Gdx.input.setInputProcessor(new InputController());
	}

	/**
	 *
     */
	@Override
	public void render () {
		model.update();
		InputController.update();
		TeleportationController.update();
		updatePlayerCollisionState();
		updateButtonCollisionState();
		view.render();
	}

	/**
	 *
     */
	private void updateButtonCollisionState() {
		boolean isPressed = CollisionController.getButtonPressed();
		//updateButtonCollisionState(isPressed);
	}

	/**
	 *
     */
	private void updatePlayerCollisionState() {
		boolean grounded = CollisionController.isPlayerOnGround();
		Fixture vicinity = CollisionController.getVicinity();
		PlayerController.updatePlayerCollisionState(grounded, vicinity);
	}

	/**
	 *
     */
	@Override
	public void dispose () {
	}

	/**
	 *Method which
	 * @param width
	 * @param height
     */
	@Override
	public void resize(int width, int height) {
		view.resize(width, height);
	}

	/**
	 *
	 * @param callback
	 * @param from
     * @param to
     */
	public static void queryRayCast(RayCastCallback callback, Vector2 from, Vector2 to) {
		model.queryRayCast(callback, from, to);
	}

	/**
	 *
	 * @param screenCoords
     * @return
     */
	public static Vector2 getGraphicsCoords(Vector2 screenCoords) {
		return view.getGraphicsCoords(screenCoords);
	}

	/**
	 *
	 * @param object
	 * @param objectView
     */
	public static  void addLevelObject(LevelObject object, LevelObjectView objectView) {
		model.addObject(object);
		view.addView(objectView);
	}

	/**
	 *
	 * @param object
	 * @param objectView
     */
	public static void removeLevelObject(LevelObject object, LevelObjectView objectView) {
		model.removeObject(object);
		view.removeView(objectView);
	}
}
