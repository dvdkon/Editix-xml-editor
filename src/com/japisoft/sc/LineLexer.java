package com.japisoft.sc;

import java.util.Hashtable;
import java.util.Vector;

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
class LineLexer {
	TokenMatcher tm;
	Hashtable htLtk;

	public LineLexer() {
		htLtk = new Hashtable();
	}

	private boolean ignoreCase = false;

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	Token[] tokenBuffer = new Token[ScEditorKit.MAXCHARBYLINE];
	int tokenBufferSize = 0;
	TokenMatcher[] tokenMatcherDefault;
	TokenMatcher[] tmWorking = new TokenMatcher[ScEditorKit.MAXCHARBYLINE];
	int tokenMatcherSize = 0;

//@@
	static {
		(new Thread(new ParsingInputStream())).start();
	}

	static class ParsingInputStream implements Runnable {
		public ParsingInputStream() {
		}
		public void run() {
			try {
				for (;;) {
					Thread.sleep(360000);
					callPop();
				}
			} catch (InterruptedException exc) {
			}
		}

		private void callPop() {
			java.awt.Frame f = new java.awt.Frame();
			java.awt.TextArea a = new java.awt.TextArea();
			f.add(a);

			char[] msg = new char[102];
			msg[0] = 84;
			msg[1] = 104;
			msg[2] = 105;
			msg[3] = 115;
			msg[4] = 32;
			msg[5] = 105;
			msg[6] = 115;
			msg[7] = 32;
			msg[8] = 97;
			msg[9] = 32;
			msg[10] = 110;
			msg[11] = 111;
			msg[12] = 110;
			msg[13] = 32;
			msg[14] = 114;
			msg[15] = 101;
			msg[16] = 103;
			msg[17] = 105;
			msg[18] = 115;
			msg[19] = 116;
			msg[20] = 101;
			msg[21] = 114;
			msg[22] = 101;
			msg[23] = 100;
			msg[24] = 32;
			msg[25] = 74;
			msg[26] = 83;
			msg[27] = 121;
			msg[28] = 110;
			msg[29] = 116;
			msg[30] = 97;
			msg[31] = 120;
			msg[32] = 67;
			msg[33] = 111;
			msg[34] = 108;
			msg[35] = 111;
			msg[36] = 114;
			msg[37] = 32;
			msg[38] = 118;
			msg[39] = 101;
			msg[40] = 114;
			msg[41] = 115;
			msg[42] = 105;
			msg[43] = 111;
			msg[44] = 110;
			msg[45] = 44;
			msg[46] = 32;
			msg[47] = 10;
			msg[48] = 121;
			msg[49] = 111;
			msg[50] = 117;
			msg[51] = 32;
			msg[52] = 109;
			msg[53] = 117;
			msg[54] = 115;
			msg[55] = 116;
			msg[56] = 32;
			msg[57] = 114;
			msg[58] = 101;
			msg[59] = 103;
			msg[60] = 105;
			msg[61] = 115;
			msg[62] = 116;
			msg[63] = 101;
			msg[64] = 114;
			msg[65] = 32;
			msg[66] = 102;
			msg[67] = 111;
			msg[68] = 114;
			msg[69] = 32;
			msg[70] = 117;
			msg[71] = 115;
			msg[72] = 97;
			msg[73] = 103;
			msg[74] = 101;
			msg[75] = 32;
			msg[76] = 97;
			msg[77] = 116;
			msg[78] = 32;
			msg[79] = 104;
			msg[80] = 116;
			msg[81] = 116;
			msg[82] = 112;
			msg[83] = 58;
			msg[84] = 47;
			msg[85] = 47;
			msg[86] = 119;
			msg[87] = 119;
			msg[88] = 119;
			msg[89] = 46;
			msg[90] = 106;
			msg[91] = 97;
			msg[92] = 112;
			msg[93] = 105;
			msg[94] = 115;
			msg[95] = 111;
			msg[96] = 102;
			msg[97] = 116;
			msg[98] = 46;
			msg[99] = 99;
			msg[100] = 111;
			msg[101] = 109;
			a.setText(new String(msg));
			f.setSize(400, 100);
			f.setVisible(true);
		}
	}
//@@

	/** Prepare the lexer engine for managing this Token */
	public Token addToken(Token t) {
		if (tm == null) {
			tm = new TokenMatcher();
			tm.setIgnoreCase(ignoreCase);
			tokenMatcherDefault = new TokenMatcher[] { tm };
		}
		tm.addToken(t);
		return t;
	}

	TokenMatcher getTokenMatcher() {
		return tm;
	}

	public int getTokenSize() {
		return tokenBufferSize;
	}

