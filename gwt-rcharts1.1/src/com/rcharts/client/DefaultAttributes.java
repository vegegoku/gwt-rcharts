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
package com.rcharts.client;

public class DefaultAttributes {

	private static String[] colors={
		"#9ACD32","#0000FF","#8A2BE2","#A52A2A","#DEB887",
		"#5F9EA0","#FF7F50","#DC143C","#FFFF00","#00FF7F",
		"#00FFFF","#FFEBCD","#7FFF00","#D2691E","#6495ED",
		"#00FFFF","#00008B","#008B8B","#B8860B","#A9A9A9",
		"#A9A9A9","#006400","#BDB76B","#8B008B","#556B2F",
		"#FF8C00","#9932CC","#8B0000","#E9967A","#8FBC8F",
		"#483D8B","#2F4F4F","#00CED1","#9400D3","#FF1493",
		"#00BFFF","#696969","#1E90FF","#B22222","#228B22",
		"#FF00FF","#DCDCDC","#FFD700","#808080","#008000",
		"#ADFF2F","#DAA520","#F08080","#7FFFD4"  
	};
	
	public static void setColors(String[] colors){
		DefaultAttributes.colors = colors;
	}
	
	public static String[] getColors(){
		return colors;
	}
}
