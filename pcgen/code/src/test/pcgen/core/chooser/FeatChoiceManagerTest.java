/*
 * FeatChoiceManagerTest.java
 * Copyright 2005 (C) Andrew Wilson <nuance@sourceforge.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.     See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on Oct 7, 2005
 *
 * $Author$ 
 * $Date: 2006-03-26 08:00:03 +0100 (Sun, 26 Mar 2006) $
 * $Revision$
 *
 */
package pcgen.core.chooser;

import pcgen.AbstractCharacterTestCase;
import pcgen.core.PObject;
import pcgen.core.PlayerCharacter;
import pcgen.core.EquipmentList;
import pcgen.util.TestHelper;

import java.lang.Class;
import java.lang.reflect.Field;
import java.util.List;

/**
 * {@code FeatChoiceManagerTest} test that the FeatChoiceManager class is functioning correctly.
 *
 * @author Andrew Wilson <nuance@sourceforge.net>
 */

public class FeatChoiceManagerTest extends AbstractCharacterTestCase {

	/**
	 * Constructs a new {@code FeatChoiceManagerTest}.
	 */
	public FeatChoiceManagerTest()
	{
		// Do Nothing
	}


	protected void setUp() throws Exception
	{
		super.setUp();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
		EquipmentList.clearEquipmentMap();
	}

	/**
	 * Test the constructor 
	 */
	public void test001()
	{
		PObject pObj = new PObject();
		pObj.setName("My PObject");
		pObj.setChoiceString("FEAT=Wellie Throwing");
		is(pObj.getChoiceString(), strEq("FEAT=Wellie Throwing"));

		PlayerCharacter aPC  = getCharacter();
		
		ChoiceManagerList choiceManager = ChooserUtilities.getChoiceManager(pObj, null, aPC);
		is(choiceManager, not(eq(null)), "Found the chooser");

		is(choiceManager.typeHandled(), strEq("FEAT"), "got expected chooser");
		
		try
		{
			Class cMClass = choiceManager.getClass();

			Field aField  = (Field) TestHelper.findField(cMClass, "requestedSelections");
			is (aField.get(choiceManager), eq(0));
			
			aField  = (Field) TestHelper.findField(cMClass, "choices");
			List choices = (List) aField.get(choiceManager);
			is (new Integer (choices.size()), eq(1));
			is (choices.get(0), strEq("Wellie Throwing"));
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}
	}

}