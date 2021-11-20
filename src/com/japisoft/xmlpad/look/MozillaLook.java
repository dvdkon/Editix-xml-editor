package com.japisoft.xmlpad.look;

import java.awt.*;

import com.japisoft.xmlpad.XMLContainer;
import com.japisoft.xmlpad.editor.*;

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
public class MozillaLook extends DefaultLook {
	public MozillaLook() {
		super();
	}

	public void install( XMLContainer container, XMLEditor editor) {
		super.install( container, editor );

		// Color

		editor.setColorForTagBorderLine( Color.gray );

		editor.setColorForEntity(new Color(255, 69, 0));
		editor.setColorForDocType(new Color(70, 130, 180));
		editor.setColorForDeclaration(new Color(70, 130, 180));

		editor.setColorForLiteral(new Color(0, 0, 255));
		editor.setColorForTag(new Color(128, 0, 128));
		editor.setColorForAttribute(new Color(0, 0, 0));
		editor.setColorForNameSpace(editor.getColorForTag().darker());
		editor.setColorForTagUnderline( Color.blue );
	}

}

// MozillaLook ends here
