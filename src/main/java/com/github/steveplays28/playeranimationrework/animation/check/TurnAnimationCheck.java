package com.github.steveplays28.playeranimationrework.animation.check;

import com.github.steveplays28.playeranimationrework.animation.AnimationData;
import com.github.steveplays28.playeranimationrework.animation.AnimationPriority;
import com.github.steveplays28.playeranimationrework.animation.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;

import java.util.ArrayList;

import static com.github.steveplays28.playeranimationrework.client.util.AnimationUtil.*;
import static com.github.steveplays28.playeranimationrework.client.util.AnimationUtil.getItemsWithThirdPersonRightArmAnimations;

public class TurnAnimationCheck implements AnimationCheck {
	private static final String TURN_LEFT_ANIMATION_NAME = "turn_left";
	private static final String TURN_RIGHT_ANIMATION_NAME = "turn_right";

	private final ArrayList<ModelPart> disabledModelParts = new ArrayList<>();

	private boolean shouldPlay = false;
	private String selectedAnimationName;
	private float lastBodyYaw;

	@Override
	public void tick(AbstractClientPlayerEntity player) {
		boolean isMoving = Math.abs(player.getX() - player.prevX) > 0 || Math.abs(player.getZ() - player.prevZ) > 0;

		if (isMoving) {
			return;
		}

		int bodyYawDelta = (int) (player.getBodyYaw() - this.lastBodyYaw);

		if (Math.abs(bodyYawDelta) > 0) {
			if (bodyYawDelta < 0) {
				this.selectedAnimationName = TURN_LEFT_ANIMATION_NAME;
			} else {
				this.selectedAnimationName = TURN_RIGHT_ANIMATION_NAME;
			}

			this.shouldPlay = true;
		}

		this.lastBodyYaw = player.getBodyYaw();

		if (getItemsWithThirdPersonArmAnimations().contains(
				player.getEquippedStack(EquipmentSlot.MAINHAND).getItem().getClass()) || getItemsWithThirdPersonArmAnimations().contains(
				player.getEquippedStack(EquipmentSlot.OFFHAND).getItem().getClass())) {
			disabledModelParts.add(ModelPart.LEFT_ARM);
			disabledModelParts.add(ModelPart.RIGHT_ARM);
		}

		if (getItemsWithThirdPersonRightArmAnimations().contains(player.getEquippedStack(
				EquipmentSlot.MAINHAND).getItem().getClass()) || getItemsWithThirdPersonRightArmAnimations().contains(
				player.getEquippedStack(EquipmentSlot.OFFHAND).getItem().getClass())) {
			disabledModelParts.add(ModelPart.RIGHT_ARM);
		}
	}

	@Override
	public AnimationData getAnimationData() {
		return new AnimationData(getAnimation(selectedAnimationName), 1.0f, 5, disabledModelParts);
	}

	@Override
	public AnimationPriority getPriority() {
		return AnimationPriority.TURN;
	}

	@Override
	public boolean getShouldPlay() {
		return this.shouldPlay;
	}

	@Override
	public void cleanup() {
		this.shouldPlay = false;
		this.selectedAnimationName = null;
		disabledModelParts.clear();
	}
}
