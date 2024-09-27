/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 The Quilt Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.networking.v1;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.PacketByteBuf;

/**
 * Helper methods for working with and creating {@link PacketByteBuf}s.
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.PacketByteBufs PacketByteBufs}
 */
@Deprecated
public final class PacketByteBufs {
	/**
	 * Returns an empty instance of packet byte buf.
	 *
	 * @return an empty buf
	 */
	public static PacketByteBuf empty() {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.empty();
	}

	/**
	 * Returns a new heap memory-backed instance of packet byte buf.
	 *
	 * @return a new buf
	 */
	public static PacketByteBuf create() {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.create();
	}

	// Convenience methods for byte buf methods that return a new byte buf

	/**
	 * Wraps the newly created buf from {@code buf.readBytes} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param length the number of bytes to transfer
	 * @return the transferred bytes
	 * @see ByteBuf#readBytes(int)
	 */
	public static PacketByteBuf readBytes(ByteBuf buf, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.readBytes(buf, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.readSlice} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param length the size of the new slice
	 * @return the newly created slice
	 * @see ByteBuf#readSlice(int)
	 */
	public static PacketByteBuf readSlice(ByteBuf buf, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.readSlice(buf, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.readRetainedSlice} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param length the size of the new slice
	 * @return the newly created slice
	 * @see ByteBuf#readRetainedSlice(int)
	 */
	public static PacketByteBuf readRetainedSlice(ByteBuf buf, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.readRetainedSlice(buf, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.copy} in a packet byte buf.
	 *
	 * @param buf the original buf
	 * @return a copy of the buf
	 * @see ByteBuf#copy()
	 */
	public static PacketByteBuf copy(ByteBuf buf) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.copy(buf);
	}

	/**
	 * Wraps the newly created buf from {@code buf.copy} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a copy of the buf
	 * @see ByteBuf#copy(int, int)
	 */
	public static PacketByteBuf copy(ByteBuf buf, int index, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.copy(buf, index, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.slice} in a packet byte buf.
	 *
	 * @param buf the original buf
	 * @return a slice of the buf
	 * @see ByteBuf#slice()
	 */
	public static PacketByteBuf slice(ByteBuf buf) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.slice(buf);
	}

	/**
	 * Wraps the newly created buf from {@code buf.retainedSlice} in a packet byte buf.
	 *
	 * @param buf the original buf
	 * @return a slice of the buf
	 * @see ByteBuf#retainedSlice()
	 */
	public static PacketByteBuf retainedSlice(ByteBuf buf) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.retainedSlice(buf);
	}

	/**
	 * Wraps the newly created buf from {@code buf.slice} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a slice of the buf
	 * @see ByteBuf#slice(int, int)
	 */
	public static PacketByteBuf slice(ByteBuf buf, int index, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.slice(buf, index, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.retainedSlice} in a packet byte buf.
	 *
	 * @param buf    the original buf
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a slice of the buf
	 * @see ByteBuf#retainedSlice(int, int)
	 */
	public static PacketByteBuf retainedSlice(ByteBuf buf, int index, int length) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.retainedSlice(buf, index, length);
	}

	/**
	 * Wraps the newly created buf from {@code buf.duplicate} in a packet byte buf.
	 *
	 * @param buf the original buf
	 * @return a duplicate of the buf
	 * @see ByteBuf#duplicate()
	 */
	public static PacketByteBuf duplicate(ByteBuf buf) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.duplicate(buf);
	}

	/**
	 * Wraps the newly created buf from {@code buf.retainedDuplicate} in a packet byte buf.
	 *
	 * @param buf the original buf
	 * @return a duplicate of the buf
	 * @see ByteBuf#retainedDuplicate()
	 */
	public static PacketByteBuf retainedDuplicate(ByteBuf buf) {
		return org.quiltmc.qsl.networking.api.PacketByteBufs.retainedDuplicate(buf);
	}

	private PacketByteBufs() {
	}
}
