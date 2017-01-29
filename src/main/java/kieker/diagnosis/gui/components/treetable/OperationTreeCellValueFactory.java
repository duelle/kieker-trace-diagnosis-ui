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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import kieker.diagnosis.service.ServiceUtil;
import kieker.diagnosis.service.nameconverter.NameConverterService;
import kieker.diagnosis.service.properties.PropertiesService;
import kieker.diagnosis.service.properties.PropertiesService.OperationNames;

/**
 * @author Nils Christian Ehmke
 */
public final class OperationTreeCellValueFactory implements Callback<CellDataFeatures<?, String>, ObservableValue<String>> {

	private static final Logger LOGGER = LogManager.getLogger( DurationTreeCellValueFactory.class );

	private final NameConverterService ivNameConverterService = ServiceUtil.getService( NameConverterService.class );
	private final PropertiesService ivPropertiesService = ServiceUtil.getService( PropertiesService.class );

	private final String ivProperty;

	public OperationTreeCellValueFactory( @NamedArg ( value = "property" ) final String aProperty ) {
		ivProperty = aProperty.substring( 0, 1 ).toUpperCase( Locale.ROOT ) + aProperty.substring( 1 );
	}

	@Override
	public ObservableValue<String> call( final CellDataFeatures<?, String> aCall ) {
		try {
			final TreeItem<?> item = aCall.getValue( );
			final Method getter = item.getValue( ).getClass( ).getMethod( "get" + ivProperty, new Class<?>[0] );
			String operationName = (String) getter.invoke( item.getValue( ), new Object[0] );

			if ( ivPropertiesService.getOperationNames( ) == OperationNames.SHORT ) {
				operationName = ivNameConverterService.toShortOperationName( operationName );
			}

			return new ReadOnlyObjectWrapper<>( operationName );
		}
		catch ( final NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex ) {
			OperationTreeCellValueFactory.LOGGER.warn( ex );
			return null;
		}
	}

}