package spinnery.common;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.world.World;
import spinnery.Spinnery;
import spinnery.registry.NetworkRegistry;
import spinnery.util.StackUtilities;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.WNetworked;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BaseContainer extends Container implements Tickable {
	public static final int PLAYER_INVENTORY = 0;

	public Map<Integer, Inventory> linkedInventories = new HashMap<>();

	protected World linkedWorld;
	protected final WInterface serverInterface;

	public BaseContainer(int synchronizationID, PlayerInventory linkedPlayerInventory) {
		super(null, synchronizationID);
		getInventories().put(PLAYER_INVENTORY, linkedPlayerInventory);
		setLinkedWorld(linkedPlayerInventory.player.world);
		serverInterface = new WInterface(this);
	}

	public Map<Integer, Inventory> getInventories() {
		return linkedInventories;
	}

	public void onInterfaceEvent(int widgetSyncId, WNetworked.Event event, CompoundTag payload) {
		Set<WAbstractWidget> checkWidgets = serverInterface.getAllWidgets();
		for (WAbstractWidget widget : checkWidgets) {
			if (!(widget instanceof WNetworked)) continue;
			if (((WNetworked) widget).getSyncId() == widgetSyncId) {
				((WNetworked) widget).onInterfaceEvent(event, payload);
				return;
			}
		}
	}

	public void onSlotClicked(int slotNumber, int inventoryNumber, int button, SlotActionType action, PlayerEntity player) {
		WSlot slotA = null;

		for (WAbstractWidget widget : serverInterface.getAllWidgets()) {
			if (widget instanceof WSlot && ((WSlot) widget).getSlotNumber() == slotNumber && ((WSlot) widget).getInventoryNumber() == inventoryNumber) {
				slotA = (WSlot) widget;
			}
		}

		if (slotA == null) {
			return;
		}

		ItemStack stackA = slotA.getStack();
		ItemStack stackB = player.inventory.getCursorStack();

		String debugString = "";

		if (Spinnery.IS_DEBUG) {
			debugString += "?\nAction:\t" + action + "\n";
		}

		switch (action) {
			case PICKUP: {
				if (!stackA.isItemEqual(stackB) || stackA.getTag() != stackB.getTag()) {
					if (button == 0) { // Swap with existing // LMB
						if (slotA.isOverrideMaximumCount()) {
							if (stackA.isEmpty()) {
								ItemStack stackC = stackA.copy();
								stackA = stackB.copy();
								stackB = stackC.copy();
							} else if (stackB.isEmpty()) {
								int maxA = slotA.getMaxCount();
								int maxB = stackB.getMaxCount();

								int countA = stackA.getCount();
								int countB = stackB.getCount();

								int availableA = maxA - countA;
								int availableB = maxB - countB;

								ItemStack stackC = stackA.copy();
								stackC.setCount(Math.min(countA, availableB));
								stackB = stackC.copy();
								stackA.decrement(Math.min(countA, availableB));
							}
						} else {
							ItemStack stackC = stackA.copy();
							stackA = stackB.copy();
							stackB = stackC.copy();
						}
					} else if (button == 1 && !stackB.isEmpty()) { // Add to existing // RMB
						if (stackA.isEmpty()) { // If existing is empty, initialize it // RMB
							stackA = new ItemStack(stackB.getItem(), 1);
							stackA.setTag(stackB.getTag());
							stackB.decrement(1);
						}
					} else if (button == 1) { // Split existing // RMB
						if (slotA.isOverrideMaximumCount()) {
							ItemStack stackC = stackA.split(Math.min(stackA.getCount(), stackA.getMaxCount()) / 2);
							stackB = stackC.copy();
						} else {
							ItemStack stackC = stackA.split(stackA.getCount() / 2);
							stackB = stackC.copy();
						}
					}
				} else {
					if (button == 0) {
						StackUtilities.clamp(stackB, stackA, stackB.getMaxCount(), slotA.getMaxCount()); // Add to existing // LMB
					} else {
						boolean canStackTransfer = stackB.getCount() >= 1 && stackA.getCount() < slotA.getMaxCount();
						if (canStackTransfer) { // Add to existing // RMB
							stackA.increment(1);
							stackB.decrement(1);
						}
					}
				}
				break;
			}
			case CLONE: {
				if (player.isCreative()) {
					stackB = new ItemStack(stackA.getItem(), stackA.getMaxCount()); // Clone existing // MMB
					stackB.setTag(stackA.getTag());
				}
				break;
			}
			case QUICK_MOVE: {
				for (WAbstractWidget widget : serverInterface.getAllWidgets()) {
					if (widget instanceof WSlot && ((WSlot) widget).getLinkedInventory() != slotA.getLinkedInventory()) {
						WSlot slotB = ((WSlot) widget);
						ItemStack stackC = slotB.getStack();

						if (!stackA.isEmpty() && (stackC.getCount() < slotB.getMaxCount() || stackC.getCount() < stackA.getMaxCount())) {
							if (stackC.isEmpty() || (stackA.isItemEqual(stackC) && stackA.getTag() == stackB.getTag())) {
								Pair<ItemStack, ItemStack> result = StackUtilities.clamp(stackA, stackC, slotA.getMaxCount(), slotB.getMaxCount());
								stackA = result.getFirst();
								slotB.setStack(result.getSecond());
								debugString += "Slot B:\t" + slotB.getStack().toString() + ", " + slotB.getLinkedInventory().getClass().getSimpleName() + "\n";
									break;

							}
						}
					}
				}
				break;
			}
		}

		slotA.setStack(stackA);
		((PlayerInventory) linkedInventories.get(PLAYER_INVENTORY)).setCursorStack(stackB);

		if (Spinnery.IS_DEBUG) {
			System.out.println(debugString + "Slot A:\t" + slotA.getStack().toString() + ", " + slotA.getLinkedInventory().getClass().getSimpleName() + "\nCursor:\t" + ((PlayerInventory) linkedInventories.get(PLAYER_INVENTORY)).getCursorStack().toString() + ", " + PlayerInventory.class.getSimpleName());
		}
	}

	public WInterface getInterface() {
		return serverInterface;
	}

	public World getLinkedWorld() {
		return linkedWorld;
	}

	public <C extends BaseContainer> C setLinkedWorld(World linkedWorld) {
		this.linkedWorld = linkedWorld;
		return (C) this;
	}

	public PlayerInventory getLinkedPlayerInventory() {
		return (PlayerInventory) linkedInventories.get(PLAYER_INVENTORY);
	}

	public Inventory getInventory(int inventoryNumber) {
		return linkedInventories.get(inventoryNumber);
	}

	public Map<Integer, Map<Integer, ItemStack>> cachedInventories = new HashMap<>();

	@Override
	public void onContentChanged(Inventory inventory) {
		for (WAbstractWidget widget : serverInterface.getAllWidgets()) {
			if (widget instanceof WSlot) {
				WSlot slotA = ((WSlot) widget);


				if (cachedInventories.get(slotA.getInventoryNumber()) != null && cachedInventories.get(slotA.getInventoryNumber()).get(slotA.getSlotNumber()) != null) {
					ItemStack stackA = slotA.getStack();
					ItemStack stackB = cachedInventories.get(slotA.getInventoryNumber()).get(slotA.getSlotNumber());

					if ((!stackA.isEmpty() || !stackB.isEmpty()) || (stackA.getCount() != stackB.getCount()) || !stackA.isItemEqual(stackB)) {
						ServerSidePacketRegistry.INSTANCE.sendToPlayer(this.getLinkedPlayerInventory().player, NetworkRegistry.SLOT_UPDATE_PACKET, NetworkRegistry.createSlotUpdatePacket(syncId, slotA.getSlotNumber(), slotA.getInventoryNumber(), slotA.getStack()));
					}

					cachedInventories.get(slotA.getInventoryNumber()).put(slotA.getSlotNumber(), slotA.getStack());
				} else {
					cachedInventories.computeIfAbsent(slotA.getInventoryNumber(), value -> new HashMap<>());

					ItemStack stackA = slotA.getStack();
					ItemStack stackB = Optional.ofNullable(cachedInventories.get(slotA.getInventoryNumber()).get(slotA.getSlotNumber())).orElse(ItemStack.EMPTY);

					if ((!stackA.isEmpty() || !stackB.isEmpty()) || (stackA.getCount() != stackB.getCount()) || !stackA.isItemEqual(stackB)) {
						ServerSidePacketRegistry.INSTANCE.sendToPlayer(this.getLinkedPlayerInventory().player, NetworkRegistry.SLOT_UPDATE_PACKET, NetworkRegistry.createSlotUpdatePacket(syncId, slotA.getSlotNumber(), slotA.getInventoryNumber(), slotA.getStack()));
					}

					cachedInventories.get(slotA.getInventoryNumber()).put(slotA.getSlotNumber(), slotA.getStack());
				}
			}
		}
	}

	@Deprecated
	@Override
	public Slot addSlot(Slot slot) {
		return super.addSlot(slot);
	}

	@Deprecated
	@Override
	public ItemStack onSlotClick(int identifier, int button, SlotActionType action, PlayerEntity player) {
		return ItemStack.EMPTY;
	}

	@Deprecated
	@Override
	public boolean canUse(PlayerEntity entity) {
		return true;
	}

	@Override
	public void tick() {
	}
}
