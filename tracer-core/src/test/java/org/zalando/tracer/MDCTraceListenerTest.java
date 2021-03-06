package org.zalando.tracer;

/*
 * ⁣​
 * Tracer: SLF4J
 * ⁣⁣
 * Copyright (C) 2015 Zalando SE
 * ⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ​⁣
 */

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.slf4j.MDC;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

// TODO is this thread safe?
public final class MDCTraceListenerTest {

    @Rule
    public final TestRule mdc = new MDCRule();

    private final Tracer unit = Tracer.builder()
            .trace("X-Trace-ID", () -> "52aecbf6-73e1-11e5-b508-10ddb1ee7671")
            .listener(new MDCTraceListener())
            .build();

    @Test
    public void shouldNotBeSetUntilStarted() {
        assertThat(MDC.get("X-Trace-ID"), is(nullValue()));
    }

    @Test
    public void shouldBeSetAfterStart() {
        unit.start();

        assertThat(MDC.get("X-Trace-ID"), is("52aecbf6-73e1-11e5-b508-10ddb1ee7671"));
    }

    @Test
    public void shouldNotBeSetAfterStop() {
        unit.start();
        unit.stop();

        assertThat(MDC.get("X-Trace-ID"), is(nullValue()));
    }

}