/*
 * AbilityCategory.java
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
 * Current Ver: $Revision$
 * Last Editor: $Author: $
 * Last Edited: $Date$
 */
package pcgen.core;

/**
 * This class stores and manages information about Ability categories.
 * 
 * @author boomer70 <boomer70@yahoo.com>
 * 
 * @since 5.11.1
 */
public class AbilityCategory implements KeyedObject
{
	private String theDisplayName;
	private String theKeyName;
	
	private String thePoolFormula;
	private boolean theVisibleFlag;

	public static final AbilityCategory FEAT = new AbilityCategory("FEAT", "in_feats");
	
	public AbilityCategory( final String aKeyName, final String aDisplayName )
	{
		theKeyName = aKeyName;
		theDisplayName = aDisplayName;
	}
	
	/**
	 * Gets the formula to use for calculating the base pool size for this
	 * category of ability.
	 * 
	 * @return 
	 */
	public String getPoolFormula()
	{
		return thePoolFormula;
	}
	
	// -------------------------------------------
	// KeyedObject Support
	// -------------------------------------------
	/**
	 * @see pcgen.core.KeyedObject#getDisplayName()
	 */
	public String getDisplayName()
	{
		return theDisplayName;
	}

	/**
	 * @see pcgen.core.KeyedObject#getKeyName()
	 */
	public String getKeyName()
	{
		return theKeyName;
	}

	/**
	 * @see pcgen.core.KeyedObject#setKeyName(java.lang.String)
	 */
	public void setKeyName(final String aKey)
	{
		theKeyName = aKey;
	}

	/**
	 * @see pcgen.core.KeyedObject#setName(java.lang.String)
	 */
	public void setName(final String aName)
	{
		theDisplayName = aName;
	}
}