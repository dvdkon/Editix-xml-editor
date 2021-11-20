// (c) ALEXANDRE BRILLANT : http://www.japisoft.com
// All this work is confidential, you have rights to 
// change and evolve it for your products but you
// have no rights to sell it, propose concurrent works.
// Morever any changes to bugs or evolutions should
// be send to JAPISOFT that needs to maintain a 
// valid version and has all rights on the product.


package com.japisoft.xpath.node;

import com.japisoft.xpath.*;

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
public class Predicate extends AbstractNode {
    public Predicate() {
	super();
    }

    public Object eval( XPathContext context ) {
	// Eval the content

        // Save the context
        NodeSet tempNS = context.getContextNodeSet();
        int tempLocation = context.getContextPosition();

	Object obj = getNodeAt( 0 ).eval( context );

        // Restore it
        context.setContextNodeSet( tempNS );
        context.setContextPosition( tempLocation );

	if ( obj instanceof Double ) {
	    Double d = ( Double )obj;
	    return new Boolean( ( double )( context.getContextPosition() + 1 ) == d.doubleValue() );
	} else
	    if ( obj instanceof Boolean ) {
		return ( Boolean )obj;
	    } else
		if ( obj instanceof NodeSet ) {
		    return new Boolean( ( ( NodeSet )obj ).size() > 0 );
		} else
		    throw new RuntimeException( "Invalid predicate" );
    }
}

// Predicate ends here
