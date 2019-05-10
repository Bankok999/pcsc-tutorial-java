/*
 * Copyright (c) 2019, Sergey Stolyarov <sergei@regolit.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import static java.util.Arrays.copyOfRange;
import static java.lang.Math.max;
import java.util.ArrayList;

// local utility class
class Util {
    public static final String hexChars = "0123456789abcdef";

    public static class TerminalNotFoundException extends Exception {}

    static class ByteStringParseException extends Exception{
        public ByteStringParseException(String message) {
            super(message);
        }
    }

    public static String hexify(byte[] bytes) {
        ArrayList<String> bytesStrings = new ArrayList<String>(bytes.length);
        for (byte b : bytes) {
            bytesStrings.add(String.format("%02X", b));
        }
        return String.join(" ", bytesStrings);
    }

    public static byte[] toByteArray(String s)
        throws ByteStringParseException
    {
        s = s.toLowerCase();

        int len = s.length();
        byte[] buf = new byte[len/2];
        int bufLen = 0;
        int i = 0;
        
        while (i < len) {
            char c1 = s.charAt(i);
            i++;
            if (c1 == ' ') {
                continue;
            }
            try {
                char c2 = s.charAt(i);
                if (hexChars.indexOf(c1) == -1 || hexChars.indexOf(c2) == -1) {
                    throw new ByteStringParseException("Incorrect input character");
                }
                byte d = (byte)((Character.digit(c1, 16) << 4) + (Character.digit(c2, 16)));
                buf[bufLen] = d;
                ++bufLen;
            } catch (StringIndexOutOfBoundsException e) {
                throw new ByteStringParseException("Input too short");
            }
            i++;

        }

        return copyOfRange(buf, 0, bufLen);
    }
}