package com.japisoft.framework.xml.parser.dom;

import com.japisoft.framework.xml.parser.document.*;
import com.japisoft.framework.xml.parser.node.*;

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
public class DomNodeFactory implements NodeFactory {

	public DomNodeFactory() {
		super();
	}

	private Document refDoc;

	public void setRefDocument(Document doc) {
		this.refDoc = doc;
	}

	private static NodeFactory instance;

	/** @return a single instance of the node factory */
	public static NodeFactory getFactory() {
		if (instance == null)
			instance = new DomNodeFactory();
		return instance;
	}

	/** @return a text node */
	public MutableNode getTextNode(String text) {
		FPNode node = new TextImpl(text);
		node.setDocument(refDoc);
		return node;
	}

	/** @return a tag node */
	public MutableNode getTagNode(String tag) {
		FPNode node = new ElementImpl(tag);
		node.setDocument(refDoc);
		return node;
	}

	public MutableNode getTagNode(int idTag) {
		FPNode node = new ElementImpl(idTag);
		node.setDocument(refDoc);
		return node;
	}	

	/** @return a comment node */
	public MutableNode getCommentNode(String comment) {
		FPNode node = new CommentImpl(comment);
		node.setDocument(refDoc);
		return node;
	}

}

// DomNodeFactory ends here
