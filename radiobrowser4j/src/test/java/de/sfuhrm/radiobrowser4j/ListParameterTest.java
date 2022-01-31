/*
* Copyright 2017 Stephan Fuhrmann
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
package de.sfuhrm.radiobrowser4j;

import org.hamcrest.collection.IsArrayWithSize;
import org.junit.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Test for the ListParameter class.
 * @author Stephan Fuhrmann
 */
public class ListParameterTest {

    @Test
    public void create() {
        OrderParameter orderParameter = OrderParameter.create();

        assertThat(orderParameter, is(not(nullValue())));
    }

    @Test
    public void applyToWithNothingSet() {
        OrderParameter orderParameter = OrderParameter.create();
        MultivaluedMap<String,String> multivaluedMap = new MultivaluedHashMap<>();
        orderParameter.applyTo(multivaluedMap);

        assertThat(multivaluedMap.keySet().toArray(), is(IsArrayWithSize.emptyArray()));
    }

    @Test
    public void applyToWithOrderSet() {
        OrderParameter orderParameter = OrderParameter.create();
        orderParameter.order(FieldName.BITRATE);
        MultivaluedMap<String,String> multivaluedMap = new MultivaluedHashMap<>();
        orderParameter.applyTo(multivaluedMap);

        assertThat(multivaluedMap.keySet(),     is(new HashSet<>(Collections.singletonList("order"))));
        assertThat(multivaluedMap.get("order"),  is(Collections.singletonList("bitrate")));
    }

    @Test
    public void applyToWithReverseSet() {
        OrderParameter orderParameter = OrderParameter.create();
        orderParameter.reverseOrder(true);
        MultivaluedMap<String,String> multivaluedMap = new MultivaluedHashMap<>();
        orderParameter.applyTo(multivaluedMap);

        assertThat(multivaluedMap.keySet(),     is(new HashSet<>(Collections.singletonList("reverse"))));
        assertThat(multivaluedMap.get("reverse"),  is(Collections.singletonList("true")));
    }

    @Test
    public void applyToWithOrderAndReverseSet() {
        OrderParameter orderParameter = OrderParameter.create();
        orderParameter.order(FieldName.CODEC);
        orderParameter.reverseOrder(true);
        MultivaluedMap<String,String> multivaluedMap = new MultivaluedHashMap<>();
        orderParameter.applyTo(multivaluedMap);

        assertThat(multivaluedMap.keySet(),     is(new HashSet<>(Arrays.asList("reverse", "order"))));
        assertThat(multivaluedMap.get("reverse"),  is(Collections.singletonList("true")));
        assertThat(multivaluedMap.get("order"),  is(Collections.singletonList("codec")));
    }
}
