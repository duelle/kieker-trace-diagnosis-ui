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

package kieker.diagnosis.mainview.subview.aggregatedtraces.util;

import kieker.diagnosis.domain.AggregatedTrace;
import kieker.diagnosis.mainview.subview.util.AbstractTraceTreeColumnSortListener;

public final class MaxDurationSortListener extends AbstractTraceTreeColumnSortListener<AggregatedTrace> {

	private static final long serialVersionUID = 1L;

	@Override
	protected int compare(final AggregatedTrace fstTrace, final AggregatedTrace sndTrace) {
		final long fstMaxDuration = fstTrace.getRootOperationCall().getMaxDuration();
		final long sndMaxDuration = sndTrace.getRootOperationCall().getMaxDuration();

		return Long.compare(fstMaxDuration, sndMaxDuration);
	}

}