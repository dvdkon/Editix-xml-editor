package com.japisoft.xmlpad.editor;

import javax.swing.text.*;

import com.japisoft.framework.collection.FastVector;

import java.awt.*;
import java.util.*;

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
class WrappedXMLView extends WrappedPlainView implements XMLViewable {

	private LineParsing lp;

	static {
		CommonView.class.getName();
	}


	/**
	 * Creates a new <code>SyntaxView</code> for painting the specified
	 * element.
	 * 
	 * @param elem
	 *            The element
	 */
	public WrappedXMLView( Element elem ) {
		super(elem);
		line = new Segment();
		lp = new LineParsing();
	}

	public void setDTDMode(boolean enabled) {
		// Ignored
	}	
	
	
	public void setSyntaxColor(boolean sc) {
		if (!sc)
			lp = null;
	}


	private int oldLineIndex = -1;

	private int oldStartUnderlineX1 = -1;

	private int oldStartUnderlineX2 = -1;

	private int oldStartUnderlineY = -1;

	private int oldStopUnderlineX1 = -1;

	private int oldStopUnderlineX2 = -1;

	private int oldStopUnderlineY = -1;

	private String oldElement = "";

	protected void drawLine(int p0, int p1, Graphics g, int x, int y) {
		colorizeLine( p0, p1, g, x, y );
	}

	public int colorizeLine( int p0, int p1, Graphics g, int x, int y) {
		XMLPadDocument syntaxDocument;
		Document document = getDocument();

		XMLEditor host = (XMLEditor) getContainer();

		Font ff = (Font) host.getFont();

		int startTag = -1;
		int stopTag = -1;
		String currentTagName = null;

		if (host instanceof XMLEditor) {
			XMLEditor e = (XMLEditor) host;
			if (e.lastStructureLocation != null) {
				if (!e.lastStructureLocation.isAutoClose()) {
					startTag = e.lastStructureLocation.getStartingOffset();
					stopTag = e.lastStructureLocation.getStoppingOffset();
					if (e.lastStructureLocation.isTag())
						currentTagName = e.lastStructureLocation.getContent();
				}
			}
		}

		if (document instanceof XMLPadDocument) {
			syntaxDocument = (XMLPadDocument) document;
		} else {
			syntaxDocument = null;
		}

		FontMetrics metrics = g.getFontMetrics();
		Color defColor = getDefaultColor();
		Font defFont = host.getFont();

		try {
			document.getText( p0, p1 - p0, line );

			int start = p0;
			int end = p1;
			
			if (lp == null) {
				g.setColor(defColor);
				Utilities.drawTabbedText(line, x, y, g, this, 0);
			} else {
				
				int lineIndex = getElement().getElementIndex( p0 );
				
				int offset = 0;
				FastVector v = lp.parse(line, lineIndex);

				for (int i = 0; i < v.size(); i++) {
					LineElement le = (LineElement) v.get(i);
					String content = le.content;
					int type = le.type;

					Color c = LineElement.getColor(
							host,
							false,
							false,
							type,
							0, 0 );
					if (c == null)
						c = defColor;

					if (le.type == LineElement.NAMESPACE
							&& content != null) {
						if ( host.hasColorForPrefix( content ) )
							c = host.getColorForPrefix( content );
					}

					if (le.type == LineElement.TAG && content != null ) {
						if ( host.hasColorForTag( content ) )
							c = host.getColorForTag( content );
							if (i >= 2) {
								LineElement le_2 = (LineElement) v.get(i - 2);
								if (le_2.type == LineElement.NAMESPACE) {
									if ( host.hasColorForPrefix( le_2.content ) )
										c = host.getColorForPrefix( le_2.content );
								}
							}
					}
					if (le.type == LineElement.ATTRIBUTE && content != null ) {
						if ( host.hasColorForAttribute( content ) )
							c = host.getColorForAttribute( content );
					}

					Font f = defFont;

					g.setColor(c);


					int length = (le.content == null) ? 0 : le.content.length();
					line.count = length;

					int oldx = x;
					x = Utilities.drawTabbedText(line, x, y, g, this, offset);
					int oldOffset = offset;
					offset += line.count;

					if (le.type == LineElement.TAG) {
						boolean ok = false;
						boolean storeLastUnderline = false;
						boolean paintIt = false;

						if (currentTagName != null
								&& (currentTagName.equals(le.content))) {
							g.setColor(host.getBackground());
							g.drawLine(oldStartUnderlineX1, oldStartUnderlineY,
									oldStartUnderlineX2, oldStartUnderlineY);
							g.drawLine(oldStopUnderlineX1, oldStopUnderlineY,
									oldStopUnderlineX2, oldStopUnderlineY);
							paintIt = true;
						} else {
							storeLastUnderline = true;
						}

						if (paintIt && startTag > -1
								&& (start + oldOffset) >= startTag
								&& (start + oldOffset) <= startTag + line.count) {

							if (storeLastUnderline) {
								oldStartUnderlineX1 = oldx;
								oldStartUnderlineX2 = x;
								oldStartUnderlineY = y + 2;
							}

							drawUnderline(
									oldx,
									x,
									y + 2,
									LineElement.getColor( host, 
											false, false,
											LineElement.TAG_UNDERLINE,
											0, 0 ),
									g);

							ok = true;
						}

						if (paintIt && !ok && stopTag > -1
								&& (start + offset) >= stopTag - line.count
								&& (start + offset) <= stopTag) {

							if (storeLastUnderline) {
								oldStopUnderlineX1 = oldx;
								oldStopUnderlineX2 = x;
								oldStopUnderlineY = y + 2;
							}

							drawUnderline(
									oldx,
									x,
									y + 2,
									LineElement.getColor( host, false, false, LineElement.TAG_UNDERLINE, 0, 0 ),
									g);
						}

					}
					line.offset += line.count;
				}
			}
		} catch (BadLocationException bl) {
		}
		
		return x;
	}

	private void drawUnderline(int oldx, int x, int y, Color c, Graphics g) {
		g.setColor( LineElement.getColor( (XMLEditor)getContainer(), false, false, LineElement.TAG_UNDERLINE, 0, 0 ));
		int z = y;
		for (int i = oldx; i <= x; i += 2) {
			g.drawLine( i, z, i, z );
		}
	}

	protected Color getDefaultColor() {
		return getContainer().getForeground();
	}

	private Segment line;

	////////////////////////////////////////////////////////////////////////////////

	class LineAttribute {
		public Color color;

		public Font font;

		public LineAttribute(Color c, Font f) {
			this.color = c;
			this.font = f;
		}
	}

}
