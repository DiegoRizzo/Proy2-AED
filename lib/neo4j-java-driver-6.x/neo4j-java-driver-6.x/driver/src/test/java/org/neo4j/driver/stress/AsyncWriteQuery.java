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
package org.neo4j.driver.stress;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletionStage;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.async.ResultCursor;
import org.neo4j.driver.internal.util.Futures;

public class AsyncWriteQuery<C extends AbstractContext> extends AbstractAsyncQuery<C> {
    private final AbstractStressTestBase<C> stressTest;

    AsyncWriteQuery(AbstractStressTestBase<C> stressTest, Driver driver, boolean useBookmark) {
        super(driver, useBookmark);
        this.stressTest = stressTest;
    }

    @Override
    public CompletionStage<Void> execute(C context) {
        var session = newSession(AccessMode.WRITE, context);

        return session.runAsync("CREATE ()")
                .thenCompose(ResultCursor::consumeAsync)
                .handle((summary, error) -> {
                    session.closeAsync();

                    if (error != null) {
                        handleError(Futures.completionExceptionCause(error), context);
                    } else {
                        context.setBookmark(session.lastBookmarks());
                        assertEquals(1, summary.counters().nodesCreated());
                        context.nodeCreated();
                    }

                    return null;
                });
    }

    private void handleError(Throwable error, C context) {
        if (!stressTest.handleWriteFailure(error, context)) {
            throw new RuntimeException(error);
        }
    }
}
