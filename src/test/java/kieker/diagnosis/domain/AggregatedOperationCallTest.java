/***************************************************************************
 * Copyright 2015 Kieker Project (http://kieker-monitoring.net)
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
 ***************************************************************************/

package kieker.diagnosis.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import kieker.diagnosis.domain.AggregatedOperationCall;
import kieker.diagnosis.domain.OperationCall;

import org.junit.Test;

public final class AggregatedOperationCallTest extends AbstractOperationCallTest<AggregatedOperationCall> {

	@Test
	public void constructorShouldCopySingleOperationCall() {
		final OperationCall call = new OperationCall("container", "component", "operation", 42);
		final AggregatedOperationCall aggregatedCall = new AggregatedOperationCall(call);

		assertThat(aggregatedCall.getContainer(), is("container"));
		assertThat(aggregatedCall.getComponent(), is("component"));
		assertThat(aggregatedCall.getOperation(), is("operation"));
	}

	@Test
	public void constructorShouldCopyNestedOperationCall() {
		final OperationCall call = new OperationCall("container", "component", "operation", 42);
		call.addChild(new OperationCall("container1", "component1", "operation1", 42));
		call.addChild(new OperationCall("container2", "component2", "operation2", 42));

		final AggregatedOperationCall aggregatedCall = new AggregatedOperationCall(call);

		assertThat(aggregatedCall.getContainer(), is("container"));
		assertThat(aggregatedCall.getComponent(), is("component"));
		assertThat(aggregatedCall.getOperation(), is("operation"));

		assertThat(aggregatedCall.getChildren().get(0).getContainer(), is("container1"));
		assertThat(aggregatedCall.getChildren().get(0).getComponent(), is("component1"));
		assertThat(aggregatedCall.getChildren().get(0).getOperation(), is("operation1"));

		assertThat(aggregatedCall.getChildren().get(1).getContainer(), is("container2"));
		assertThat(aggregatedCall.getChildren().get(1).getComponent(), is("component2"));
		assertThat(aggregatedCall.getChildren().get(1).getOperation(), is("operation2"));
	}

	@Test
	public void constructorShouldCopyStatistics() {
		final OperationCall call = new OperationCall("container", "component", "operation", 42);
		call.setStackSize(1);

		final AggregatedOperationCall aggregatedCall = new AggregatedOperationCall(call);

		assertThat(aggregatedCall.getStackSize(), is((Object) 1));
	}

	@Override
	protected AggregatedOperationCall createOperationCall(final String container, final String component, final String operation) {
		return new AggregatedOperationCall(new OperationCall(container, component, operation, -1));
	}

}
