package project_part2;

public abstract class Person {

    int ID ;
	private String name;
    private String address;
    private String phone_number;
    private String email_address;

    public Person() {
    }

    public Person(String newName, String newAddress, String newPhone_number, String newEmail){
        name = newName;
        address = newAddress;
        phone_number = newPhone_number;
        email_address = newEmail;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public void setAddress(String newAddress){
        address = newAddress;
    }

    public String getAddress(){
        return address;
    }

    public void setPhone(String newPhone_number){
        phone_number = newPhone_number;
    }

    public String getPhone(){
        return phone_number;
    }

    public void setEmail(String newEmail){
        email_address = newEmail;
    }

    public String getEmail(){
        return email_address;
    }

    public abstract String toString();

}
