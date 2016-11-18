package com.game.controllers;


import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.game.models.Model;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.views.PlayerView;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AssetsService.class, PlayerView.class})
public class PlayerControllerTest {

    @BeforeClass
    public static void setUp() throws Exception{
        PowerMock.mockStatic(AssetsService.class);
        PlayerView playerView = PowerMock.createMock(PlayerView.class);
        EasyMock.expect(AssetsService.getPlayerStateData()).andReturn(new AnimationStateData(new SkeletonData())).times(2);
        PowerMock.replay(AssetsService.class);
        PowerMock.expectNew(PlayerView.class, new Player()).andReturn(playerView);
        //EasyMock.expect(AssetsService.getPlayerStateData()).andReturn(new AnimationStateData(new SkeletonData()));
        PowerMock.replay(AssetsService.class, PlayerView.class, playerView);

        PlayerController.initialize();
    }

    @Test
    public void setAnimation() throws Exception {
        //assertFalse(PlayerController.getPlayer() == null);
    }

    @Test
    public void firePortal() throws Exception {

    }

}