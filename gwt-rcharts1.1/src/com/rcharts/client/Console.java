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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

public class Console {

	static public final native void logM(String msg)/*-{
		console.log(msg);
	}-*/;
	

	public static void log(String msg){
/*		String agent = Window.Navigator.getUserAgent();
		agent.toLowerCase();
		int indexOfMozilla = agent.lastIndexOf("Mozilla");
		if(indexOfMozilla != -1){
			logM(msg);
		}
*/		return;
	}
}
