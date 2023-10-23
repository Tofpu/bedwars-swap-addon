package io.tofpu.bedwarsswapaddon.swap.wrapper;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImmutableConcurrectMap<K, V> extends ConcurrentHashMap<K, V> {
    public ImmutableConcurrectMap(final ConcurrentHashMap<K, V> map) {
        if (map == null) {
            return;
        }
        super.putAll(map);
    }

    @Override
    public V put(@NotNull final K key, @NotNull final V value) {
        throw new UnsupportedOperationException("You cannot modify an immutable map");
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("You cannot modify an immutable map");
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        throw new UnsupportedOperationException("You cannot modify an immutable map");
    }

    @Override
    public V remove(@NotNull final Object key) {
        throw new UnsupportedOperationException("You cannot modify an immutable map");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("You cannot modify an immutable map");
    }
}
