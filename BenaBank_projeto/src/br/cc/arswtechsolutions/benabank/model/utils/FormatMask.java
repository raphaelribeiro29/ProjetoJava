package br.cc.arswtechsolutions.benabank.model.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatMask {
	public static String format(String pattern, Object value){
		MaskFormatter mask;
		try{
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e){
			throw new RuntimeException(e);
		}
	}
}
