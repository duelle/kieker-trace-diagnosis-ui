/***************************************************************************
 * Copyright 2015-2016 Kieker Project (http://kieker-monitoring.net)
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

package kieker.diagnosis.gui.components.treetable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.scene.control.TreeItem;
import kieker.diagnosis.service.ServiceUtil;
import kieker.diagnosis.service.data.domain.OperationCall;
import kieker.diagnosis.service.properties.PropertiesService;

/**
 * @author Nils Christian Ehmke
 */
public final class LazyOperationCallTreeItem extends AbstractLazyOperationCallTreeItem<OperationCall> {

	private static final String METHOD_CALLS_AGGREGATED;
	private static final String UNMONITORED_TIME;

	private final PropertiesService ivPropertiesService = ServiceUtil.getService( PropertiesService.class );

	static {
		final String bundleBaseName = "kieker.diagnosis.gui.components.components";
		final ResourceBundle resourceBundle = ResourceBundle.getBundle( bundleBaseName, Locale.getDefault( ) );

		METHOD_CALLS_AGGREGATED = resourceBundle.getString( "methodCallsAggregated" );
		UNMONITORED_TIME = resourceBundle.getString( "unmonitoredTime" );
	}

	public LazyOperationCallTreeItem( final OperationCall aValue ) {
		super( aValue );
	}

	@Override
	protected void initializeChildren( ) {
		final List<TreeItem<OperationCall>> result = new ArrayList<>( );

		if ( ivPropertiesService.isShowUnmonitoredTime( ) ) {
			double percent = ivPropertiesService.isPercentageCalculationActive( ) ? getValue( ).getPercent( ) : 100.0;
			long duration = getValue( ).getDuration( );
			for ( final OperationCall child : getValue( ).getChildren( ) ) {
				percent -= child.getPercent( );
				duration -= child.getDuration( );
			}

			final OperationCall call = new OperationCall( "-", "-", UNMONITORED_TIME, getValue( ).getTraceID( ), getValue( ).getTimestamp( ) );
			call.setPercent( (float) percent );
			call.setDuration( duration );
			result.add( new LazyOperationCallTreeItem( call ) );
		}

		if ( ivPropertiesService.isMethodCallAggregationActive( ) ) {
			final float threshold = ivPropertiesService.getThreshold( ).getPercent( );
			final List<OperationCall> underThreshold = new ArrayList<>( );
			for ( final OperationCall child : getValue( ).getChildren( ) ) {
				if ( child.getPercent( ) < threshold ) {
					underThreshold.add( child );
				} else {
					result.add( new LazyOperationCallTreeItem( child ) );
				}
			}
			if ( !underThreshold.isEmpty( ) ) {
				final double percent = underThreshold.stream( ).map( OperationCall::getPercent ).collect( Collectors.summingDouble( Float::doubleValue ) );
				final long duration = underThreshold.stream( ).map( OperationCall::getDuration ).collect( Collectors.summingLong( Long::longValue ) );
				final int traceDepth = underThreshold.stream( ).map( OperationCall::getStackDepth ).max( Comparator.naturalOrder( ) ).get( );
				final int traceSize = underThreshold.stream( ).map( OperationCall::getStackSize ).collect( Collectors.summingInt( Integer::intValue ) );
				final OperationCall call = new OperationCall( "-", "-", underThreshold.size( ) + " " + LazyOperationCallTreeItem.METHOD_CALLS_AGGREGATED,
						getValue( ).getTraceID( ), -1 );
				call.setPercent( (float) percent );
				call.setDuration( duration );
				call.setStackDepth( traceDepth );
				call.setStackSize( traceSize );
				result.add( new LazyOperationCallTreeItem( call ) );
			}

		} else {
			for ( final OperationCall child : getValue( ).getChildren( ) ) {
				result.add( new LazyOperationCallTreeItem( child ) );
			}
		}

		super.getChildren( ).setAll( result );
	}
}