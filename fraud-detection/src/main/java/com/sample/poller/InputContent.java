package com.sample.poller;

public class InputContent {
	
	private String firstName;
	private String lastName;
	private String nationality;
	private String countrOfResidence;
	private String dateOfBirth;
	private String traderId;
	private long amount;
	private String currency;
	private String stockId;
	private String buyOrSell;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getNationality() {
		return nationality;
	}
	public String getCountrOfResidence() {
		return countrOfResidence;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public String getTraderId() {
		return traderId;
	}
	public long getAmount() {
		return amount;
	}
	public String getCurrency() {
		return currency;
	}
	public String getStockId() {
		return stockId;
	}
	public String getBuyOrSell() {
		return buyOrSell;
	}
	
	public InputContent(String input) {
		this.firstName = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.lastName = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.nationality = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.countrOfResidence = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.dateOfBirth = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.traderId = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		try {
			this.amount = Integer.parseInt(input.substring(0, input.indexOf(";")));
		} catch (Exception ex) {
			this.amount = 0;
		}
		
		input = input.substring(input.indexOf(";") + 1);
		this.currency = input.substring(0, input.indexOf(";"));
		
		input = input.substring(input.indexOf(";") + 1);
		this.stockId = input.substring(0, input.indexOf(";"));
		
		this.buyOrSell = input.substring(input.indexOf(";") + 1);
		
	}
	
	@Override
	public boolean equals(Object input) {
		boolean isSimilar = false;
		if (input instanceof InputContent) {
			InputContent toCompareObj = (InputContent) input;
			if (toCompareObj == this) {
				isSimilar = true;
			}
			if (toCompareObj.traderId.equals(this.traderId) && toCompareObj.stockId.equals(this.stockId)) {
				isSimilar = true;
			}
		}
		return isSimilar;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (traderId != null) {
	        result = 31 * result + traderId.hashCode();
	    }
	    if (stockId != null) {
	        result = 31 * result + stockId.hashCode();
	    }
	    return result;
	}
	
}
