/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [https://neo4j.com]
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
package org.neo4j.driver.internal.value;

import static org.neo4j.driver.Values.ofObject;
import static org.neo4j.driver.Values.ofValue;
import static org.neo4j.driver.internal.util.Format.formatPairs;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.function.Function;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.value.Uncoercible;
import org.neo4j.driver.internal.types.InternalTypeSystem;
import org.neo4j.driver.internal.util.Extract;
import org.neo4j.driver.types.Type;

public class MapValue extends ValueAdapter {
    private final Map<String, Value> val;

    public MapValue(Map<String, Value> val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot construct MapValue from null");
        }
        this.val = val;
    }

    @Override
    public boolean isEmpty() {
        return val.isEmpty();
    }

    @Override
    public Map<String, Object> asObject() {
        return asMap(ofObject());
    }

    @Override
    public <T> T as(Class<T> targetClass) {
        if (targetClass.isAssignableFrom(Map.class)) {
            return targetClass.cast(asMap());
        }
        return asMapped(targetClass);
    }

    @Override
    public Object as(ParameterizedType type) {
        var rawType = type.getRawType();
        if (rawType instanceof Class<?> cls) {
            if (cls.isAssignableFrom(Map.class)) {
                var keyType = type.getActualTypeArguments()[0];
                if (keyType instanceof Class<?> keyCls) {
                    if (keyCls.isAssignableFrom(String.class)) {
                        var valueType = type.getActualTypeArguments()[1];
                        return asMap(v -> {
                            var value = (InternalValue) v;
                            return value.as(valueType);
                        });
                    }
                }
            }
        }
        throw new Uncoercible(type().name(), type.toString());
    }

    @Override
    public Map<String, Object> asMap() {
        return Extract.map(val, ofObject());
    }

    @Override
    public <T> Map<String, T> asMap(Function<Value, T> mapFunction) {
        return Extract.map(val, mapFunction);
    }

    @Override
    public int size() {
        return val.size();
    }

    @Override
    public boolean containsKey(String key) {
        return val.containsKey(key);
    }

    @Override
    public Iterable<String> keys() {
        return val.keySet();
    }

    @Override
    public Iterable<Value> values() {
        return val.values();
    }

    @Override
    public <T> Iterable<T> values(Function<Value, T> mapFunction) {
        return Extract.map(val, mapFunction).values();
    }

    @Override
    public Value get(String key) {
        var value = val.get(key);
        return value == null ? Values.NULL : value;
    }

    @Override
    public String toString() {
        return formatPairs(asMap(ofValue()));
    }

    @Override
    public Type type() {
        return InternalTypeSystem.TYPE_SYSTEM.MAP();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var values = (MapValue) o;
        return val.equals(values.val);
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public BoltValue asBoltValue() {
        return new BoltValue(this, org.neo4j.bolt.connection.values.Type.MAP);
    }
}
