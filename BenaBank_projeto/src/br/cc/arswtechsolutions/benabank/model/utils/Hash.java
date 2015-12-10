package br.cc.arswtechsolutions.benabank.model.utils;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public abstract class Hash {

	/**
	 * @brief Método para converter um array de bytes em uma String hexadecimal.
	 * @param array de bytes com os valores.
	 * @param mask String com o tipo de máscara.
	 * @see "%02X" ou %C.
	 * @return String com os valores.
	 */
	private static String convertHexToString (byte[] array, String mask){
		String result = "";

		StringBuilder hexString = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			hexString.append(String.format(mask, array[i]));
		}

		result = hexString.toString();

		return result;
	}

	/**
	 * @brief Método para converter um caractere char em um nibble hexadecimal.
	 * @param value char referente ao caractere.
	 * @return byte referente ao char.
	 */
	private static byte convertCharToByte (char value) {
		byte result = 0x00;

		if((value >= '0') && (value <= '9')){
			result = (byte) (value & 0x0F);
		} else {
			switch(value){
				case 'a':
				case 'A':{
					result = 0x0A;
					break;
				}
				case 'b':
				case 'B':{
					result = 0x0B;
					break;
				}
				case 'c':
				case 'C':{
					result = 0x0C;
					break;
				}
				case 'd':
				case 'D':{
					result = 0x0D;
					break;
				}
				case 'e':
				case 'E':{
					result = 0x0E;
					break;
				}
				case 'f':
				case 'F':{
					result = 0x0F;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * @brief Método para codificar uma string com o algoritmo Base64.
	 * @param input String a ser codificada.
	 * @return O retorno será a String codificada.
	 */
	public static String encode (String input) {
		Encoder enc = Base64.getEncoder();

		/*
		 * Gera um hash apartir de uma String.
		 */
		byte[] hash = enc.encode(input.getBytes());

		String result = convertHexToString(hash, "%02X");

		return result;
	}

	/**
	 * @brief Método para decodificar uma string com o algoritmo Base64.
	 * @param input String a ser decodificada.
	 * @return O retorno será a String decodificada.
	 */
	public static String decode (String input) {
		if (input == null)
			return null;
		Decoder dec = Base64.getDecoder();

		byte value1, value2;
		byte[] hex = new byte[input.length()/2];

		/*
		 * Converter uma string hexadecimal em um array de byte.
		 */
		for (int i = 0, j = 0; i < input.length(); i += 2, j++) {
			value1 = convertCharToByte(input.charAt(i));
			value2 = convertCharToByte(input.charAt(i+1));

			/*
			 * Adiciona o byte no array.
			 */
			hex[j] = (byte) ((value1 << 4) | value2);
		}

		/*
		 * Decodifica os bytes.
		 */
		byte[] hash = dec.decode(hex);

		String result = convertHexToString(hash, "%c");

		return result;
	}

}