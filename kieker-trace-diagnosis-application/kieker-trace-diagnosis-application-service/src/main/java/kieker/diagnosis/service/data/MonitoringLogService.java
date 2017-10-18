/*************************************************************************** 
 * Copyright 2015-2017 Kieker Project (http://kieker-monitoring.net)         
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

package kieker.diagnosis.service.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Singleton;

import kieker.diagnosis.architecture.exception.BusinessException;
import kieker.diagnosis.architecture.exception.TechnicalException;
import kieker.diagnosis.architecture.service.ServiceBase;

@Singleton
public class MonitoringLogService extends ServiceBase {

	private final List<MethodCall> ivTraceRoots = new ArrayList<>( );
	private final List<AggregatedMethodCall> ivAggreatedMethods = new ArrayList<>( );
	private final List<MethodCall> ivMethods = new ArrayList<>( );
	private long ivProcessDuration;
	private long ivProcessedBytes;
	private boolean dataAvailable = false;
	private int ivIgnoredRecords;
	private int ivDanglingRecords;
	private int ivIncompleteTraces;
	private String ivDirectory;

	public MonitoringLogService( ) {
		// This is one of the few services that is allowed to store a state
		super( false );
	}

	public void importMonitoringLog( final File aDirectory ) throws BusinessException {
		try {
			clear( );

			// We use a helper class to avoid having temporary fields in the service
			final MonitoringLogImporter importer = new MonitoringLogImporter( );
			importer.importMonitoringLog( aDirectory, this );

			setDataAvailable( aDirectory );
		} catch ( final BusinessException ex ) {
			// A technical exception means, that something went wrong, but that the data is partially available
			setDataAvailable( aDirectory );

			throw ex;
		} catch ( final Exception ex ) {
			throw new TechnicalException( getLocalizedString( "errorMessageImportFailed" ), ex );
		}
	}

	private void setDataAvailable( final File aDirectory ) {
		ivDirectory = aDirectory.getAbsolutePath( );
		dataAvailable = true;
	}

	private void clear( ) {
		dataAvailable = false;
		ivTraceRoots.clear( );
		ivAggreatedMethods.clear( );
		ivMethods.clear( );
	}

	void addTraceRoot( final MethodCall aTraceRoot ) {
		ivTraceRoots.add( aTraceRoot );
	}

	void addAggregatedMethods( final Collection<AggregatedMethodCall> aAggregatedMethodCalls ) {
		ivAggreatedMethods.addAll( aAggregatedMethodCalls );
	}

	void addMethods( final Collection<MethodCall> aMethodCalls ) {
		ivMethods.addAll( aMethodCalls );
	}

	void setProcessDuration( final long aProcessDuration ) {
		ivProcessDuration = aProcessDuration;
	}

	void setProcessedBytes( final long aProcessedBytes ) {
		ivProcessedBytes = aProcessedBytes;
	}

	public List<MethodCall> getTraceRoots( ) {
		return ivTraceRoots;
	}

	public List<AggregatedMethodCall> getAggreatedMethods( ) {
		return ivAggreatedMethods;
	}

	public List<MethodCall> getMethods( ) {
		return ivMethods;
	}

	public long getProcessDuration( ) {
		return ivProcessDuration;
	}

	public long getProcessedBytes( ) {
		return ivProcessedBytes;
	}

	public boolean isDataAvailable( ) {
		return dataAvailable;
	}

	public int getIgnoredRecords( ) {
		return ivIgnoredRecords;
	}

	void setIgnoredRecords( final int aIgnoredRecords ) {
		ivIgnoredRecords = aIgnoredRecords;
	}

	public int getDanglingRecords( ) {
		return ivDanglingRecords;
	}

	void setDanglingRecords( final int aDanglingRecords ) {
		ivDanglingRecords = aDanglingRecords;
	}

	public int getIncompleteTraces( ) {
		return ivIncompleteTraces;
	}

	void setIncompleteTraces( final int aIncompleteTraces ) {
		ivIncompleteTraces = aIncompleteTraces;
	}

	void setDataAvailable( final boolean aDataAvailable ) {
		dataAvailable = aDataAvailable;
	}

	public String getDirectory( ) {
		return ivDirectory;
	}

}
