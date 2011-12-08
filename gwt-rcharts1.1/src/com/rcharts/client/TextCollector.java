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

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.TextListener;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;

public class TextCollector implements TextListener {

	Set<Text> texts = new HashSet<Text>();
	
	@Override
	public void onText(Text text) {
		texts.add(text);
	}
	
	public Set<Text> getTexts(){
		return texts;
	}
	
	public void setTextsFont(double fontSize){
		for(Text t : texts){
			t.attr("font-size", fontSize);
		}
	}
	
	public void incrementFont(){
		for(Text t : texts){
			double size = 14;//t.attrAsDouble("font-size");
			t.attr("font-size", size+1);
		}
	}
	
	public void setFontColor(String color){
		for(Text t : texts){
			
		}
	}

}
