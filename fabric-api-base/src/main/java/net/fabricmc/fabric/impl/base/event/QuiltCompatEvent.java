/*
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

package net.fabricmc.fabric.impl.base.event;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.Event;

/**
 * Allows for the translation of events from Quilt to Fabric.
 */
public final class QuiltCompatEvent {
	private QuiltCompatEvent() {
	}

	/**
	 * Creates a new Fabric event that delegates to a Quilt Event.
	 *
	 * @param event the Quilt event
	 * @param listenerConverter a function that converts a Fabric listener to a Quilt listener
	 * @param invoker a function that converts a Quilt listener to a Fabric listener
	 * @param <S> the Quilt event listener type
	 * @param <D> the Fabric event listener type
	 * @return a Fabric event wrapping the Quilt event
	 */
	public static <S, D> Event<D> fromQuilt(org.quiltmc.qsl.base.api.event.Event<S> event,
											Function<D, S> listenerConverter, Function<Supplier<S>, D> invoker) {
		return new QuiltEvent<>(event, listenerConverter, invoker);
	}

	public static final class QuiltEvent<S, D> extends Event<D> {
		private final org.quiltmc.qsl.base.api.event.Event<S> event;
		private final Function<D, S> listenerConverter;

		public QuiltEvent(org.quiltmc.qsl.base.api.event.Event<S> event, Function<D, S> listenerConverter, Function<Supplier<S>, D> invoker) {
			this.event = event;
			this.listenerConverter = listenerConverter;
			this.invoker = invoker.apply(event::invoker);
		}

		@Override
		public void register(D listener) {
			this.event.register(this.listenerConverter.apply(listener));
		}

		@Override
		public void register(Identifier phase, D listener) {
			this.event.register(quiltifyPhases(phase), this.listenerConverter.apply(listener));
		}

		@Override
		public void addPhaseOrdering(Identifier firstPhase, Identifier secondPhase) {
			this.event.addPhaseOrdering(quiltifyPhases(firstPhase), quiltifyPhases(secondPhase));
		}

		// This makes Fabric's default phase equal to the Quilt one, preventing ordering issues
		private static Identifier quiltifyPhases(Identifier phase) {
			return phase.equals(DEFAULT_PHASE) ? org.quiltmc.qsl.base.api.event.Event.DEFAULT_PHASE : phase;
		}
	}
}
