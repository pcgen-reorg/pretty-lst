/*
 * GearToken.java
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
 * Created on March 3, 2006
 *
 * Current Ver: $Revision: $
 * Last Editor: $Author: $
 * Last Edited: $Date: $
 */
package plugin.lsttokens.kit;

import java.util.StringTokenizer;

import pcgen.core.Kit;
import pcgen.core.kit.KitGear;
import pcgen.persistence.PersistenceLayerException;
import pcgen.persistence.SystemLoader;
import pcgen.persistence.lst.KitLstToken;
import pcgen.util.Logging;

/**
 * Handles the GEAR and its related subtags for a Kit.  Includes support for
 * <code>EQMOD</code>, <code>SIZE</code>, <code>LOCATION</code>,
 * <code>QTY</code>, and <code>MAXCOST</code> tags.
 */
public class GearToken extends KitLstToken
{
	/**
	 * Gets the name of the tag this class will parse.
	 *
	 * @return Name of the tag this class handles
	 */
	public String getTokenName()
	{
		return "GEAR";
	}

	/**
	 * Handles the GEAR and GEAR subtags for a Kit.
	 *
	 * @param aKit the Kit object to add this information to
	 * @param value the token string
	 * @return true if parse OK
	 * @throws PersistenceLayerException
	 */
	public boolean parse(Kit aKit, String value)
		throws PersistenceLayerException
	{
		final StringTokenizer colToken = new StringTokenizer(value, SystemLoader.TAB_DELIM);
		KitGear kGear = new KitGear(colToken.nextToken());

		while (colToken.hasMoreTokens())
		{
			final String colString = colToken.nextToken();

			if (colString.startsWith("GEAR:"))
			{
				Logging.errorPrint("Ignoring second GEAR tag \"" + colString + "\" in GearToken.parse");
			}
			else if (colString.startsWith("EQMOD:"))
			{
				kGear.addEqMod(colString.substring(6));
			}
			else if (colString.startsWith("QTY:"))
			{
				kGear.setQty(colString.substring(4));
			}
			else if (colString.startsWith("MAXCOST:"))
			{
				kGear.setMaxCost(colString.substring(8));
			}
			else if (colString.startsWith("SIZE:"))
			{
				kGear.setSize(colString.substring(5));
			}
			else if (colString.startsWith("LOCATION:"))
			{
				kGear.setLocation(colString.substring(9));
			}
			else if (colString.startsWith("SPROP:") || colString.startsWith("LEVEL:"))
			{
				Logging.errorPrint("unhandled parsed object in KitLoader.parseGearLine: " + colString);
			}
			else
			{
				if (parseCommonTags(kGear, colString) == false)
				{
					throw new PersistenceLayerException(
						"Unknown KitGear info " +  " \"" + value + "\"");
				}
			}
		}

		aKit.addObject(kGear);
		return true;
	}
}