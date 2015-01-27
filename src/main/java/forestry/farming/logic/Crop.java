/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.farming.logic;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import forestry.api.farming.ICrop;
import forestry.core.config.Defaults;
import forestry.core.vect.Vect;

public abstract class Crop implements ICrop {

	protected final World world;
	protected final Vect position;

	public Crop(World world, Vect position) {
		this.world = world;
		this.position = position;
	}

	protected final void setBlock(Vect position, Block block, int meta) {
		world.setBlock(position.x, position.y, position.z, block, meta, Defaults.FLAG_BLOCK_SYNCH);
	}

	protected final void clearBlock(Vect position) {
		world.setBlockToAir(position.x, position.y, position.z);
		if (world.getTileEntity(position.x, position.y, position.z) != null) {
			world.setTileEntity(position.x, position.y, position.z, null);
		}
	}

	protected final Block getBlock(Vect position) {
		return world.getBlock(position.x, position.y, position.z);
	}

	protected final int getBlockMeta(Vect position) {
		return world.getBlockMetadata(position.x, position.y, position.z);
	}

	protected final ItemStack getAsItemStack(Vect position) {
		return new ItemStack(getBlock(position), 1, getBlockMeta(position));
	}

	protected abstract boolean isCrop(Vect pos);

	protected abstract Collection<ItemStack> harvestBlock(Vect pos);

	@Override
	public Collection<ItemStack> harvest() {
		if (!isCrop(position)) {
			return null;
		}

		return harvestBlock(position);
	}

}
