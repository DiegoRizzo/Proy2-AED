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

import org.neo4j.driver.internal.types.InternalTypeSystem;
import org.neo4j.driver.types.IsoDuration;
import org.neo4j.driver.types.Type;

public class DurationValue extends ObjectValueAdapter<IsoDuration> {
    public DurationValue(IsoDuration duration) {
        super(duration);
    }

    @Override
    public IsoDuration asIsoDuration() {
        return asObject();
    }

    @Override
    public Type type() {
        return InternalTypeSystem.TYPE_SYSTEM.DURATION();
    }

    @Override
    public BoltValue asBoltValue() {
        return new BoltValue(this, org.neo4j.bolt.connection.values.Type.DURATION);
    }

    @Override
    public <T> T as(Class<T> targetClass) {
        if (targetClass.isAssignableFrom(IsoDuration.class)) {
            return targetClass.cast(asIsoDuration());
        }
        return asMapped(targetClass);
    }
}
