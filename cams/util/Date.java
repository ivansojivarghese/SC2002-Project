public int viewAllCamps(User user) {
    	int option;
    	
        CampRepository repo = UnifiedCampRepository.getInstance();
        Set<Camp> allCamps = new HashSet<>(repo.allCamps());
        
        ArrayList<Object> camps = new ArrayList<Object>(); // Create an ArrayList object
        
        if(allCamps.isEmpty()) {
            System.out.println("No camps available.");
            return 0;
        }
        
        System.out.println("Camp list will be shown in alphabetical order based on?");
        System.out.println("(0) Name");
        System.out.println("(1) Location");
        System.out.println("(2) Description");
        
        option = UserInput.getIntegerInput(0, 2, "Choose a filter from above by its index:");
        
        // get array of camps (objects)
        // get array of camp names (strings)
        // insertion sort (compareTo) on camp names
        
        // swap objects when names are being swapped
        // display all objects, replace the loop below
        
        // DEFAULT: by alphabetical order of name, compareTo
        
        ArrayList<String> strings = new ArrayList<String>(); // Create an ArrayList object of strings
        
        switch (option) {
	        case 0: // name
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getCampName()); // add name
	            }
	        	
	        	
	    	break;
	        case 1: // location
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getLocation()); // add location
	            }
	        	
	        	
	    	break;
	        case 2: // description
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getDescription()); // add des.
	            }
	        	
			break;
        }
 
        int n = strings.size(); 
        

        for (int j = 1; j < n; j++) {  // insertion sort

            String key = strings.get(j);  // c, g
            Object keyC = camps.get(j); // c, g
            String otherkey;
            Object otherkeyC;
            
            int i = j-1;  // 0, 1
            while ( (i > -1) && ( strings.get(i).compareToIgnoreCase(key) > 0 ) ) {  // f vs c, f vs g
            	String org = strings.get(i); // f
            	Object orgC = camps.get(i); // f
            	String nw = strings.get(i + 1); // c
            	Object nwC = camps.get(i + 1); // c
                
                strings.set(i + 1, org);
                camps.set(i + 1, orgC);
                
                i--;  
            }  
            otherkey = strings.get(i + 1); // f, g
            otherkeyC = camps.get(i + 1); // f, g
            
            strings.set(i + 1, key);
            camps.set(i + 1, keyC);
            
        }  

        
        for (Object c: camps) {
        	System.out.println("_________________________________");
            ((Camp) c).display();
        }
        
        /*
        for(Camp c : allCamps){
            System.out.println("_________________________________");
            c.display();
            
            // c.getCampName().compareTo(null);
        }
        */
        
        return allCamps.size();
    }