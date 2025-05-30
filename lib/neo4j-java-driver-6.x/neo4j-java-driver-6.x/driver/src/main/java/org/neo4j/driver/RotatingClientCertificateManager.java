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
package org.neo4j.driver;

/**
 * A {@link ClientCertificateManager} that supports rotating its {@link ClientCertificate}.
 * @since 5.19
 */
public sealed interface RotatingClientCertificateManager extends ClientCertificateManager
        permits org.neo4j.driver.internal.InternalRotatingClientCertificateManager {
    /**
     * Rotates the current {@link ClientCertificate}.
     * @param clientCertificate the new certificate, must not be {@literal null}
     */
    void rotate(ClientCertificate clientCertificate);
}
