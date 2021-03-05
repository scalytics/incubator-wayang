/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wayang.api.python;

import org.apache.wayang.api.python.function.WrappedPythonFunction;
import org.apache.wayang.basic.operators.*;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.core.function.FlatMapDescriptor;
import org.apache.wayang.core.function.MapPartitionsDescriptor;
import org.apache.wayang.core.function.TransformationDescriptor;
import org.apache.wayang.core.plan.wayangplan.WayangPlan;
import org.apache.wayang.core.types.DataUnitType;
import org.apache.wayang.java.Java;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class BasicPython {

    public static URI createUri(String resourcePath) {
        try {
            return Thread.currentThread().getClass().getResource(resourcePath).toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal URI.", e);
        }

    }

    @Test
    public void testOnJava() throws URISyntaxException, IOException {

        URI FILE_SOME_LINES_TXT = createUri("/python-lines.txt");

        // Instantiate Rheem and activate the backend.
        WayangContext rheemContext = new WayangContext().with(Java.basicPlugin());
        TextFileSource textFileSource = new TextFileSource(FILE_SOME_LINES_TXT.toString());

        TextFileSink<String> sink = new TextFileSink<String>(
                "file:///Users/rodrigopardomeza/wayang/incubator-wayang/wayang-api/wayang-api-python/src/main/resources/basic_output.txt",
                String.class
        );

        textFileSource.connectTo(0, sink, 0);

        rheemContext.execute(new WayangPlan(sink));
    }
}

