package spinnery.container.common.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.util.Tickable;

import java.util.Optional;

public class WWidget implements Tickable {
	protected WPanel linkedWPanel;

	protected WAlignment alignment;

	protected double offsetX;
	protected double offsetY;

	protected double positionX = 0;
	protected double positionY = 0;
	protected double positionZ = 0;

	protected double sizeX = 0;
	protected double sizeY = 0;

	protected boolean isHidden = false;

	protected boolean hasFocus = false;

	protected boolean canMove = false;

	protected Runnable linkedRunnableOnMouseClicekd;
	protected Runnable linkedRunnableOnKeyPressed;
	protected Runnable linkedRunnableOnKeyReleased;
	protected Runnable linkedRunnableOnFocusGained;
	protected Runnable linkedRunnableOnFocusReleased;
	protected Runnable linkedRunnableOnDrawTooltip;
	protected Runnable linkedRunnableOnMouseReleased;
	protected Runnable linkedRunnableOnMouseMoved;
	protected Runnable linkedRunnableOnMouseDragged;
	protected Runnable linkedRunnableOnMouseScrolled;
	protected Runnable linkedRunnableOnSlotClicked;

	public WWidget() {
	}

	public void onKeyPressed(int keyPressed) {
		if (linkedRunnableOnKeyPressed != null) {
			linkedRunnableOnKeyPressed.run();
		}
	}

	public Runnable getOnKeyPressed() {
		return linkedRunnableOnKeyPressed;
	}

	public void setOnKeyPressed(Runnable linkedRunnableOnKeyPressed) {
		this.linkedRunnableOnKeyPressed = linkedRunnableOnKeyPressed;
	}

	public void onKeyReleased(int keyReleased) {
		if (linkedRunnableOnKeyReleased != null) {
			linkedRunnableOnKeyReleased.run();
		}
	}

	public Runnable getOnKeyReleased() {
		return linkedRunnableOnKeyReleased;
	}

	public void setOnKeyReleased(Runnable linkedRunnableOnKeyReleased) {
		this.linkedRunnableOnKeyReleased = linkedRunnableOnKeyReleased;
	}

	public void onFocusGained() {
		if (linkedRunnableOnFocusGained != null) {
			linkedRunnableOnFocusGained.run();
		}
	}

	public Runnable getOnFocusGained() {
		return linkedRunnableOnFocusGained;
	}

	public void setOnFocusGained(Runnable linkedRunnableOnFocusGained) {
		this.linkedRunnableOnFocusGained = linkedRunnableOnFocusGained;
	}

	public void onFocusReleased() {
		if (linkedRunnableOnFocusReleased != null) {
			linkedRunnableOnFocusReleased.run();
		}
	}

	public Runnable getOnFocusReleased() {
		return linkedRunnableOnFocusReleased;
	}

	public void setOnFocusReleased(Runnable linkedRunnableOnFocusReleased) {
		this.linkedRunnableOnFocusReleased = linkedRunnableOnFocusReleased;
	}

