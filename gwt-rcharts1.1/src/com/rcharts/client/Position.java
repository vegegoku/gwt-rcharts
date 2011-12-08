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

/**
 * 
 * Marker to state the position of raphael object in a layout manager like RDockPanel
 *
 */
public enum Position {
	NORTH, SOUTH, EAST, WEST;
	
	public static Position getPosition(String position){
		if(position.equalsIgnoreCase("north")){
			return NORTH;
		}
		else if(position.equalsIgnoreCase("south")){
			return SOUTH;
		}
		else if(position.equalsIgnoreCase("east")){
			return EAST;
		}
		else if(position.equalsIgnoreCase("west")){
			return WEST;
		}
		else{
			return null;
		}
	}
	
}
