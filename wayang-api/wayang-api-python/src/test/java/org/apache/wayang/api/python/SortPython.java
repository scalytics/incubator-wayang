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

public class SortPython {

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

        // for each line (input) output an iterator of the words
        FlatMapOperator<String, String> flatMapOperator
                = new FlatMapOperator<>(
                new FlatMapDescriptor<>(
                        line -> Arrays.asList(
                                (String[]) line.split(" ")
                        ),
                        String.class,
                        String.class
                )
        );

        SortOperator<String, String> sortJava
                = new SortOperator<String, String>(
                new TransformationDescriptor<String, String>(
                        (l) -> l.toLowerCase(),
                        DataUnitType.createBasic(String.class),
                        DataUnitType.createBasic(String.class)
                )
        );

        MapPartitionsOperator<String, String> sortPython =
                new MapPartitionsOperator<String, String>(
                        new MapPartitionsDescriptor<String, String>(
                                new WrappedPythonFunction<String, String>(
                                        l -> l
                                ),
                                String.class,
                                String.class
                        )
                );

        //URI outputfile = Thread.currentThread().getClass().getResource("/output.txt").toURI();

        TextFileSink<String> sink = new TextFileSink<String>(
                "file:///Users/rodrigopardomeza/wayang/incubator-wayang/wayang-api/wayang-api-python/src/main/resources/output.txt",
                String.class
        );

        textFileSource.connectTo(0, flatMapOperator, 0);
        flatMapOperator.connectTo(0, sortPython, 0);
        sortPython.connectTo(0, sink, 0);

        rheemContext.execute(new WayangPlan(sink));
    }
}

