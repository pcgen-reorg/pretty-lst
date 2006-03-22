/**
 * AbilityFromTemplateChoiceManager.java
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Current Version: $Revision: 285 $
 * Last Editor:     $Author: nuance $
 * Last Edited:     $Date: 2006-03-17 15:19:49 +0000 (Fri, 17 Mar 2006) $
 *
 * Copyright 2006 Andrew Wilson <nuance@sourceforge.net>
 */
package pcgen.core.chooser;

import java.util.List;

import pcgen.core.AbilityInfo;
import pcgen.core.Categorisable;
import pcgen.core.PObject;
import pcgen.core.PlayerCharacter;
import pcgen.util.Logging;

/**
 * @author andrew
 *
 */
public class AbilityFromTemplateChoiceManager extends
		AbstractCategorisableChoiceManager {

	/**
	 * @param aPObject
	 * @param aPC
	 */
	public AbilityFromTemplateChoiceManager(
			PObject         aPObject,
			PlayerCharacter aPC)
	{
		super(aPObject, aPC);
	}

	/**
	 * Deliberately empty since the template constructs the AbilityStore Objects
	 * that will be passed to doChooser
	 * @param aPc
	 * @param availableList
	 * @param selectedList
	 */
	public void getChoices(
			PlayerCharacter aPc,
			List            availableList,
			List            selectedList)
	{
		return;
	}

	/**
	 * This is not used since the Template arranges to have the choices made
	 * here applied to itself.
	 * 
	 * @param aPC
	 * @param selected
	 * @param selectedBonusList
	 */
	public void applyChoices(
			PlayerCharacter aPC,
			List            selected,
			List            selectedBonusList)
	{
		return;
	}


	/**
	 * Convert the Categorisable Object to an AbilityInfo object.  If the
	 * AbilityInfo object refers to a valid Ability, add it to the choice
	 * maps.
	 *  
	 * @param categorisableObj
	 */
	protected void addToMaps(Categorisable categorisableObj)
	{
		AbilityInfo abInfo = (AbilityInfo) categorisableObj;
		
		if (abInfo.getAbility() != null)
		{
			AbilityInfo abI = (AbilityInfo) nameMap.put(abInfo.toString(), abInfo);
			catMap.put(abInfo.getCategory() + " " + abInfo.toString(), abInfo);
			
			if (abI != null) { useNameMap = false; }
		}
		else
		{
			Logging.errorPrint("attempt to add Non-existant Ability: cat = " + abInfo.getCategory() + ", name = "  + abInfo.getKeyName());
		}
		return;
	}

}