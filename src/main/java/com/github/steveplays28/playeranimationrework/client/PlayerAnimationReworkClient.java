package com.github.steveplays28.playeranimationrework.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class PlayerAnimationReworkClient implements ClientModInitializer {
	public static final String MOD_ID = "player-animation-rework";
	public static final String MOD_NAMESPACE = "playeranimationrework";
	public static final String MOD_NAME = "Player Animation Rework";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String REAL_CAMERA_MOD_ID = "realcamera";
	public static final Random RANDOM = new Random();
	// Sound events
	public static final Identifier SLIDE_SOUND_EVENT_ID = new Identifier(MOD_NAMESPACE, "player.slide");
	public static final SoundEvent SLIDE_SOUND_EVENT = SoundEvent.of(SLIDE_SOUND_EVENT_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Loading {}.", MOD_NAME);

		// Register sound events
		Registry.register(Registries.SOUND_EVENT, SLIDE_SOUND_EVENT_ID, SLIDE_SOUND_EVENT);
	}
}
