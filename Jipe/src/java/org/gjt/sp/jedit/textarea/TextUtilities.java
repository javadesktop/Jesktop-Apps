/*
 * TextUtilities.java - Utility functions used by the text area classes
 * Copyright (C) 1999 Slava Pestov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.gjt.sp.jedit.textarea;

import javax.swing.text.*;

/**
 * Class with several utility functions used by the text area component.
 * @author Slava Pestov
 * @version $Id: TextUtilities.java,v 1.1.1.1 2002-01-11 08:51:32 paul-h Exp $
 */
public class TextUtilities
{
	/**
	 * Returns the offset of the bracket matching the one at the
	 * specified offset of the document, or -1 if the bracket is
	 * unmatched (or if the character is not a bracket).
	 * @param doc The document
	 * @param offset The offset
	 * @exception BadLocationException If an out-of-bounds access
	 * was attempted on the document text
	 */
	public static int findMatchingBracket(Document doc, int offset)
		throws BadLocationException
	{
		if(doc.getLength() == 0)
			return -1;
		char c = doc.getText(offset,1).charAt(0);
		char cprime; // c` - corresponding character
		boolean direction; // true = back, false = forward

		switch(c)
		{
		case '(': cprime = ')'; direction = false; break;
		case ')': cprime = '('; direction = true; break;
		case '[': cprime = ']'; direction = false; break;
		case ']': cprime = '['; direction = true; break;
		case '{': cprime = '}'; direction = false; break;
		case '}': cprime = '{'; direction = true; break;
		default: return -1;
		}

		int count;

		// How to merge these two cases is left as an exercise
		// for the reader.

		// Go back or forward
		if(direction)
		{
			// Count is 1 initially because we have already
			// `found' one closing bracket
			count = 1;

			// Get text[0,offset-1];
			String text = doc.getText(0,offset);

			// Scan backwards
			for(int i = offset - 1; i >= 0; i--)
			{
				// If text[i] == c, we have found another
				// closing bracket, therefore we will need
				// two opening brackets to complete the
				// match.
				char x = text.charAt(i);
				if(x == c)
					count++;

				// If text[i] == cprime, we have found a
				// opening bracket, so we return i if
				// --count == 0
				else if(x == cprime)
				{
					if(--count == 0)
						return i;
				}
			}
		}
		else
		{
			// Count is 1 initially because we have already
			// `found' one opening bracket
			count = 1;

			// So we don't have to + 1 in every loop
			offset++;

			// Number of characters to check
			int len = doc.getLength() - offset;

			// Get text[offset+1,len];
			String text = doc.getText(offset,len);

			// Scan forwards
			for(int i = 0; i < len; i++)
			{
				// If text[i] == c, we have found another
				// opening bracket, therefore we will need
				// two closing brackets to complete the
				// match.
				char x = text.charAt(i);

				if(x == c)
					count++;

				// If text[i] == cprime, we have found an
				// closing bracket, so we return i if
				// --count == 0
				else if(x == cprime)
				{
					if(--count == 0)
						return i + offset;
				}
			}
		}

		// Nothing found
		return -1;
	}

	/**
	 * Locates the start of the word at the specified position.
	 * @param line The text
	 * @param pos The position
	 * @param noWordSep Characters that are non-alphanumeric, but
	 * should be treated as word characters anyway
	 */
	public static int findWordStart(String line, int pos, String noWordSep)
	{
		char ch = line.charAt(pos);

		if(noWordSep == null)
			noWordSep = "";
		boolean selectNoLetter = (!Character.isLetterOrDigit(ch)
			&& noWordSep.indexOf(ch) == -1);

		int wordStart = 0;
		for(int i = pos; i >= 0; i--)
		{
			ch = line.charAt(i);
			if(selectNoLetter ^ (!Character.isLetterOrDigit(ch) &&
				noWordSep.indexOf(ch) == -1))
			{
				wordStart = i + 1;
				break;
			}
		}

		return wordStart;
	}

	/**
	 * Locates the end of the word at the specified position.
	 * @param line The text
	 * @param pos The position
	 * @param noWordSep Characters that are non-alphanumeric, but
	 * should be treated as word characters anyway
	 */
	public static int findWordEnd(String line, int pos, String noWordSep)
	{
		if(pos != 0)
			pos--;

		char ch = line.charAt(pos);

		if(noWordSep == null)
			noWordSep = "";
		boolean selectNoLetter = (!Character.isLetterOrDigit(ch)
			&& noWordSep.indexOf(ch) == -1);

		int wordEnd = line.length();
		for(int i = pos; i < line.length(); i++)
		{
			ch = line.charAt(i);
			if(selectNoLetter ^ (!Character.isLetterOrDigit(ch) &&
				noWordSep.indexOf(ch) == -1))
			{
				wordEnd = i;
				break;
			}
		}
		return wordEnd;
	}
}

/*
 * ChangeLog:
 * $Log: not supported by cvs2svn $
 * Revision 1.0  2000-07-15 11:24:34+01  steve_lawson
 * Initial revision
 *
 * Revision 1.0  2000-06-29 21:50:41+01  steve_lawson
 * Initial revision
 *
 * Revision 1.6  2000/01/28 00:20:58  sp
 * Lots of stuff
 *
 * Revision 1.5  1999/12/19 11:14:29  sp
 * Static abbrev expansion started
 *
 * Revision 1.4  1999/12/13 03:40:30  sp
 * Bug fixes, syntax is now mostly GPL'd
 *
 * Revision 1.3  1999/11/21 03:40:18  sp
 * Parts of EditBus not used by core moved to EditBus.jar
 *
 * Revision 1.2  1999/07/16 23:45:49  sp
 * 1.7pre6 BugFree version
 *
 * Revision 1.1  1999/06/29 09:03:18  sp
 * oops, forgot to add TextUtilities.java
 *
 */