	public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
		if (linkedRunnableOnMouseReleased != null) {
			linkedRunnableOnMouseReleased.run();
		}
	}

	public Runnable getOnMouseReleased() {
		return linkedRunnableOnMouseReleased;
	}

	public void setOnMouseReleased(Runnable linkedRunnable) {
		this.linkedRunnableOnMouseReleased = linkedRunnable;
	}

	public void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (linkedRunnableOnMouseClicekd != null) {
			linkedRunnableOnMouseClicekd.run();
		}
	}

	public Runnable getOnMouseClicked() {
		return linkedRunnableOnMouseClicekd;
	}

	public void setOnMouseClicked(Runnable linkedRunnable) {
		this.linkedRunnableOnMouseClicekd = linkedRunnable;
	}

	public void onMouseDragged(double mouseX, double mouseY, int mouseButton, double dragOffsetX, double dragOffsetY) {
		if (linkedRunnableOnMouseDragged != null) {
			linkedRunnableOnMouseDragged.run();
		}
	}

	public Runnable getOnMouseDragged() {
		return linkedRunnableOnMouseDragged;
	}

	public void setOnMouseDragged(Runnable linkedRunnable) {
		this.linkedRunnableOnMouseDragged = linkedRunnable;
	}

	public void onMouseMoved(double mouseX, double mouseY) {
		if (linkedRunnableOnMouseMoved != null) {
			linkedRunnableOnMouseMoved.run();
		}
	}

	public Runnable getOnMouseMoved() {
		return linkedRunnableOnMouseMoved;
	}

	public void setOnMouseMoved(Runnable linkedRunnable) {
		this.linkedRunnableOnMouseMoved = linkedRunnable;
	}

	public void onMouseScrolled(double mouseX, double mouseY, double mouseZ) {
		if (linkedRunnableOnMouseScrolled != null) {
			linkedRunnableOnMouseScrolled.run();
		}
	}

	public Runnable getOnMouseScrolled() {
		return linkedRunnableOnMouseScrolled;
	}

	public void setOnMouseScrolled(Runnable linkedRunnable) {
		this.linkedRunnableOnMouseScrolled = linkedRunnable;
	}

	public void onSlotClicked(Slot slot, int slotX, int slotY, SlotActionType slotActionType) {
		if (linkedRunnableOnSlotClicked != null) {
			linkedRunnableOnSlotClicked.run();
		}
	}

	public Runnable getOnSlotClicked() {
		return linkedRunnableOnSlotClicked;
	}

	public void setOnSlotClicked(Runnable linkedRunnable) {
		this.linkedRunnableOnSlotClicked = linkedRunnable;
	}

	public void onDrawTooltip() {
		if (linkedRunnableOnDrawTooltip != null) {
			linkedRunnableOnDrawTooltip.run();
		}
	}

	public Runnable getOnDrawTooltip() {
		return linkedRunnableOnDrawTooltip;
	}

	public void setOnDrawTooltip(Runnable linkedRunnableOnDrawTooltip) {
		this.linkedRunnableOnDrawTooltip = linkedRunnableOnDrawTooltip;
	}

	public boolean getCanMove() {
		return canMove;
	}

	public void setMovable(boolean canMove) {
		this.canMove = canMove;
	}

	public WPanel getLinkedPanel() {
		return linkedWPanel;
	}

	public void setLinkedPanel(WPanel linkedWPanel) {
		this.linkedWPanel = linkedWPanel;
	}

	public double getSizeX() {
		return sizeX;
	}

	public void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getPositionZ() {
		return positionZ;
	}

	public void setPositionZ(double positionZ) {
		this.positionZ = positionZ;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	public boolean getFocus() {
		return hasFocus;
	}

	public boolean setFocus(boolean hasFocus) {
		if (!getFocus() && hasFocus) {
			onFocusGained();
		}
		if (getFocus() && !hasFocus) {
			onFocusReleased();
		}
		this.hasFocus = hasFocus;
		return hasFocus;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isWithinBounds(double positionX, double positionY) {
		return positionX > getPositionX() - 1
			&& positionX < getPositionX() + getSizeX() - 2
			&& positionY > getPositionY() - 1
			&& positionY < getPositionY() + getSizeY() - 2;
	}

	public boolean isFocused(double mouseX, double mouseY) {
		if (isHidden) {
			return false;
		}
		Optional<? extends WWidget> optional = linkedWPanel.getLinkedWidgets().stream().filter((widget) ->
				widget.getPositionZ() > getPositionZ() && widget.isWithinBounds(mouseX, mouseY)
		).findAny();
		setFocus(!optional.isPresent() && isWithinBounds(mouseX, mouseY));
		return getFocus();
	}

	public void alignWithContainerEdge() {
		this.setPositionX(getPositionX() + MinecraftClient.getInstance().window.getScaledWidth() / 2D - getLinkedPanel().getSizeX() / 2);
		this.setPositionY(getPositionY() + MinecraftClient.getInstance().window.getScaledHeight() / 2D - getLinkedPanel().getSizeY() / 2);
	}

	public void alignWithContainerCenter() {
		setPositionX(getPositionX() + MinecraftClient.getInstance().window.getScaledWidth() / 2D - (this.getSizeX() / 2));
	}

	public void drawWidget() {
		if (isHidden) {
			return;
		}
	}

	public void setAlignment(WAlignment alignment) {
		this.alignment = alignment;
		switch (alignment) {
			case PANEL_TOP_LEFT:
				setPositionX(MinecraftClient.getInstance().window.getScaledWidth() / 2f - getLinkedPanel().getSizeX() / 2f);
				setPositionY(MinecraftClient.getInstance().window.getScaledHeight() / 2f - getLinkedPanel().getSizeY() / 2f);
				break;
			case SCREEN_TOP_LEFT:
				setPositionX(0);
				setPositionY(0);
				break;
			case SCREEN_MIDDLE:
				setPositionX(MinecraftClient.getInstance().window.getScaledWidth() / 2f - getSizeX() / 2);
				setPositionY(MinecraftClient.getInstance().window.getScaledHeight() / 2f - getSizeY() / 2);
			case SCREEN_MIDDLE_HORIZONTAL:
				setPositionX(MinecraftClient.getInstance().window.getScaledWidth() / 2f - getSizeX() / 2f);
				break;
			case SCREEN_MIDDLE_VERTICAL:
				setPositionY(MinecraftClient.getInstance().window.getScaledHeight() / 2f - getSizeY() / 2f);
				break;
		}
	}

	@Override
	public void tick() {
	}
}
