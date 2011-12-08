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
 * Align class marks for Layout Managers in application telling how the element should be aligned
 * eg: if you have added a {@link com.hydro4ge.raphaelgwt.client.RaphaelObject RaphaelObject} taking
 * space as Box(30*30) to a {@link com.hydro4ge.mycircle.client.RDockPanel RDockPanel} in 
 * (@link com.hydro4ge.mycircle.client.Position * Position.EAST} where 
 * {@link com.hydro4ge.mycircle.client.RDockPanel RDockPanel} already have enough 
 * space as Box(100 x 100). 
 */
public enum Align {

	LEFT, RIGHT, CENTER, TOP, BOTTOM
}
