/* 
 * SubclassToken.java
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
 * Current Ver: $Revision: $
 * Last Editor: $Author: $
 * Last Edited: $Date: $
 */

package plugin.lsttokens.kit.clazz;

import pcgen.core.kit.KitClass;
import pcgen.persistence.lst.KitClassLstToken;

public class LevelToken implements KitClassLstToken
{
	public boolean parse(KitClass kitClass, String value)
	{
		kitClass.setLevel(value);
		return true;
	}

	/**
	 * Gets the name of the tag this class will parse.
	 * 
	 * @return Name of the tag this class handles
	 */
	public String getTokenName()
	{
		return "LEVEL";
	}
}