package javaFile;

import java.util.*;

public class Dictionary {
	
	public ArrayList<String> data = new ArrayList<String>(1000); 
	
	Dictionary() {
		
		data.add("#include ");		

		//Libraries
		data.add("<bits/stdc++.h>");
		
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
		data.add("vector<int,int> ");
		
		//Sets
		
		//Pairs
		
		//Keywords
		data.add("break");
		data.add("continue");
		
		//Braces
		data.add("{\n\n}");
		data.add("()");
		data.add("[]");
	}
}
