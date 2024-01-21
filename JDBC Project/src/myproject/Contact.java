package myproject;

public class Contact {
	
	    private int id;  
	    private String name;
	    private String phoneNumber;

	    public Contact(String name, String phoneNumber) {
	        this.name = name;
	        this.phoneNumber = phoneNumber;
	    }

	    public Contact(int id, String name, String phoneNumber) {
	        this.id = id;
	        this.name = name;
	        this.phoneNumber = phoneNumber;
	    }

	    
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    
	    public String getName() {
	        return name;
	    }

	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    @Override
	    public String toString() {
	        return String.format("{id=%d, name=%s, phoneNumber=%s}", id, name, phoneNumber);
	    }
	}



