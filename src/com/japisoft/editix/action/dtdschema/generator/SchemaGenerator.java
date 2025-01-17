package com.japisoft.editix.action.dtdschema.generator;

import java.util.ArrayList;
import java.util.HashMap;

import com.japisoft.framework.xml.parser.node.FPNode;

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
public class SchemaGenerator {

	public static MetaNode getMetaModel( FPNode root ) {
		FPNode documentRoot = new FPNode( FPNode.DOCUMENT_NODE, null );
		documentRoot.appendChild( root );

		MetaNode metaRoot = new MetaNode( documentRoot, new HashMap<String, MetaNode>() );
		metaRoot.manageAttributes( documentRoot );
		metaRoot.manageChildren( documentRoot );
		metaRoot.manageMissing( documentRoot );
		
		return metaRoot;
	}

	public static String generate(
			MetaNode metaRoot,
			Transformer transformer ) {
		String newDocument = transformer.transform( ( MetaNode )metaRoot.getChildren().get( 0 ), metaRoot.getNodeCollection() );
		return newDocument;
	}
	
	public static String generate( 
			FPNode root, 
			Transformer transformer ) {
		MetaNode metaRoot = getMetaModel( root );
		return generate( metaRoot, transformer );
	}

}
