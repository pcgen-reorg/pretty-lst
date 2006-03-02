/*
 * Var.java
 * Copyright 2002 (C) Greg Bingleman <byngl@hotmail.com>
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
 * Created on December 13, 2002, 9:19 AM
 *
 * Current Ver: $Revision: 1.9 $
 * Last Editor: $Author: jdempsey $
 * Last Edited: $Date: 2006/01/29 00:08:05 $
 *
 */
package plugin.bonustokens;

import pcgen.core.bonus.BonusObj;


/**
 * <code>Var</code> deals with bonuses to data defined variables.
 *
 * @author  Greg Bingleman <byngl@hotmail.com>
 */
public final class Var extends BonusObj
{
	/** The type of bonus tags dealt with by this object. */
	private static final String[] bonusHandled = {"VAR"};

	/**
	 * @see pcgen.core.bonus.BonusObj#parseToken(java.lang.String)
	 */
	protected boolean parseToken(final String token)
	{
		addBonusInfo(token);

		return true;
	}

	/**
	 * @see pcgen.core.bonus.BonusObj#unparseToken(java.lang.Object)
	 */
	protected String unparseToken(final Object obj)
	{
		return (String) obj;
	}

	/**
	 * @see pcgen.core.bonus.BonusObj#getBonusesHandled()
	 */
	protected String[] getBonusesHandled()
	{
		return bonusHandled;
	}

}