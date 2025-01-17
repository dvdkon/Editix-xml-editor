package com.japisoft.xmlpad.tree.parser;

import java.io.Reader;

import com.japisoft.framework.xml.parser.ErrorParsingListener;
import com.japisoft.framework.xml.parser.ParseException;
import com.japisoft.framework.xml.parser.document.Document;
import com.japisoft.framework.xml.parser.node.NodeFactory;

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
public interface Parser {

	public static final int CONTINUE_PARSING_MODE = 0;

	public boolean isInterrupted();

	public void interruptParsing();

	public void setFlatView(boolean b);

	public void setBackgroundMode(boolean b);

	public void setParsingMode(int continueParsingMode);

	public void setErrorSignal(ErrorParsingListener parsingErrorListener);

	public void setNodeFactory(NodeFactory factory);

	public Document parse(Reader reader) throws ParseException;

	public boolean hasError();

}
