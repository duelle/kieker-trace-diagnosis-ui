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

package kieker.diagnosis.components.converter;

import java.util.ResourceBundle;

import kieker.diagnosis.model.PropertiesModel.ComponentNames;
import kieker.diagnosis.util.Mapper;

/**
 * @author Nils Christian Ehmke
 */
public final class ComponentNamesStringConverter extends AbstractStringConverter<ComponentNames> {

	@Override
	protected void fillMapper(final Mapper<ComponentNames, String> aMapper, final ResourceBundle aResourceBundle) {
		final String shortStr = aResourceBundle.getString("short");
		final String longStr = aResourceBundle.getString("long");

		aMapper.map(ComponentNames.SHORT).to(shortStr + " (Catalog)");
		aMapper.map(ComponentNames.LONG).to(longStr + " (kieker.examples.bookstore.Catalog)");
	}

}