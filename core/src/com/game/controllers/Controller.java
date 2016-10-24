package com.game.controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.models.Model;
import com.game.views.View;

public class Controller extends ApplicationAdapter {

	private Model model;
	private View view;
	
	@Override
	public void create () {
		model = new Model(this);
		view = new View(model);
	}

	@Override
	public void render () {
	}
	
	@Override
	public void dispose () {
	}
}
