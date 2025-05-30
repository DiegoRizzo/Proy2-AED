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
package org.neo4j.driver.internal;

import static java.util.Collections.singleton;

import java.io.Serial;
import java.util.Set;
import org.neo4j.driver.net.ServerAddress;
import org.neo4j.driver.net.ServerAddressResolver;

public class IdentityResolver implements ServerAddressResolver {
    public static final IdentityResolver IDENTITY_RESOLVER = new IdentityResolver();

    @Serial
    private static final long serialVersionUID = -881546487990290715L;

    private IdentityResolver() {}

    @Override
    public Set<ServerAddress> resolve(ServerAddress initialRouter) {
        return singleton(initialRouter);
    }
}
