/*
    Copyright (C) 2012  Filippe Costa Spolti

    This file is part of Hrstatus.

    Hrstatus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.hrstatus.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    public static char[] decode(String secret) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        final byte[] kbytes = "summer time way".getBytes();
        final SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

        final BigInteger n = new BigInteger(secret, 16);
        final byte[] encoding = n.toByteArray();

        final Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key);
        final byte[] decode = cipher.doFinal(encoding);
        return new String(decode).toCharArray();
    }

    public static String encode(String secret) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        final byte[] kbytes = "summer time way".getBytes();
        final SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

        final Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        final byte[] encoding = cipher.doFinal(secret.getBytes());
        final BigInteger n = new BigInteger(encoding);
        return n.toString(16);
    }
}