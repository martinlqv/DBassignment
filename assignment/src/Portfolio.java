public class Portfolio {

    // Private instance variables to hold information about the portfolio
    private String portfolioName;
    private String description;
    private double totalValue;
    private String username;  // Foreign key linking to the User table

    public Portfolio() {

            // Get the username of the currently logged-in user from SessionManager
            this.username = SessionManager.getCurrentUsername();
    }

    // Method to set the portfolio name
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    // Method to set the description
    public void setDescription(String description) {
        this.description = description;
    }

    // Method to set the total value
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    // Method to set the username
    public void setUsername(String username) {
        this.username = username;
    }


    // Method to get the portfolio name
    public String getPortfolioName() {
        return portfolioName;
    }

    // Method to get the description
    public String getDescription() {
        return description;
    }

    // Method to get the total value
    public double getTotalValue() {
        return totalValue;
    }

    // Method to get the user ID (foreign key)
    public String getUsername() {
        return username;
    }
}