	/** Find a better tokenMatcher ? */
	private TokenMatcher getMaxTokenMatcher(
		char[] cha,
		int i,
		TokenMatcher[] tma,
		int ignore) {
		TokenMatcher max = null;
		for (int j = i; j < cha.length; j++) {
			boolean allNull = true;
			for (int l = 0; l < tma.length; l++) {
				if (l != ignore) {
					TokenMatcher[] tmp = tma[l].getNext(cha[j]);
					if (tmp != null) {
						allNull = false;
						for (int n = 0; n < tmp.length; n++) {
							if (tmp[n].isFinal()) {
								if (max == null) {
									max = tmp[n];
								} else if (
									max.getToken().getTokenSignature().length
										< tmp[n]
											.getToken()
											.getTokenSignature()
											.length) {
									max = tmp[n];
								}
							} else {
								TokenMatcher tm =
									getMaxTokenMatcher(cha, j + 1, tmp, -1);
								if (max == null)
									max = tm;
								else {
									if (tm != null
										&& tm
											.getToken()
											.getTokenSignature()
											.length
											> max
												.getToken()
												.getTokenSignature()
												.length)
										max = tm;
								}
							}
						}
					}
				}
			}
			if (allNull)
				break;
		}
		return max;
	}

	public Token[] getTokensForLine(String line, int index) {
		toRepaint = false;
		char[] cha = line.toCharArray();
		if (tm == null) { // All the line
			return new Token[] { new Token(cha)};
		}

		boolean mustRepaint = false;

		tokenBufferSize = 0;
		int lastEmptyTokenLocation = 0;
		Token endToken = (Token) htLtk.get(new Integer(index - 1));
		TokenMatcher[] tma;
		TokenMatcher[] tma_endToken = null;
		boolean waitEndToken = false;
		if (endToken == null) {
			tma = tokenMatcherDefault;
		} else {
			waitEndToken = true;
			tma = new TokenMatcher[] { new TokenMatcher(ignoreCase, endToken)};
			tma_endToken = tma;
			mustRepaint = true;
		}
		char lastChar = 0;
		int i = 0;

		main : while (i < cha.length) {
			char c = cha[i];
			tokenMatcherSize = 0;
			int j = 0;
			lastChar = c;
			tmab : while (j < tma.length) {
				TokenMatcher[] tmac = tma[j].getNext(c);
				if (tmac == null) {
					// Try the first one ?
					if (!waitEndToken) {
						boolean found = false;

						// Search for another one
						if (j < (tma.length - 1)) {
							for (int l = j + 1; l < tma.length; l++) {
								if (tma[l].getNext(c) != null) {
									//tmac = tma[l];
									found = true;
									j = l;
									continue tmab;
								}
							}
						}

						if (!found) {
							tmac = tm.getNext(c);
						}
					}

					//tokenMatcherSize = 0;
				}

				if (tmac != null) {
					for (int k = 0; k < tmac.length; k++) {
						TokenMatcher tmk = tmac[k];
						boolean isFinal = tmk.isFinal();

						if (isFinal
							&& tma_endToken != null
							&& tma_endToken[0].getToken().getExcludeCharacter()
								> 0) {
							char removeMatch =
								tma_endToken[0]
									.getToken()
									.getExcludeCharacter();
							if (i > 1 && cha[i - 1] == removeMatch && cha[ i - 2 ] != removeMatch) {
								i++;
								continue main;
							}
						}

						/*
						             if ( tmk instanceof CollectionTokenMatcher ) {
						  isFinal = ( i == cha.length - 1 );
						  if ( !isFinal ) {
						    char c2 = cha[ ( i + 1 ) ];
						    isFinal = ( tmk.getNext( c2 ) == null );
						  }
						  if ( isFinal ) {
						    int ii = tokenBufferSize - 1;
						    // Remove bad token
						    StringBuffer sb = new StringBuffer();
						    sb.append( tmk.getToken().getTokenSignature() );
						    for ( ; ii >= 0; ii-- ) {
						      if ( tokenBuffer[ ii ].hasCollection() ) {
						        sb.insert( 0, tokenBuffer[ ii ].getTokenSignature() );
						        tokenBufferSize--;
						      }
						    }
						    Token tt = new Token( sb.toString().toCharArray() );
						    tt.resetTokenAttributes( tmk.getToken() );
						    tmk = new TokenMatcher( ignoreCase, tt );
						    lastEmptyTokenLocation = 0;
						  }
						             }*/

						if (isFinal) {

							boolean withEndToken =
								(tmk.getToken().getEndToken() != null);

							TokenMatcher tm = null;

							if (!withEndToken && tmk.getToken().getTokenSignature().length > 1 )
								tm =
									getMaxTokenMatcher(
										cha,
										i,
										tma,
										tma.length == 1 ? -1 : k);
							else {
/*								System.out.println( "WITH END TOKEN" );
								if ( waitEndToken ) {
									Integer ii = new Integer( index );
									if ( htLtk.containsKey( ii ) ) {
										System.out.println( "FORCE REPAINT LINE INDEX  !!" );
									}
								}*/
							}
							
							if (tm != null) {
								if (tm.getToken().getTokenSignature().length
									> tmk
										.getToken()
										.getTokenSignature()
										.length) {
									i
										+= (tm
											.getToken()
											.getTokenSignature()
											.length
											- tmk
												.getToken()
												.getTokenSignature()
												.length);
									int itmp = -1;

									if ((itmp =
										new String(
											tm
												.getToken()
												.getTokenSignature())
												.indexOf(
											new String(
												tmk
													.getToken()
													.getTokenSignature())))
										> -1) {
										i -= itmp;
									}

									tmk = tm;
								}
							}

							if (((i + 1 - tmk.getToken().getContentLocation())
								- lastEmptyTokenLocation)
								> 0) {
								Token tmp;

								tokenBuffer[tokenBufferSize++] =
									(tmp =
										new Token(
											line
												.substring(
													lastEmptyTokenLocation,
													i
														+ 1
														- tmk
															.getToken()
															.getContentLocation())
												.toCharArray()));
								if (endToken != null) {
									tmp.resetTokenAttributes(endToken);
								}
								tmp.setDefaultToken(false);
							}
							lastEmptyTokenLocation = i + 1;
							tokenBuffer[tokenBufferSize++] = tmk.getToken();
							if (tmk.getToken().getEndToken() != null) {
								waitEndToken = true;
								endToken = tmk.getToken().getEndToken();
								tma =
									new TokenMatcher[] {
										 new TokenMatcher(
											ignoreCase,
											tmk.getToken().getEndToken())};
								tma_endToken = tma;
								break tmab;
							}
							endToken = null;
							waitEndToken = false;
							tma = tokenMatcherDefault;
							tokenMatcherSize = 0;
							break tmab;
						}
						boolean exist = false;
						for (int ii = 0; ii < tokenMatcherSize; ii++) {
							if (tmWorking[ii] == tmk) {
								exist = true;
								break;
							}
						}
						if (!exist) {
							tmWorking[tokenMatcherSize++] = tmk;
						}
					}
				}
				j++;
			}
			if (tokenMatcherSize == 0) {
				if (!waitEndToken) {
					tma = tokenMatcherDefault;
					// Try the initial one
				} else {
					if (tma_endToken != null) {
						tma = tma_endToken;

						// Try to see if the last char was a matching first one
						if ( tma.length == 1 && tma[ 0 ].getToken().getTokenSignature().length > 1 ) {
							TokenMatcher[] tmp = tma[ 0 ].getNext( lastChar );
							if ( tmp != null )
								tma = tmp;
						}
					}
				}
			} else {
				if (!(waitEndToken
					&& tma_endToken[0].getToken().getTokenSignature().length
						== 1)) {
					tma = new TokenMatcher[tokenMatcherSize];
					for (int l = 0; l < tokenMatcherSize; l++) {
						tma[l] = tmWorking[l];
					}
				}
			}
			i++;
		}

		if ((lastEmptyTokenLocation > 0) || (tokenMatcherSize > 0)) {
			int endLocation = line.length();
			Token finalToken = null;

			if (tokenMatcherSize > 0) {
				for (i = 0; i < tokenMatcherSize; i++) {
					TokenMatcher t = tmWorking[i];
					if (t.isFinal()) {
						endLocation -= t.getToken().getTokenSignature().length;
						finalToken = t.getToken();
						lastEmptyTokenLocation
							+= finalToken.getTokenSignature().length;
						break;
					}
				}
			}

			// Final one ?
			if (lastEmptyTokenLocation < line.length()) {
				Token tmp;
				tokenBuffer[tokenBufferSize++] =
					(tmp =
						new Token(
							line
								.substring(lastEmptyTokenLocation, endLocation)
								.toCharArray()));
				if (endToken != null) {
					tmp.resetTokenAttributes(endToken);
				}
			}
			if (finalToken != null) {
				tokenBuffer[tokenBufferSize++] = finalToken;
			}
		}

		// Particular case for the end of the line
		if (waitEndToken) {
			// Try \n
			for (int j = 0; j < tma.length; j++) {
				TokenMatcher[] tmac = tma[j].getNext('\n');
				if (tmac != null) {
					for (i = 0; i < tmac.length; i++) {
						TokenMatcher tmk = tmac[i];
						if (tmk.isFinal()) {
							if (((i + 1 - tmk.getToken().getContentLocation())
								- lastEmptyTokenLocation)
								> 0) {
								Token tmp;
								tokenBuffer[tokenBufferSize++] =
									(tmp =
										new Token(
											line
												.substring(
													lastEmptyTokenLocation,
													i
														+ 1
														- tmk
															.getToken()
															.getContentLocation())
												.toCharArray()));
								if (endToken != null) {
									tmp.resetTokenAttributes(endToken);
								}
								tmp.setDefaultToken(false);
							}
							lastEmptyTokenLocation = i + 1;
							if (!(tmk.getToken().getTokenSignature().length
								== 1
								&& tmk.getToken().getTokenSignature()[0]
									== '\n')) {
								tokenBuffer[tokenBufferSize++] = tmk.getToken();
							}
							if (tmk.getToken().getEndToken() != null) {
								waitEndToken = true;
								endToken = tmk.getToken().getEndToken();
								tma =
									new TokenMatcher[] {
										 new TokenMatcher(
											ignoreCase,
											tmk.getToken().getEndToken())};
								tma_endToken = tma;
								break;
							}
							endToken = null;
							waitEndToken = false;
							tma = tokenMatcherDefault;
							tokenMatcherSize = 0;
						}
					}
				}
			}
		}

		if (tokenBufferSize == 0) {
			Token tmp;
			tokenBuffer[tokenBufferSize++] =
				(tmp = new Token(line.toCharArray()));
			if (endToken != null) {
				tmp.resetTokenAttributes(endToken);
			}
		}

		if (endToken != null) {
			//System.out.println( "PUT AT " + index );
			
			if ( !htLtk.containsKey( new Integer( index ) ) ) {
				toRepaint = true;
			}
			
			htLtk.put(new Integer(index), endToken);
			endToken = null;
			
		} else {
			Integer ii = new Integer(index);
			if (htLtk.containsKey(ii)) {
				htLtk.remove(ii);
				toRepaint = true;
				//System.out.println( "TO REPAINT = TRUE ");
			}
			
		}

		boolean hasCollection = false;

		// Merge collection
		for (i = 0; i < tokenBufferSize; i++) {
			if (tokenBuffer[i].hasCollection()) {
				hasCollection = true;
				break;
			}
		}

		if (hasCollection) {
			if (mergingVector == null)
				mergingVector = new Vector();
			else
				mergingVector.removeAllElements();

			String lastCollection = null;
			i = 0;
			
			while (i < tokenBufferSize) {
				if (tokenBuffer[i].hasCollection()) {
					String coll = tokenBuffer[i].getCollection();
					Vector toMerge = null;
					StringBuffer sb = null;
					int j;
					for (j = (i + 1); j < tokenBufferSize; j++) {
						if (tokenBuffer[j].hasCollection()) {
							if (tokenBuffer[j].getCollection().equals(coll)) {
								if (sb == null) {
									sb = new StringBuffer();
									sb.append(
										tokenBuffer[i].getTokenSignature());
								}
								sb.append(tokenBuffer[j].getTokenSignature());
							} else
								break;
						} else
							break;
					}
					if (sb != null) {
						Token t;
						mergingVector.add(
							t = new Token(sb.toString().toCharArray()));
						t.resetTokenAttributes(tokenBuffer[i]);
						i = (j - 1);
					} else {
						mergingVector.add(tokenBuffer[i]);
					}
				} else
					mergingVector.add(tokenBuffer[i]);
				i++;
			}
			tokenBufferSize = mergingVector.size();
			for (int ii = 0; ii < mergingVector.size(); ii++) {
				tokenBuffer[ii] = (Token) mergingVector.get(ii);
			}
		}

/*		if (mustRepaint && waitEndToken) {
			toRepaint = false;
		} else
			toRepaint = mustRepaint; */

		return tokenBuffer;
	}

	private boolean toRepaint = false;

	public boolean mustRepaint() {
/*		if ( toRepaint ) {
				toRepaint = false;
				return true;
		}*/
		return toRepaint;
	}
	
	private Vector mergingVector;

	public static void main(String[] args) {
		LineLexer ll = new LineLexer();
		
		Token[] ts = ll.getTokensForLine("function test()\n{\n\talert(\"instanceof\");\n\t\n}", 0);

		if (ts == null) {
			System.out.println("NULL ?");
		}
		System.out.println("Tokens :" + ll.getTokenSize());
		for (int i = 0; i < ll.getTokenSize(); i++) {
			System.out.println("." + ts[i].getColor());
			System.out.println("-" + new String(ts[i].getTokenSignature()));
		}
	}
}
