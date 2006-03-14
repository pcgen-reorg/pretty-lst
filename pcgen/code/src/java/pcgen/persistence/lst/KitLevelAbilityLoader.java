/*
 * KitLevelAbilityLoader.java
 * Copyright 2006 (C) Aaron Divinsky <boomer70@yahoo.com>
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
 * Created on March 6, 2006
 *
 * Current Ver: $Revision$
 * Last Editor: $Author$
 * Last Edited: $Date$
 */

package pcgen.persistence.lst;

import java.util.Map;
import java.util.StringTokenizer;

import pcgen.core.Kit;
import pcgen.core.kit.KitLevelAbility;
import pcgen.persistence.PersistenceLayerException;
import pcgen.persistence.SystemLoader;
import pcgen.util.Logging;

public class KitLevelAbilityLoader
{
	public static void parseLine(Kit kit, String colString)
			throws PersistenceLayerException
	{
		final StringTokenizer colToken = new StringTokenizer(colString,
				SystemLoader.TAB_DELIM);

		KitLevelAbility kitLA = new KitLevelAbility();

		colString = colToken.nextToken();
		String classInfo = colString;
		int levelInd = classInfo.indexOf("=");
		if (levelInd < 0)
		{
			throw new PersistenceLayerException(
					"Invalid level in KitLevelAbility info \"" + colString
							+ "\"");
		}
		kitLA.setClass(classInfo.substring(0, levelInd));
		try
		{
			kitLA.setLevel(Integer.parseInt(classInfo.substring(levelInd + 1)));
		}
		catch (NumberFormatException e)
		{
			throw new PersistenceLayerException(
					"Invalid level in KitLevelAbility info \"" + colString
							+ "\"");
		}
		Map tokenMap = TokenStore.inst().getTokenMap(
				KitLevelAbilityLstToken.class);
		while (colToken.hasMoreTokens())
		{
			colString = colToken.nextToken();

			// We will find the first ":" for the "controlling" line token
			final int idxColon = colString.indexOf(':');
			String key = "";
			try
			{
				key = colString.substring(0, idxColon);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				// TODO Handle Exception
			}
			KitLevelAbilityLstToken token = (KitLevelAbilityLstToken) tokenMap
					.get(key);

			if (token != null)
			{
				final String value = colString.substring(idxColon + 1);
				LstUtils.deprecationCheck(token, kit, value);
				if (!token.parse(kitLA, value))
				{
					Logging.errorPrint("Error parsing Kit Level Ability tag "
							+ kitLA.getObjectName() + ':' + colString + "\"");
				}
			}
			else if (BaseKitLoader.parseCommonTags(kitLA, colString))
			{
				continue;
			}
			else
			{
				Logging.errorPrint("Unknown Kit Level Ability info: \""
						+ colString + "\"");
			}

		}
		kit.setDoLevelAbilities(false);
		kit.addObject(kitLA);
	}
}