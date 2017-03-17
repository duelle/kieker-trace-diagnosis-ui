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

package kieker.diagnosis.application.service.export;

import kieker.diagnosis.architecture.exception.TechnicalException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Component;

/**
 * @author Nils Christian Ehmke
 */
@Component
class ExportServiceImpl implements ExportService {

	private static final char COMMENT_LINES_PREFIX = '#';
	private static final char COLUMN_SEPARATOR = ';';
	private static final String cvLineSeparator = System.getProperty( "line.separator" );

	@Override
	public void exportToCSV( final CSVData aCSVData, final File aFile ) {
		try {
			final boolean fileCreated = aFile.createNewFile( );

			if ( !fileCreated ) {
				throw new IOException( String.format( "Could not create file '%s'", aFile.getName( ) ) );
			}

			try ( final OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream( aFile ), "UTF-8" ) ) {
				writer.write( COMMENT_LINES_PREFIX );

				for ( final String header : aCSVData.getHeader( ) ) {
					writer.write( header );
					writer.write( COLUMN_SEPARATOR );
				}

				writer.write( cvLineSeparator );

				for ( final String[] row : aCSVData.getRows( ) ) {
					for ( final String column : row ) {
						if ( column != null ) {
							writer.write( column );
						}
						writer.write( COLUMN_SEPARATOR );
					}
					writer.write( cvLineSeparator );
				}
			}
		} catch ( final IOException ex ) {
			throw new TechnicalException( ex );
		}
	}

}