public class User {

    // Private instances of variables to hold information about the user
    private String username;
    private String password;

    public User(){
    }

    // Method to set the username
    public void setUsername(String username){
        this.username = username;
    }

    // Method to set password
    public void setPassword(String password){
        this.password = password;
    }

    // Method to get the username
    public String getUsername(){
        return username;
    }

    // Method to get password
    public String getPassword(){
        return password;
    }
}
