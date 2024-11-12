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

package org.apache.wayang.flink.compiler.criterion;

import org.apache.flink.api.common.aggregators.Aggregator;
import org.apache.flink.types.ListValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Class create a {@link Aggregator} that generate aggregatorWrapper
 */
public class WayangAggregator implements Aggregator<ListValue<WayangValue>> {
    private List<WayangValue> elements;

    public WayangAggregator(){
        this.elements = new ArrayList<>();
    }

    @Override
    public ListValue<WayangValue> getAggregate() {
        return new WayangListValue(this.elements);
    }

    @Override
    public void aggregate(ListValue wayangValues) {
        this.elements.addAll(wayangValues);
    }


    public void aggregate(Object t){
        this.elements.add(new WayangValue(t));
    }

    @Override
    public void reset() {
        this.elements.clear();
    }
}
