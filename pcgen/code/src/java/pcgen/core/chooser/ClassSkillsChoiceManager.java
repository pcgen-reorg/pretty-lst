/**
 * ClassSkillsChoiceManager.java
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

import pcgen.core.Ability;
import pcgen.core.Domain;
import pcgen.core.Globals;
import pcgen.core.PObject;
import pcgen.core.PlayerCharacter;
import pcgen.core.Skill;

import java.util.Iterator;
import java.util.List;

/**
 * This is the chooser that deals with choosing from among a PCs class skills.
 */
public class ClassSkillsChoiceManager extends AbstractComplexChoiceManager {

	/**
	 * Make a new Class Skills chooser.
	 *
	 * @param aPObject
	 * @param choiceString
	 * @param aPC
	 */
	public ClassSkillsChoiceManager(
			PObject         aPObject,
			String          choiceString,
			PlayerCharacter aPC)
	{
		super(aPObject, choiceString, aPC);
		title = "Skill Choice";
		chooserHandled = "CSKILLS";
		
		if (choices != null && choices.size() > 0 &&
				((String) choices.get(0)).equals(chooserHandled)) {
			choices.remove(0);
		}
	}

	
	/**
	 * Parse the Choice string and build a list of available choices.
	 * @param aPc
	 * @param availableList
	 * @param selectedList
	 */
	public void getChoices(
			final PlayerCharacter aPc,
			final List            availableList,
			final List            selectedList)
	{
		Iterator iter;
		Skill aSkill;

		for (iter = Globals.getSkillList().iterator(); iter.hasNext();)
		{
			aSkill = (Skill) iter.next();

			if (aSkill.costForPCClassList(aPc.getClassList(), aPc) == Globals.getGameModeSkillCost_Class())
			{
				availableList.add(aSkill.getName());
			}
		}

		pobject.addAssociatedTo(selectedList);
	}
	
	/**
	 * Apply the choices selected to the associated PObject (the one passed
	 * to the constructor)
	 * @param aPC
	 * @param selected
	 *
	 */
	public void applyChoices(
			PlayerCharacter  aPC,
			List             selected)
	{
		String objPrefix = "";

		if (pobject instanceof Domain)
		{
			objPrefix = chooserHandled + '?';
		}

		if (pobject instanceof Ability) {
			((Ability)pobject).clearSelectedWeaponProfBonus(); //Cleans up the feat
		}

		for (int i = 0; i < selected.size(); ++i)
		{
			final String chosenItem = (String) selected.get(i);

			if (multiples && !dupsAllowed)
			{
				if (!pobject.containsAssociated(objPrefix + chosenItem))
				{
					pobject.addAssociated(objPrefix + chosenItem);
				}
			}
			else
			{
				pobject.addAssociated(objPrefix + chosenItem);
			}

		}

		double featCount = aPC.getFeats();
		if (numberOfChoices > 0)
		{
			if (cost > 0)
			{
				featCount -= cost;
			}
		}
		else
		{
			if (cost > 0)
			{
				featCount = ((maxSelections - selected.size()) * cost);
			}
		}

		aPC.adjustFeats(featCount - aPC.getFeats());

		// This will get assigned by autofeat (if a feat)

		if (objPrefix.length() != 0)
		{
			aPC.setAutomaticFeatsStable(false);
		}
	}
	
}