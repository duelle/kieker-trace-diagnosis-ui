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

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.util.StringConverter;

import kieker.diagnosis.util.Mapper;

/**
 * @author Nils Christian Ehmke
 * 
 * @param <T>
 *            The type of the objects to convert from and to {@link String}.
 */
public abstract class AbstractStringConverter<T> extends StringConverter<T> {

	private final Mapper<T, String> ivMapper = new Mapper<>();

	public AbstractStringConverter() {
		final String bundleBaseName = "locale.kieker.diagnosis.components.components";
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleBaseName, Locale.getDefault());

		this.fillMapper(this.ivMapper, resourceBundle);
	}

	protected abstract void fillMapper(final Mapper<T, String> aMapper, final ResourceBundle aResourceBundle);

	@Override
	public final T fromString(final String aString) {
		return this.ivMapper.invertedResolve(aString);
	}

	@Override
	public final String toString(final T aObject) {
		return this.ivMapper.resolve(aObject);
	}

}