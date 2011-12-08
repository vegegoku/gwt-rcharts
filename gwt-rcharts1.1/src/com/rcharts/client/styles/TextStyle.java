/*
 * Copyright 2011 Saurabh Tripathi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rcharts.client.styles;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;

public class TextStyle extends Style{
	
	
	public static final class TextAnchor {
		public static final String START = "start";
		public static final String MIDDLE = "middle";
		public static final String END = "end";
		
		public String getAttrName() {
			return Keys.TEXT_ANCHOR;
		}
	}
	
	
	//Double is also a possible type for this
	public static final class FontWeight{
		public static final String NORMAL = "normal";
		public static final String BOLD = "bold";
		public static final String BOLDER = "bolder";
		public static final String LIGHTER = "lighter";

		public String getAttrName() {
			return Keys.FONT_WEIGHT;
		}
	}
	
	
	public TextStyle(){
		super();
	}
	
	public void setFontName(String fontName){
		style.put(Keys.FONT, new JSONString(fontName));
	}
	
	public void setFontFamily(String fontFamily){
		style.put(Keys.FONT_FAMILY, new JSONString(fontFamily));
	}
	
	public void setFontSize(double fontSize){
		style.put(Keys.FONT_SIZE, new JSONNumber(fontSize));
	}
	
	
	public void setFontWeight(String fontWeight){
		style.put(Keys.FONT_WEIGHT, new JSONString(fontWeight));
	}
	
	public void setTextAnchor(String textAnchor){
		style.put(Keys.TEXT_ANCHOR, new JSONString(textAnchor));
	}
	
	
	//FORBIDDEN SVG TEXT ATTRIBUTE NOT SUPPOTED BY raphaeljs; FOR THESE USE FONT FAMILY
	
	/*
	public void setWritingMode(String writingMode){
		textStyle.put(Keys.WRITING_MODE, new JSONString(writingMode));
	}
	
	public void setGlyphOrientationVertical(String gylphOrientationVertical){
		textStyle.put(Keys.GLYPH_ORIENTATION_VERTICAL, new JSONString(gylphOrientationVertical));
	}
	
	public void setGlyphOrientationHorizontal(String gylphOrientationHorizontal){
		textStyle.put(Keys.GLYPH_ORIENTATION_HORIZONTAL, new JSONString(gylphOrientationHorizontal));
	}
  
  
 	public void setFontStretch(String fontStretch){
		textStyle.put(Keys.FONT_STRETCH, new JSONString(fontStretch));
	}
	
	public void setFontStyle(String fontStyle){
		textStyle.put(Keys.FONT_STYLE, new JSONString(fontStyle));
	}
	
	public void setTextDecoration(String textDecoration){
		textStyle.put(Keys.TEXT_DECORATION, new JSONString(textDecoration));
	}
 
	public void setAlignmentBaseLine(String alignmentBaseLine){
		textStyle.put(Keys.ALIGNMENT_BASELINE, new JSONString(alignmentBaseLine));
	}
	
	public void setBaseLineShift(String baselineShift){
		textStyle.put(Keys.BASELINE_SHIFT, new JSONString(baselineShift));
	}
  	
 */
	
	
	
	/*	public static final class FontStretch implements Keys{
	public static final String NORMAL = "normal";
	public static final String WIDER = "wider";
	public static final String NARROWER = "narrower";
	public static final String ULTRA_CONDENSED = "ultra-condensed";
	public static final String EXTRA_CONDENSED = "extra-condensed";
	public static final String EXPANDED = "expanded";
	
	@Override
	public String getAttrName() {
		return FONT_STRETCH;
	}
}

public static final class FontStyle implements Keys{
	public static final String NORMAL = "normal";
	public static final String ITALIC = "italic";
	public static final String OBLIQUE = "oblique";
	
	@Override
	public String getAttrName() {
		return FONT_STYLE;
	}
}

public static final class TextDecoration implements Keys{
	public static final String UNDERLINE = "underline";
	public static final String OVERLINE = "overline";
	public static final String LINE_THROUGH = "line-through";
	
	@Override
	public String getAttrName() {
		return TEXT_DECORATION;
	}
}




public static final class AlignmentBaseline implements Keys{
	public static final String BASELINE = "baseline";
	public static final String MIDDLE = "middle";

	@Override
	public String getAttrName() {
		return ALIGNMENT_BASELINE;
	}
}
/**
 * _____                       ______ 
 *|  3  |                     |		|
 *| 2 	| : stands for super  |3	| :stands for sub
 *-------	  				  |  log|
 *//*				
public static final class Baseline_Shift implements Keys{
	public static final String SUPER = "super";
	public static final String SUB = "sub";

	@Override
	public String getAttrName() {
		return BASELINE_SHIFT;
	}
}

public static final class Writing_Mode implements Keys{
	public static final String LEFT_TO_RIGHT = "lr";
	public static final String RIGHT_TO_LEFT = "rl";
	public static final String TOP_TO_BOTTOM = "tb";

	@Override
	public String getAttrName() {
		return WRITING_MODE;
	}
}

	public static class Glyph_Orientation_Horizontal implements Keys{
	public static final Double ZERO = 0d;
	public static final Double NINETY = 90d;
	public static final Double ONEEIGHTY = 180d;
	public static final Double TWOSEVENTY = 270d;

	@Override
	public String getAttrName() {
		return GLYPH_ORIENTATION_HORIZONTAL;
	}
}

	public static final class Glyph_Orientation_Vertical extends Glyph_Orientation_Horizontal {
	public static final String AUTO = "auto";		
	
	@Override
	public String getAttrName(){
		return GLYPH_ORIENTATION_VERTICAL;
	}
}



*/	

}
