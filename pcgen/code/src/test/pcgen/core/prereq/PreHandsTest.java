/*
 * PreHandsTest.java
 *
 * Copyright 2006 (C) Aaron Divinsky <boomer70@yahoo.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	   See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 */
package pcgen.core.prereq;

import junit.framework.Test;
import junit.framework.TestSuite;
import pcgen.AbstractCharacterTestCase;
import pcgen.core.PlayerCharacter;
import pcgen.persistence.lst.prereq.PreParserFactory;
import pcgen.core.Race;

/**
 * <code>PreHandsTest</code> tests that the PREHANDS tag is
 * working correctly.
 *
 * Last Editor: $Author: $
 * Last Edited: $Date$
 *
 * @author Aaron Divinsky <boomer70@yahoo.com>
 * @version $Revision$
 */
public class PreHandsTest extends AbstractCharacterTestCase
{
	public static void main(final String[] args)
	{
		junit.swingui.TestRunner.run(PreHandsTest.class);
	}

	/**
	 * @return Test
	 */
	public static Test suite()
	{
		return new TestSuite(PreHandsTest.class);
	}

	/**
	 * Test the PREHANDS code
	 * @throws Exception
	 */
	public void testHands()
		throws Exception
	{
		final PlayerCharacter character = getCharacter();
		Race race = new Race();
		race.setHands(2);

		character.setRace(race);

		Prerequisite prereq;

		final PreParserFactory factory = PreParserFactory.getInstance();
		prereq = factory.parse("PREHANDSLT:2");

		assertFalse("Character has more than 1 hand",
				   PrereqHandler.passes(prereq, character, null));

		prereq = factory.parse("PREHANDSEQ:2");

		assertTrue("Character has 2 hands",
				   PrereqHandler.passes(prereq, character, null));

		prereq = factory.parse("PREHANDSGT:2");

		assertFalse("Character does not have more than 2 hands",
					PrereqHandler.passes(prereq, character, null));
	}
}