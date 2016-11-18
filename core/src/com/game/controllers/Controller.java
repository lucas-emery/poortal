package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject;
import com.game.models.Model;
import com.game.services.AssetsService;
import com.game.views.LevelObjectView;
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
	private static int level;

	/**
	 *
     */
	@Override
	public void create () {
		level = 1;

		AssetsService.initialize();
		PlayerController.initialize();
		LevelController.initialize();

		Gdx.input.setInputProcessor(new InputController());

		LevelController.generateLevel(level);

		model = new Model();
		view = new View(model);
	}

	/**
	 * This method is in charge of updating the state of the game
     */
	@Override
	public void render () {
       // if(InputController.spaceispressed) {
			model.update();
		//}
			InputController.update();
			TeleportationController.update(model);
			updatePlayerCollisionState();
			updateButtonState();
			view.render();
	}

	private void updateButtonState() {
		ButtonController.updateTimer(Gdx.graphics.getDeltaTime());
	}

	/**
	 * this method will update the player's physical state
	 * within the game
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
	 * This method returns the Model object in Controller,
	 * used for testing purposes
	 * @return Model object
	 */
	public static Model getModel(){
		return model;
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
	 * This method will add the respective LevelObject to the model
	 * and the view contained within the controller
	 * @param object model of the LevelObject to add
	 * @param objectView view of the LevelObject to add
     */
	public static  void addLevelObject(LevelObject object, LevelObjectView objectView) {
		model.addObject(object);
		view.addView(objectView);
	}

	/**
	 * This method will remove the LevelObject from the model
	 * and the view within the Controller
	 * @param object the model of the LevelObject to remove
	 * @param objectView the view of the LevelObject to remove
     */
	public static void removeLevelObject(LevelObject object, LevelObjectView objectView) {
		model.removeObject(object);
		view.removeView(objectView);
	}

	/**
	 * This method will advance the game to the next level so
	 * it can continue
     */
	public static void nextLevel() {
		level++;

		CollisionController.reset();
		TeleportationController.reset();
		InputController.reset();
		PortalController.reset();
		PlayerController.reset();

		LevelController.generateLevel(level);

		model = new Model();
		view.reset(model);
	}
}
