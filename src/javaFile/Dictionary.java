package javaFile;

import java.util.*;

public class Dictionary {
	
	public ArrayList<String> data = new ArrayList<String>(1000); 
	
	Dictionary() {
		
		data.add("#include ");		

		//Libraries
		data.add("<bits/stdc++.h>");
		data.add("iostream");
		
		//Namespaces
		data.add("using ");		data.add("namespace ");
		
		//Variables
		data.add("int");	
		data.add("double"); 
		data.add("long");
		data.add("long long"); 
		data.add("string");
		
		
		//Data Structures
		
		//Maps
		data.add("map<int,int> ");
		data.add("map<int,bool> ");
		
		//Vectors
		data.add("vector<int> ");
		data.add("vector < pair < int , int > > ");
		data.add("vector < long long > ");
		data.add("vector < string > ");
		
		//Sets
		
		//Pairs
		
		//Keywords
		data.add("break");
		data.add("continue");
		
		//Braces
		data.add("{\n\n}");
		data.add("()");
		data.add("[]");
		
		//Keywords
		data.add("return");
		data.add("make_pair");
		data.add("static");
		data.add("iterator");
		data.add("begin()");
		data.add("end()");
		data.add("stack ");
		data.add("queue ");
		data.add("set ");
		data.add("vector ");
		data.add("fill");
		data.add("new ");
		data.add("delete ");
		data.add("swap");
		data.add("this");
	}
}
