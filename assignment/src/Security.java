public class Security {
    // Private instance variables to hold information about the security
    private String ticker;
    private String investment_name;
    private String investment_type;
    private String risk_level;
    private Double investment_value;
    private int portfolio_id;
    private int quantity;


    public Security() {
    }

    // Method to set the ticker
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    // Method to set the investment name
    public void setInvestmentName(String investment_name) {
        this.investment_name = investment_name;
    }

    // Method to set the investment type
    public void setInvestmentType(String investment_type) {
        this.investment_type = investment_type;
    }

    // Method to set the risk level
    public void setRiskLevel(String risk_level) {
        this.risk_level = risk_level;
    }

    // Method to set the investment value
    public void setInvestmentValue(double investment_value) {
        this.investment_value = investment_value;
    }

    // Method to set the portfolio_id
    public void setPortfolioId(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }



    // Method to set quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    // Method to get the ticker
    public String getTicker() {
        return ticker;
    }

    // Method to get the investment name
    public String getInvestmentName() {
        return investment_name;
    }

    // Method to get the investment type
    public String getInvestmentType() {
        return investment_type;
    }

    // Method to get the risk level
    public String getRiskLevel() {
        return risk_level;
    }

    // Method to get the investment value
    public double getInvestmentValue(){
        return investment_value;
    }


    // Method to get the portfolio id
    public int getPortfolioId() {
        return portfolio_id;
    }

    // Method to get quantity
    public int getQuantity() {
        return quantity;
    }




}

