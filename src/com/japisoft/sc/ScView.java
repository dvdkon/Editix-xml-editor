package com.japisoft.sc;

import javax.swing.SwingUtilities;
import javax.swing.text.*;
import java.awt.*;

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
class ScView extends PlainView implements Runnable {
	private SyntaxLexer sl;
	private Segment line;

	public ScView(Element e, SyntaxLexer sl) {
		super(e);
		line = new Segment();
		this.sl = sl;
	}

	public void setSyntaxLexer(SyntaxLexer sl) {
		this.sl = sl;
	}

	/** Parse this line and show all tokens */
	public void drawLine(int lineIndex, Graphics g, int x, int y) {
		Document d = getDocument();
		Color defColor = getDefaultColor();
		JTextComponent cc = (JTextComponent) getContainer();
		
		int select_start = cc.getSelectionStart();
		int select_end = cc.getSelectionEnd();
		boolean forceRepaint = false;
		
		try {
			Element lineElement = getElement().getElement(lineIndex);
			int start = lineElement.getStartOffset();
			int end = lineElement.getEndOffset();
			d.getText(start, end - (start + 1), line);
			int offset = 0;
			Token[] tks =
				sl.getTokenForLine(
					d.getText(start, end - (start + 1)),
					lineIndex);
			
			if ( sl.mustRepaint() ) {
				forceRepaint = true;
			}
			
			if (tks == null) {
				return; // ??
			}
			FontMetrics fm = g.getFontMetrics();
			int h = fm.getHeight();
			for (int i = 0; i < sl.getTokenCount(); i++) {
				Token t = tks[i];
				int tl = t.getTokenSignature().length;
				line.count = tl;
				Color c = t.getColor();
				Color tokenColor = c;
				if (t.isDefaultToken() || c == null) {
					c = defColor;
					tokenColor = c;
				}

				int adderOnSelection = 0;
				int adderOnSelection2 = 0;

				// Fix selection color

				if (select_start < select_end) {
					int select_startY =
						getElement().getElementIndex(select_start);
					int select_endY = getElement().getElementIndex(select_end);

					int lineX = select_startY;

					if (lineIndex > select_startY && lineIndex < select_endY) {
						c = cc.getSelectedTextColor();
					} else {

						int lineY = getElement().getElementIndex(select_end);

						Element e = getElement().getElement(select_startY);
						int select_startX = select_start - e.getStartOffset();
						e = getElement().getElement(select_endY);
						int select_endX = select_end - e.getStartOffset();

						// Même ligne
						if (lineIndex == lineX && lineIndex == lineY) {

							if ( offset >= select_startX && offset + line.count <= select_endX ) {
								c= cc.getSelectedTextColor();
							} else
							if ( select_startX >= offset && select_endX <= offset + line.count ) {
									adderOnSelection2 =
										( offset + tl ) - select_endX;
									line.count = select_startX - offset;
									tl = line.count;
									adderOnSelection =
										select_endX - select_startX;
							} else 
							if (offset > select_startX
								&& offset + line.count < select_endX) {
								c = cc.getSelectedTextColor();
							} else {
								if (offset + line.count >= select_startX
									&& offset <= select_startX) {
									adderOnSelection =
										offset + tl - select_startX;
									line.count = select_startX - offset;
									tl = line.count;
								} else 
								if (
									offset + line.count > select_endX
										&& offset < select_endX) {
									tl = offset + line.count - select_endX;
									line.count = select_endX - offset;
									g.setColor(cc.getSelectedTextColor());
									x =
										Utilities.drawTabbedText(
											line,
											x,
											y,
											g,
											this,
											offset);
									offset += line.count;
									line.offset += line.count;
									line.count = tl;
								}
							}
						} else
							// Partie haute
							if (lineIndex == select_startY) {
								if (offset >= select_startX)
									c = cc.getSelectedTextColor();
								else {
									if (offset + line.count >= select_startX
										&& offset <= select_startX) {
										adderOnSelection =
											offset + tl - select_startX;
										line.count = select_startX - offset;
										tl = line.count;
									}
								}
							} else {
								// Partie basse
								if (lineIndex == select_endY) {
									if (offset <= select_endX)
										if (offset + line.count <= select_endX)
											c = cc.getSelectedTextColor();
										else if (
											offset + line.count
												> select_endX) {
											tl =
												offset
													+ line.count
													- select_endX;
											line.count = select_endX - offset;
											g.setColor(
												cc.getSelectedTextColor());
											x =
												Utilities.drawTabbedText(
													line,
													x,
													y,
													g,
													this,
													offset);
											offset += line.count;
											line.offset += line.count;
											line.count = tl;
										}
								}
							}
					}
				}

				g.setColor(c);
				x = Utilities.drawTabbedText(line, x, y, g, this, offset);

				if (!t.isDefaultToken()) {
					if (t.isUnderline()) {
						int dx =
							fm.charsWidth(line.array, line.offset, line.count);
						g.drawLine(x, y + 1, x - dx, y + 1);
					} else if (t.isBorder()) {
						int dx =
							fm.charsWidth(line.array, line.offset, line.count);
						g.drawRect(x - dx, y - h + 4, dx + 1, h - 1);
					}
				}
				offset += tl;
				line.offset += tl;

				if (adderOnSelection > 0) {
					line.count = adderOnSelection;
					g.setColor(cc.getSelectedTextColor());
					x = Utilities.drawTabbedText(line, x, y, g, this, offset);
					offset += line.count;
					line.offset += line.count;
				}

				if ( adderOnSelection2 > 0 ) {
					line.count = adderOnSelection2;
					g.setColor( tokenColor );
					x = Utilities.drawTabbedText(line, x, y, g, this, offset);
					offset += line.count;
					line.offset += line.count;
				}

			}
		} catch (BadLocationException ex) {
		}
		
		if ( forceRepaint ) {
			SwingUtilities.invokeLater( this );
		}
	}

	public Color getDefaultColor() {
		return getContainer().getForeground();
	}

	public void run() {
		getContainer().repaint();
	}
	
}
