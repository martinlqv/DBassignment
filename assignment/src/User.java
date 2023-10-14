public class User {

    // Private instances of variables to hold information about the user
    private String username;
    private String name;
    private String surname;
    private String email;
    private String userPassword;
    private String dataOfBirth;

    public User(){
    }

    // Method to set the username
    public void setUsername(String username){
        this.username = username;
    }

    // Method to set password
    public void setPassword(String userPassword){
        this.userPassword = userPassword;
    }

    // Method to set name
    public void setName(String name) {
        this.name = name;
    }

    // Method to set surname
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Method to set email
    public void setEmail(String email) {
        this.email = email;
    }

    // Method to set date of birth
    public void setDateOfBirth(String dateOfBirth) {
        this.dataOfBirth = dateOfBirth;
    }

    // Method to get the username
    public String getUsername(){
        return username;
    }

    // Method to get password
    public String getPassword(){
        return userPassword;
    }

    // Method to get name
    public String getName() {
        return name;
    }

    // Method to get surname
    public String getSurname() {
        return surname;
    }

    // Method to get email
    public String getEmail() {
        return email;
    }

    // Method to get date of birth
    public String getDataOfBirth() {
        return dataOfBirth;
    }


}
