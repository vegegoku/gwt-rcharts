A plugin for making gwt charts

## The gwt-rcharts is a charting plugin that based on 'raphaeljs' and 'raphaelgwt'(**modified custom version) for various types of charts. ##**

## Plugin uses the SVG W3C Recommendation and VML as a base for creating graphics and are compatible on almost all major browsers without any dependency over third party plugin like Adobe flash etc. ##

To see live demo goto http://gwt-rcharts.appspot.com/

To use :
1)Include the jar file to the path of project
2)write the following line of code if module.gwt.xml file
> 

&lt;inherits name='com.rcharts.Gwt\_rcharts1\_1'&gt;



&lt;/inherits&gt;


3)Link the web page to external RaphaelJS library for example
> > 

&lt;script type="text/javascript" src="raphael.js"&gt;



&lt;/script&gt;






(**Note : To take attention to one unresolved issue of application on IE, its explicitly shown in live demo on IE. The Workaround solution for now is avoid detaching then attaching Charts instead use new instance. For details see issues)**
