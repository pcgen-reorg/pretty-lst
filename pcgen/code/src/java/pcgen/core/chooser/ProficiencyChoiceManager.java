/**
 * ProficiencyChoiceManager.java
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
 * Current Version: $Revision$
 * Last Editor:     $Author$
 * Last Edited:     $Date: 2006-03-17 15:19:49 +0000 (Fri, 17 Mar 2006) $
 *
 * Copyright 2006 Andrew Wilson <nuance@sourceforge.net>
 */
package pcgen.core.chooser;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pcgen.core.Globals;
import pcgen.core.PObject;
import pcgen.core.PlayerCharacter;
import pcgen.core.WeaponProf;
import pcgen.core.utils.ListKey;
import pcgen.util.Logging;

/**
 * This is the chooser that deals with choosing a Weapon Proficiency
 */
public class ProficiencyChoiceManager extends AbstractComplexChoiceManager
{
	final static int SCOPE_PC		= 0;
	final static int SCOPE_ALL		= 1;
	final static int SCOPE_UNIQUE	= 2;
	int              intScope		= -1;
	String           typeOfProf		= "";

	/**
	 * Make a new Weapon Proficiency chooser.
	 *
	 * @param aPObject
	 * @param choiceString
	 * @param aPC
	 */
	public ProficiencyChoiceManager(
			PObject         aPObject,
			String          choiceString,
			PlayerCharacter aPC)
	{
		super(aPObject, choiceString, aPC);
		title = "Choose Proficiency";
		chooserHandled = "PROFICIENCY";

		if (choices != null && choices.size() > 0 &&
				((String) choices.get(0)).equals(chooserHandled)) {
			choices = choices.subList(1, choices.size());
		}
		
		if (choices.size() < 3)
		{
			Logging.errorPrint("CHOOSE:PROFICIENCY - Incorrect format, not enough tokens. " + choiceString);
		}
		else
		{
			typeOfProf = (String) choices.get(0);

			if ("PC".equals(choices.get(1)))
			{
				intScope = SCOPE_PC;
			}
			else if ("ALL".equals(choices.get(1)))
			{
				intScope = SCOPE_ALL;
			}
			else if ("UNIQUE".equals(choices.get(1)))
			{
				intScope = SCOPE_UNIQUE;
			}
			else
			{
				Logging.errorPrint("CHOOSE:PROFICIENCY - Unknown scope " + choices.get(1));
			}
		}
	}

	/**
	 * Parse the Choice string and build a list of available choices.
	 *	 CHOOSE:PROFICIENCY|<Type of Prof>|<scope>|<list of profs>
	 * Type of Prof = WEAPON, ARMOR, SHIELD
	 * scope = PC (proficiencies already possessed by PC), ALL (all profs of type), UNIQUE (all profs not already possessed by PC)
	 * list of profs = Either a list of specific profs or a prof TYPE
	 * XXX Note that ARMOR and SHIELD don't work at the moment since I can't get a list of
	 * armor or weapon proficiencies.
	 *
	 * @param aPc
	 * @param availableList
	 * @param selectedList
	 */
	public void getChoices(
			final PlayerCharacter aPc,
			final List            availableList,
			final List            selectedList)
	{
		Iterator It = choices.subList(2, choices.size()).iterator();
		if ("WEAPON".equals(typeOfProf))
		{
			Set profStrings = new TreeSet();
			while (It.hasNext())
			{
				final String prof = (String) It.next();
				if (prof.startsWith("TYPE.") || prof.startsWith("TYPE="))
				{
					String typeString = prof.substring(5);
					for (Iterator i = Globals.getWeaponProfs(typeString, aPc).iterator();i.hasNext();)
					{
						profStrings.add(i.next().toString());
					}
				}
				else
				{
					WeaponProf aProf = Globals.getWeaponProfNamed(prof);
					if (aProf != null)
					{
						profStrings.add(aProf.toString());
					}
				}
			}
			
			if (intScope == SCOPE_ALL)
			{
				availableList.addAll(profStrings);
			}
			else
			{
				
				Set pcProfs = aPc.getWeaponProfList();
				Set pcProfStrings = new TreeSet();
				
				for (Iterator profIt = pcProfs.iterator(); profIt.hasNext();)
				{
					pcProfStrings.add(profIt.next());
				}
				
				if (intScope == SCOPE_PC)
				{
					profStrings.retainAll(pcProfStrings);
					availableList.addAll(profStrings);
				}
				else if (intScope == SCOPE_UNIQUE)
				{
					
					// Get a new set which is the intersection of all the Weapon profs
					// specified by the chooser and the Weapon profs that the Pc has
					Set pcHas = new TreeSet();
					pcHas.addAll(profStrings);
					pcHas.retainAll(pcProfStrings);
					
					for (Iterator i = pcHas.iterator(); i.hasNext();)
					{
						String     profName = (String) i.next();
						WeaponProf wp       = Globals.getWeaponProfNamed(profName);
						
						// may have martial and exotic, etc.
						if (wp.getSafeListFor(ListKey.TYPE).size() != 1)
						{
							availableList.add(profName);
						}
					}
					
					// since this is a unique list, add all the ones the pc hasn't got
					profStrings.removeAll(pcProfStrings);
					availableList.addAll(profStrings);
				}
			}
			
		}
			
		else if ("ARMOR".equals(typeOfProf))
		{
//			List checkList = null;
//			if (intScope == SCOPE_ALL)
//			{
//				checkList = Globals.getArmorProfList();
//			}
//			else
//			{
//				checkList = aPC.getArmorProfList();
//			}
//			while (aTok.hasMoreTokens())
//			{
//				String prof = aTok.nextToken();
//				if ("ALL".equals(prof))
//				{
//					if (intScope == SCOPE_UNIQUE)
//					{
//						List allProfs = Globals.getArmorProfList();
//						for (Iterator i = allProfs.iterator(); i.hasNext();)
//						{
//							String aProf = (String)i.next();
//							if (!checkList.contains(aProf))
//							{
//								availableList.add(aProf);
//							}
//						}
//					}
//					else
//					{
//						availableList.addAll(checkList);
//					}
//					return;
//				}
//				if (prof.startsWith("TYPE") == false)
//				{
//					prof = "TYPE." + prof;
//				}
//				if (checkList.contains(prof))
//				{
//					availableList.add(prof);
//				}
//				
//			}
		}
		else if ("SHIELD".equals(typeOfProf))
		{
//			List checkList = null;
//			if (intScope == SCOPE_ALL)
//			{
//				checkList = Globals.getShieldProfList();
//			}
//			else
//			{
//				checkList = aPC.getShieldProfList();
//			}
//			while (aTok.hasMoreTokens())
//			{
//				String prof = aTok.nextToken();
//				if ("ALL".equals(prof))
//				{
//					if (intScope == SCOPE_UNIQUE)
//					{
//						List allProfs = Globals.getArmorProfList();
//						for (Iterator i = allProfs.iterator(); i.hasNext();)
//						{
//							String aProf = (String)i.next();
//							if (!checkList.contains(aProf))
//							{
//								availableList.add(aProf);
//							}
//						}
//					}
//					else
//					{
//						availableList.addAll(checkList);
//					}
//					return;
//				}
//				if (prof.startsWith("TYPE") == false)
//				{
//					prof = "TYPE." + prof;
//				}
//				if (checkList.contains(prof))
//				{
//					availableList.add(prof);
//				}
//				
//			}
		}
		else
		{
			Logging.errorPrint("CHOOSE:PROFICIENCY - Unknown type " + typeOfProf);
		}
		pobject.addAssociatedTo(selectedList);
	}
}