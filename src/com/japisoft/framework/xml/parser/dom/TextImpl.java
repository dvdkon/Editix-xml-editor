package com.japisoft.framework.xml.parser.dom;

import com.japisoft.framework.xml.parser.node.*;

import org.w3c.dom.*;

/**
This program is available under two licenses : 

1. GPL license for non commercial and commercial usage : 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

2. For commercial usage :

You need to get a commercial license for source usage at : 

http://www.editix.com/buy.html

Copyright (c) 2018 Alexandre Brillant - JAPISOFT SARL - http://www.japisoft.com

@author Alexandre Brillant - abrillant@japisoft.com
@author JAPISOFT SARL - http://www.japisoft.com

*/
public class TextImpl extends CharacterDataImpl implements Text {
	public TextImpl(String content) {
		super(FPNode.TEXT_NODE, content);
	}

	/**
	 * Breaks this <code>Text</code> node into two Text nodes at the specified 
	 * offset, keeping both in the tree as siblings. This node then only 
	 * contains all the content up to the <code>offset</code> point. And a new 
	 * <code>Text</code> node, which is inserted as the next sibling of  this 
	 * node, contains all the content at and after the <code>offset</code> 
	 * point.
	 * @param offset The offset at which to split, starting from 0.
	 * @return The new <code>Text</code> node.
	 * @exception DOMException
	 *   INDEX_SIZE_ERR: Raised if the specified offset is negative or greater 
	 *   than the number of characters in <code>data</code>.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	public Text splitText(int offset) throws DOMException {
		if (getContent() == null)
			throw new DOMExceptionImpl(
				DOMException.INVALID_CHARACTER_ERR,
				"No content");
		if (offset >= getContent().length())
			throw new DOMExceptionImpl(
				DOMException.INDEX_SIZE_ERR,
				"Index " + offset + " errors ");
		String s = getContent();
		Text t = new TextImpl(s.substring(0, offset));
		setContent(s.substring(offset));
		return (Text) insertBefore(t, this);
	}

	public String getWholeText() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isElementContentWhitespace() {
		// TODO Auto-generated method stub
		return false;
	}

	public Text replaceWholeText(String content) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

}

// TextImpl ends here
