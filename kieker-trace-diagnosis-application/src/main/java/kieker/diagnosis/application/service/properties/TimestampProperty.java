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

package kieker.diagnosis.application.service.properties;

import kieker.diagnosis.architecture.service.properties.AbstractEnumApplicationProperty;

import org.springframework.stereotype.Component;

/**
 * @author Nils Christian Ehmke
 */
@Component
public final class TimestampProperty extends AbstractEnumApplicationProperty<TimestampTypes> {

	public TimestampProperty( ) {
		super( TimestampTypes.class );
	}

	@Override
	public TimestampTypes getDefaultValue( ) {
		return TimestampTypes.TIMESTAMP;
	}

	@Override
	public String getKey( ) {
		return "timestampType";
	}

}