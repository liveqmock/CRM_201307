package com.oasis.tmsv5.util.helper;

public class UnicodeHelper {
	private static final int MASKBITS = 0x3F;
	private static final int MASKBYTE = 0x80;
	private static final int MASK2BYTES = 0xC0;
	private static final int MASK3BYTES = 0xE0;

	// private static final int MASK4BYTES = 0xF0;
	// private static final int MASK5BYTES = 0xF8;
	// private static final int MASK6BYTES = 0xFC;
	/**
	 * @功能: 将UNICODE（UTF-16LE）编码转成UTF-8编码
	 * @参数: byte[] b 源字节数组
	 * @返回值: byte[] b 转为UTF-8编码后的数组
	 * @作者: imuse
	 * @MAIL: postzhu@hotmail.com
	 */
	public static byte[] UNICODE_TO_UTF8(byte[] b) {
		int i = 0;
		int j = 0;
		byte[] utf8Byte = new byte[b.length * 2];
		while (i < b.length) {
			byte[] bUTF = new byte[1];
			int nCode = (b[i] & 0xFF) | ((b[i + 1] & 0xFF) << 8);
			if (nCode < 0x80) {
				bUTF = new byte[1];
				bUTF[0] = (byte) nCode;
			}
			// 110xxxxx 10xxxxxx
			else if (nCode < 0x800) {
				bUTF = new byte[2];
				bUTF[0] = (byte) (MASK2BYTES | nCode >> 6);
				bUTF[1] = (byte) (MASKBYTE | nCode & MASKBITS);
			}
			// 1110xxxx 10xxxxxx 10xxxxxx
			else if (nCode < 0x10000) {
				bUTF = new byte[3];
				bUTF[0] = (byte) (MASK3BYTES | nCode >> 12);
				bUTF[1] = (byte) (MASKBYTE | nCode >> 6 & MASKBITS);
				bUTF[2] = (byte) (MASKBYTE | nCode & MASKBITS);
			}
			for (int k = 0; k < bUTF.length; k++) {
				utf8Byte[j++] = bUTF[k];
			}
			i += 2;
		}
		b = new byte[j];
		System.arraycopy(utf8Byte, 0, b, 0, j);
		return b;
	}
}
