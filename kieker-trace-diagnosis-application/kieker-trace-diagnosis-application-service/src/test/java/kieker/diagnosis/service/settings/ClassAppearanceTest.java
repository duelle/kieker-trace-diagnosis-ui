/***************************************************************************
 * Copyright 2015-2018 Kieker Project (http://kieker-monitoring.net)
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

package kieker.diagnosis.service.settings;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test class for the {@link ClassAppearance}.
 *
 * @author Nils Christian Ehmke
 */
public final class ClassAppearanceTest {

	@Test
	public void testConvertWithNullValue( ) {
		assertThat( ClassAppearance.SHORT.convert( null ), is( nullValue( ) ) );
		assertThat( ClassAppearance.LONG.convert( null ), is( nullValue( ) ) );
	}

	@Test
	public void testConvertWithLongAppearance( ) {
		assertThat( ClassAppearance.LONG.convert( "A.B.C" ), is( "A.B.C" ) );
		assertThat( ClassAppearance.LONG.convert( "A" ), is( "A" ) );
	}

	@Test
	public void testConvertWithShortAppearance( ) {
		assertThat( ClassAppearance.SHORT.convert( "A.B.C" ), is( "C" ) );
		assertThat( ClassAppearance.SHORT.convert( "A" ), is( "A" ) );
	}

}
